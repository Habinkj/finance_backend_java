package com.example.demo.config;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import java.util.Arrays;

// 🔥 SECURITY REACTIVATED
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults()) // Activates the CorsFilter below
                .csrf(AbstractHttpConfigurer::disable) // Disabled because we use JWTs, not cookies
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // 1. Let the invisible Preflight checks pass
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // 2. Let Render's Health Snipers pass without a token
                        .requestMatchers("/", "/health", "/api/expenses/health").permitAll()

                        // 3. Let users log in and register without a token
                        .requestMatchers("/users/login", "/users/register", "/api/auth/**").permitAll()

                        // 4. Everything else requires a valid JWT
                        .anyRequest().authenticated()
                );

        // Inject your custom JWT filter before the standard Spring password check
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Allow credentials (like sending JWTs in the Authorization header)
        config.setAllowCredentials(true);

        // 🔥 CRITICAL FIX: The exact URL of your frontend
        config.setAllowedOrigins(Arrays.asList(
                "http://localhost:5173", // For your local development
                "https://finance-dashboard-ui-blond-omega.vercel.app" // Your live cloud frontend
        ));

        config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}