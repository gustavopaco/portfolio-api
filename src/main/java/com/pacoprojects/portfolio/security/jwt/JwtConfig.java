package com.pacoprojects.portfolio.security.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.application.jwt")
public class JwtConfig {
    private String prefix;
    private String secretKey;
    private Integer daysExpiration;
}
