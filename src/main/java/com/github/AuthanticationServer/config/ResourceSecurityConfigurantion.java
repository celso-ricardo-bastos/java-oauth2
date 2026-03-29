package com.github.AuthanticationServer.config;

import com.github.AuthanticationServer.security.JwtCustomAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ResourceSecurityConfigurantion {

//    @Bean
//    @Order(2)
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
//        return http
//                .csrf(AbstractHttpConfigurer::disable)
//                .formLogin(Customizer.withDefaults())
//                .httpBasic(Customizer.withDefaults())
//                .authorizeHttpRequests( authorize ->
//                        authorize
//                                .requestMatchers(HttpMethod.POST, "/usuarios/**").permitAll()
//                                .anyRequest().authenticated()
//                )
//                .build();
//    }

    // Configurações padrão do security
    @Bean
    @Order(2)
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtCustomAuthenticationFilter jwtCustomAuthenticationFilter
    ) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) //Desabilite essa funcionalidade para permitir URL diferentes
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())

//                .formLogin(configurer -> {
//                    configurer.loginPage("/login").permitAll();
//                })
                .authorizeHttpRequests(autorize -> {
                    autorize.requestMatchers( "/login/**").permitAll();
                    autorize.requestMatchers(HttpMethod.POST, "/usuarios/**").permitAll();
                    autorize.anyRequest().authenticated();
                })
                .oauth2ResourceServer(oauthRs -> oauthRs.jwt(Customizer.withDefaults()))
                .addFilterAfter(jwtCustomAuthenticationFilter, BearerTokenAuthenticationFilter.class)
                .build();
    }

    // Configurando o scope para evitar prefixo para nas ROLES
    // Prefixo: "ROLE"
    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }
}
