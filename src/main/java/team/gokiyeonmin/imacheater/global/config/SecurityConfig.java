package team.gokiyeonmin.imacheater.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.cors.CorsConfigurationSource;
import team.gokiyeonmin.imacheater.global.security.filter.GlobalLoggerFilter;
import team.gokiyeonmin.imacheater.global.security.filter.JwtAuthenticationFilter;
import team.gokiyeonmin.imacheater.global.security.filter.JwtExceptionFilter;
import team.gokiyeonmin.imacheater.global.security.service.CustomUserDetailService;
import team.gokiyeonmin.imacheater.global.util.JwtUtil;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    private final CustomUserDetailService customUserDetailService;
    private final JwtUtil jwtUtil;
    private final AntPathMatcher antPathMatcher;
    private final ObjectMapper objectMapper;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain securityFilterChain(
            final HttpSecurity httpSecurity,
            final CorsConfigurationSource corsConfigurationSource
    ) throws Exception {
        // TODO: CORS Configuration
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable);

        httpSecurity
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        httpSecurity
                .headers(headers ->
                        headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));

        /*
         * Default: All requests are permitted
         * Endpoints need authority: Use @Secured annotation
         * ex) @Secured("ROLE_USER")
         */
        httpSecurity
                .authorizeHttpRequests(request ->
                        request
                                .anyRequest().permitAll());

        httpSecurity
                .addFilterBefore(
                        new JwtAuthenticationFilter(customUserDetailService, jwtUtil, antPathMatcher),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(
                        new JwtExceptionFilter(objectMapper),
                        JwtAuthenticationFilter.class)
                .addFilterBefore(
                        new GlobalLoggerFilter(),
                        JwtExceptionFilter.class);

        return httpSecurity.build();
    }

}
