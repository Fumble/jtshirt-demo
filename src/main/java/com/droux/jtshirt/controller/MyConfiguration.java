package com.droux.jtshirt.controller;

import java.util.Locale;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
@EnableJpaRepositories(basePackages = {"com.droux.jtshirt.data.repository"})
@EntityScan(basePackages = {"com.droux.jtshirt.data.bean", "com.droux.jtshirt.controller.form"})
public class MyConfiguration {
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.US);
        return slr;
    }

}
