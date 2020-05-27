package main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/*@PropertySources({
        @PropertySource(value = "classpath:application.properties"),
        @PropertySource(value = "file:~/application.properties", ignoreResourceNotFound = true)
})*/
@SpringBootApplication
public class LibraryAutomationApplication {
    public static void main(String[] args) {
        SpringApplication.run(LibraryAutomationApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
