package com.Delivery.delivery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*"); // Defina as origens permitidas aqui
        config.addAllowedMethod("*"); // Defina os métodos HTTP permitidos aqui
        config.addAllowedHeader("*"); // Defina os cabeçalhos permitidos aqui
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
