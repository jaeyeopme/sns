package me.jaeyeopme.sns.global.config;

import me.jaeyeopme.sns.domain.user.application.PasswordEncryptor;
import me.jaeyeopme.sns.domain.user.application.impl.BCryptPasswordEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncryptor encoder() {
        return new BCryptPasswordEncryptor();
    }

}
