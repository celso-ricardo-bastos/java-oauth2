package com.github.AuthanticationServer.security;

import com.github.AuthanticationServer.model.Usuario;
import com.github.AuthanticationServer.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAutenticationProvider implements AuthenticationProvider {

    private final UsuarioService service;
    private final PasswordEncoder encoder;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String login = authentication.getName();
        String senhaDigitada = authentication.getCredentials().toString();

        Usuario usuario = service.obterPorLogin(login);

        if(usuario == null) {
            throw this.getUsernameNotFoundException();
        }

        String senhaEncoded = usuario.getSenha();

        boolean matchSenha = encoder.matches(senhaDigitada, senhaEncoded);

        if (matchSenha) {
            return new CustomAuthentication(usuario);
        }
        throw this.getUsernameNotFoundException();
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }

    private UsernameNotFoundException getUsernameNotFoundException() {
        return new UsernameNotFoundException("Ususario e/ou senha incorretos.");
    }
}
