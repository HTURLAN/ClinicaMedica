package com.axelT.Medclinic.infra.security;

import com.axelT.Medclinic.domain.usuario.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // System.out.println("El filtro está siendo implementado");
        var authToken = request.getHeader("Authorization");
               if (authToken != null) {
                     var token = authToken.replace("Bearer ", "");
                var subject = tokenService.getSubject(token);
                    if (subject != null){   //Esto, quiere decir que el usuario es válido
                        var usuario = usuarioRepository.findByLogin(subject);
                        var authentication = new UsernamePasswordAuthenticationToken(usuario, null,
                                usuario.getAuthorities());  //AQUí FORZAMOS EL INICIO DE SEsION0
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
            }
        filterChain.doFilter(request, response);

    }
}

