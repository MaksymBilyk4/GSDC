package com.gsdc.server.config;

import com.gsdc.server.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configurable
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final String account;
    private final String login;
    private final String signup;
    private final String token;


    // Configure here open endpoints for non-auth user
    public SecurityConfig(
            JwtFilter jwtFilter, @Value("${api.version}/auth/account") String account,
            @Value("${api.version}/auth/login") String login, @Value("${api.version}/auth/signup") String signup,
            @Value("${api.version}/auth/access") String token
    ) {
        this.jwtFilter = jwtFilter;
        this.account = account;
        this.login = login;
        this.signup = signup;
        this.token = token;
    }

//    TODO Swagger UI open endpoint for Web
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().antMatchers("/h2-console/**");
//    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.httpBasic().disable().csrf().disable().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests(
                        auth -> auth.antMatchers(account, login, signup, token)
                                .permitAll().anyRequest().authenticated()
                                .and().addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                ).build();
    }

    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
