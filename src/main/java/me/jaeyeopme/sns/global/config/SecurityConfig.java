package me.jaeyeopme.sns.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {

    @Bean
    public UserPasswordEncoder encoder() {
        return new BCryptUserPasswordEncoder();
    }

}
