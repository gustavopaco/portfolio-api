package com.pacoprojects.portfolio.service;

import com.pacoprojects.portfolio.constants.Messages;
import com.pacoprojects.portfolio.dto.AuthRequest;
import com.pacoprojects.portfolio.model.TokenConfirmation;
import com.pacoprojects.portfolio.model.UserApplication;
import com.pacoprojects.portfolio.repository.UserApplicationRepository;
import com.pacoprojects.portfolio.security.jwt.JwtConfig;
import com.pacoprojects.portfolio.security.jwt.JwtUtilService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserApplicationRepository userApplicationRepository;
    private final JwtUtilService jwtUtilService;
    private final JwtConfig jwtConfig;
    private final AuthenticationManager authenticationManager;

    public void authenticate(@NotNull AuthRequest authRequest, HttpServletResponse response) {

        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password()));
            UserApplication user = (UserApplication) authenticate.getPrincipal();
            String basicToken = generateToken(user);
            saveNewToken(basicToken, user);
            String fullToken = getFullToken(basicToken);
            addHeaderResponse(response, fullToken);

        } catch (BadCredentialsException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.INVALID_CREDENTIALS);
        } catch (LockedException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.USER_NOT_ACTIVE);
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.INTERNAL_ERROR);
        }
    }

    private String generateToken(UserDetails user) {
        return jwtUtilService.generateToken(user);
    }

    private void saveNewToken(String basicToken, UserApplication user) {
        TokenConfirmation tokenConfirmation = new TokenConfirmation();
        tokenConfirmation.setToken(basicToken);
        tokenConfirmation.setExpiredAt(LocalDateTime.ofInstant(jwtUtilService.getExpirationDate().toInstant(), ZoneId.systemDefault()));
        tokenConfirmation.setCreatedAt(LocalDateTime.now());
        user.addTokenConfirmation(tokenConfirmation);
        userApplicationRepository.save(user);
    }

    private String getFullToken(String basicToken) {
        return jwtConfig.getPrefix().concat(" ").concat(basicToken);
    }

    private void addHeaderResponse(HttpServletResponse response, String fullToken) {
        response.addHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.AUTHORIZATION);
        response.addHeader(HttpHeaders.AUTHORIZATION, fullToken);
    }
}
