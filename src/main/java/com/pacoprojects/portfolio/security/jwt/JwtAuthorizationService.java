package com.pacoprojects.portfolio.security.jwt;

import com.pacoprojects.portfolio.constants.Messages;
import com.pacoprojects.portfolio.repository.UserApplicationRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtAuthorizationService {

    private final JwtUtilService jwtUtilService;
    private final UserApplicationRepository userApplicationRepository;

    public void authorization(HttpServletRequest request) {

        Map<String, Object> map = jwtUtilService.breakToken(request);

        if (!map.isEmpty()) {
            String username = map.get("username").toString();
            String basicToken = map.get("basicToken").toString();

            userApplicationRepository.findByUsername(username)
                    .ifPresentOrElse(userApplication -> {
                        if (userApplication.getTokenConfirmations().stream().anyMatch(tokenConfirmation -> tokenConfirmation.getToken().equals(basicToken))) {
                            Authentication authentication =
                                    new UsernamePasswordAuthenticationToken(username, null, userApplication.getAuthorities());
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        }
                    }, () -> {
                        throw new AuthorizationServiceException(Messages.USER_NOT_FOUND);
                    });
        }
    }
}
