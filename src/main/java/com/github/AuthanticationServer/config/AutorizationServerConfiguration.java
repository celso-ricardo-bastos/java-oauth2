package com.github.AuthanticationServer.config;

import com.github.AuthanticationServer.security.CustomAuthentication;
import com.github.AuthanticationServer.security.JwtCustomAuthenticationFilter;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Configuration
@EnableWebSecurity
public class AutorizationServerConfiguration {

    @Bean
    @Order(1)
    public SecurityFilterChain authSeverSecurityFilterChain(
            HttpSecurity http,
            JwtCustomAuthenticationFilter jwtCustomAuthenticationFilter) throws Exception{

        // Apply config defaults
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

        // Use to get the info of the token, eg. user, roles, etc.
        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                .oidc(Customizer.withDefaults());

        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
        http.formLogin(Customizer.withDefaults());
//        http.addFilterAfter(jwtCustomAuthenticationFilter, BearerTokenAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public TokenSettings tokenSettings() {
        return TokenSettings.builder()
                .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
                // access_token: token usado nas requisições
                .accessTokenTimeToLive(Duration.ofMinutes(60))
                // refresh_token: token usado para renovar o acces_token
                .refreshTokenTimeToLive(Duration.ofMinutes(90))
                .build();
    }

    @Bean
    public ClientSettings clientSettings() {
        return ClientSettings
                .builder()
                .requireAuthorizationConsent(false)
                .build();
    }

    // JWK - Json Web Key
    // Usado para criar a chave de assinatura do token
    // RSA: chave assimetrica "Publica e Privada"
    @Bean
    public JWKSource<SecurityContext> jwkSource() throws Exception{
        RSAKey rsaKey = gerarChaveRSA();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

    // Gerando par de chaves RSA
    private RSAKey gerarChaveRSA() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();

        return new RSAKey
                .Builder(rsaPublicKey)
                .privateKey(rsaPrivateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
    }

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

//    @Bean
//    public AuthorizationServerSettings authorizationServerSettings(){
//        return AuthorizationServerSettings.builder()
//                // Obter o Token
//                .tokenEndpoint("/oauth2/token")
//                // consultar status do token
//                .tokenIntrospectionEndpoint("/oauth2/introspect")
//                // revogar
//                .tokenRevocationEndpoint("/oauth2/revoke")
//                // Autorization endpoint
//                .authorizationEndpoint("/oauth2/autorize")
//                // Informações do usuario
//                .oidcUserInfoEndpoint("/oauth2/inserinfo")
//                // Obter chave publica
//                .jwkSetEndpoint("/oauth2/jwks")
//                // logaout
//                .oidcLogoutEndpoint("/oauth2/logout")
//                .build();
//    }

//    @Bean
//    public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer() {
//        return context -> {
//          var principal = context.getPrincipal();
//          if(principal instanceof CustomAuthentication customAuthentication) {
//              OAuth2TokenType type = context.getTokenType();
//
//              if(OAuth2TokenType.ACCESS_TOKEN.equals(type)){
//                  Collection<GrantedAuthority> authorities = customAuthentication.getAuthorities();
//                  List<String> listAuthoritiesList = authorities.stream().map(GrantedAuthority::getAuthority).toList();
//                  context
//                          .getClaims()
//                          .claim("authorities", listAuthoritiesList)
//                          .claim("email", customAuthentication.getUsuario().getLogin());
//              }
//          }
//        };
//    }

}
