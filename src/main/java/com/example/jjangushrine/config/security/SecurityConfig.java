package com.example.jjangushrine.config.security;

import com.example.jjangushrine.config.security.jwt.JwtAccessDeniedHandler;
import com.example.jjangushrine.config.security.jwt.JwtAuthenticationEntryPoint;
import com.example.jjangushrine.config.security.jwt.JwtFilter;
import com.example.jjangushrine.domain.oauth2.handler.OAuth2FailureHandler;
import com.example.jjangushrine.domain.oauth2.handler.OAuth2SuccessHandler;
import com.example.jjangushrine.domain.oauth2.service.OAuth2UserService;
import com.example.jjangushrine.domain.user.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final JwtAccessDeniedHandler accessDeniedHandler;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;
    private final OAuth2UserService oAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final OAuth2FailureHandler oAuth2FailureHandler;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .oauth2Login(oauth -> oauth
                        .userInfoEndpoint(userInfo -> userInfo.userService(oAuth2UserService))
                        .successHandler(oAuth2SuccessHandler)
                        .failureHandler(oAuth2FailureHandler)
                )

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/v1/auth/**",
                                "/oauth2/**",
                                "/login/**",
                                "/auth/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-resources/**"
                        ).permitAll()

                        // 누구나 조회 가능한 GET
                        .requestMatchers(HttpMethod.GET,
                                "/api/v1/stores",
                                "/api/v1/products/**",
                                "/api/v1/products/ranking",
                                "/api/v1/products/store"
                        ).permitAll()

                        // ADMIN
                        .requestMatchers("/api/v1/admin/**").hasRole(UserRole.ADMIN.name())

                        // SELLER
                        .requestMatchers("/api/v1/sellers/**").hasRole(UserRole.SELLER.name())
                        .requestMatchers(HttpMethod.POST,
                                "/api/v1/stores").hasRole(UserRole.SELLER.name())
                        .requestMatchers(HttpMethod.DELETE,
                                "/api/v1/stores").hasRole(UserRole.SELLER.name())
                        .requestMatchers(HttpMethod.PATCH,
                                "/api/v1/stores").hasRole(UserRole.SELLER.name())
                        .requestMatchers(HttpMethod.DELETE,
                                "/api/v1/products/{productId}").hasRole(UserRole.SELLER.name())

                        // USER
                        .requestMatchers("/api/v1/user/**").hasRole(UserRole.USER.name())
                        .requestMatchers("/api/v1/carts/**").hasRole(UserRole.USER.name())
                        .requestMatchers("/api/v1/orders/**").hasRole(UserRole.USER.name())
                        .requestMatchers("/api/v1/addresses/**").hasRole(UserRole.USER.name())
                        .requestMatchers(HttpMethod.GET,
                                "/api/v1/users").hasRole(UserRole.USER.name())
                        .requestMatchers(HttpMethod.DELETE,
                                "/api/v1/users").hasRole(UserRole.USER.name())
                        .requestMatchers(HttpMethod.PATCH,
                                "/api/v1/users").hasRole(UserRole.USER.name())
                        .anyRequest().authenticated())

                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

                .exceptionHandling(handler ->
                        handler.authenticationEntryPoint(authenticationEntryPoint)
                                .accessDeniedHandler(accessDeniedHandler))
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:3000",
                "http://15.164.91.27:8080"
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setExposedHeaders(List.of("Authorization"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
