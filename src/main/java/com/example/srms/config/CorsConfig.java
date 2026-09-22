package com.example.srms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

/**
 * Global CORS configuration.
 *
 * The frontend in /frontend/index.html is a plain static HTML file that can
 * be opened directly (file://) or served from any local dev server (e.g.
 * http://127.0.0.1:5500 via VS Code Live Server). Since that's a different
 * origin than the backend (http://localhost:8080), the browser blocks
 * fetch() calls unless the server explicitly allows it — this filter does that.
 *
 * IMPORTANT: this is the ONLY place CORS is configured. Do not also add
 * @CrossOrigin on controllers or a WebMvcConfigurer.addCorsMappings() override
 * — having more than one CORS mechanism active at once can make Spring send
 * the Access-Control-Allow-Origin header twice on the same response, which
 * browsers treat as invalid and block outright (this silently breaks POST/PUT
 * in particular, since those trigger a preflight OPTIONS request first).
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(List.of("*")); // includes the "null" origin sent by file://
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(false);
        config.setMaxAge(3600L);
        source.registerCorsConfiguration("/api/**", config);
        return new CorsFilter(source);
    }
}
