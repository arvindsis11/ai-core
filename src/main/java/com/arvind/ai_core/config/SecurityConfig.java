package com.arvind.ai_core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Security configuration with password encoding and future authentication setup.
 *
 * NOTE: If you see compilation errors about missing imports (spring.security.crypto),
 * please run: mvn clean install
 * Or in IDE: Maven > Reload Projects
 * This will download all dependencies defined in pom.xml
 */
@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // JWT configuration will be added in Phase 4
    // OAuth2 configuration will be added in Phase 4
}
