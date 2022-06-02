package me.jaeyeopme.sns.global.config;

import me.jaeyeopme.sns.domain.user.application.AccountPasswordEncoder;
import me.jaeyeopme.sns.domain.user.application.BCryptAccountPasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {

    @Bean
    public AccountPasswordEncoder encoder() {
        return new BCryptAccountPasswordEncoder();
    }

}
