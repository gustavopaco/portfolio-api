package com.pacoprojects.portfolio.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pacoprojects.portfolio.constants.Messages;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtAuthorizationService jwtAuthorizationService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response,@NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            jwtAuthorizationService.authorization(request);
            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            Map<String, Object> map = mapException(exception, response);
            new ObjectMapper().writeValue(response.getOutputStream(), map);
        }
    }

    private Map<String, Object> mapException(Exception exception, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        final String MESSAGE = "message";

        if (exception instanceof AuthorizationServiceException) {
            map.put(MESSAGE, exception.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return map;
        } else if (exception instanceof ExpiredJwtException) {
            map.put(MESSAGE, Messages.TOKEN_EXPIRED);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return map;
        } else if (exception instanceof MalformedJwtException) {
            map.put(MESSAGE, Messages.INVALID_TOKEN);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return map;
        } else {
            map.put(MESSAGE, exception.getMessage());
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return map;
        }
    }
}
