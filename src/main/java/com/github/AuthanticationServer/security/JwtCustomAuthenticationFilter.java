package com.github.AuthanticationServer.security;

import com.github.AuthanticationServer.model.Usuario;
import com.github.AuthanticationServer.service.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtCustomAuthenticationFilter extends OncePerRequestFilter {
    private final UsuarioService usuarioService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (isOAlth2(authentication)) {
            String login = authentication.getName();
            Usuario usuario = usuarioService.obterPorLogin(login);
            if (usuario != null) {
                authentication = new CustomAuthentication(usuario);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        };

        filterChain.doFilter(request, response);
    }

    private boolean isOAlth2(Authentication authentication) {
        return authentication != null && authentication instanceof JwtAuthenticationToken;
    }
}
