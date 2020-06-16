package com.pl.orthography;

import com.pl.orthography.config.security.SecurityConfiguration;
import com.pl.orthography.service.UserService;
import com.pl.orthography.ui.MainView;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * The entry point of the Spring Boot application.
 */
@SpringBootApplication(
        scanBasePackageClasses = {SecurityConfiguration.class, MainView.class,
                Application.class, UserService.class},
        exclude = {
                ErrorMvcAutoConfiguration.class,
        })
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
