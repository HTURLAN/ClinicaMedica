package com.axelT.Medclinic.infra.security;

import com.axelT.Medclinic.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacionService implements UserDetailsService {

    @Autowired  //NO ES RECOMENDABLE HACERLO CON AUTOWIRED PORQUE DA ERROR CUANDO SE HACEN LOS TESTING!!
                // CONVIENE HACER UN SETTER O CONSTRUCTOR...
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername (String username) throws UsernameNotFoundException {
        return usuarioRepository.findByLogin(username);

    }
}
