package com.pacoprojects.portfolio.security.jwt;

import com.google.common.base.Strings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtUtilService {

    private final JwtConfig jwtConfig;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtConfig.getSecretKey().getBytes());
    }

    public Map<String, Object> breakToken(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String fullToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (Strings.isNullOrEmpty(fullToken)) {
            String basicToken = getBasicToken(fullToken);
            if (!isTokenExpired(basicToken)) {
                map.put("username", extractUsername(basicToken));
                map.put("basicToken", basicToken);
                return map;
            }
        }
        return map;
    }

    private Claims extractAllClaims(String basicToken) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(basicToken)
                .getBody();
    }

    private <T> T extractClaim(String basicToken, Function<Claims, T> extractor) {
        return extractor.apply(extractAllClaims(basicToken));
    }

    private boolean isTokenExpired(String basicToken) {
        Date date = extractClaim(basicToken, Claims::getExpiration);
        return date.before(new Date());
    }

    private String extractUsername(String basicToken) {
        return extractClaim(basicToken, Claims::getSubject);
    }

    private String getBasicToken(String fullToken) {
        return fullToken.replace(jwtConfig.getPrefix() + " ", "");
    }
}
