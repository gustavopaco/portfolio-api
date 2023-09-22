package com.pacoprojects.portfolio;

import com.pacoprojects.portfolio.security.jwt.JwtUtilService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("dev")
class ApplicationTests {

    @Autowired
    private JwtUtilService jwtUtilService;

    @Test
    void contextLoads() {
        assertNotNull(jwtUtilService);
    }
}
