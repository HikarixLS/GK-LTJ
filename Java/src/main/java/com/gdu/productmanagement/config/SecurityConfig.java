package com.gdu.productmanagement.config;

import com.gdu.productmanagement.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        // Public resources
                        .requestMatchers("/", "/home", "/login", "/register", "/css/**", "/js/**", "/images/**",
                                "/webjars/**")
                        .permitAll()
                        .requestMatchers("/h2-console/**").permitAll() // For H2 console in development

                        // Admin only
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // Manager and Admin
                        .requestMatchers("/manage/**", "/reports/**").hasAnyRole("ADMIN", "MANAGER")

                        // User, Manager and Admin
                        .requestMatchers("/students/**", "/products/**", "/stores/**", "/books/**")
                        .hasAnyRole("USER", "MANAGER", "ADMIN")

                        // Any authenticated user
                        .requestMatchers("/profile/**", "/dashboard/**").authenticated()

                        // All other requests require authentication
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/perform_login")
                        .defaultSuccessUrl("/dashboard", true)
                        .failureUrl("/login?error=true")
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll())
                .rememberMe(remember -> remember
                        .key("uniqueAndSecret")
                        .tokenValiditySeconds(86400) // 24 hours
                        .userDetailsService(userDetailsService))
                .sessionManagement(session -> session
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(false))
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**") // Disable CSRF for H2 console
                )
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.sameOrigin()) // Allow frames for H2 console
                );
        http.authenticationProvider(authenticationProvider());

        return http.build();
    }
}
