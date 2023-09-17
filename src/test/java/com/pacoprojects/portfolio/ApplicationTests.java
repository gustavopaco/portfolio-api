package com.pacoprojects.portfolio;

import com.pacoprojects.portfolio.security.jwt.JwtUtilService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("dev")
class ApplicationTests {

    private final JwtUtilService jwtUtilService;

    public ApplicationTests(JwtUtilService jwtUtilService) {
        this.jwtUtilService = jwtUtilService;
    }

    @Test
    void contextLoads() {
        assertNotNull(jwtUtilService);
    }
}
