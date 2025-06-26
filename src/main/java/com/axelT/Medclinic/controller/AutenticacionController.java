package com.axelT.Medclinic.controller;

import com.axelT.Medclinic.domain.usuario.DatosAutenticacionUsuario;
import com.axelT.Medclinic.domain.usuario.Usuario;
import com.axelT.Medclinic.infra.security.DatosJWTtoken;
import com.axelT.Medclinic.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity autenticarUsuario(@RequestBody @Valid
                                                DatosAutenticacionUsuario datosAutenticacionUsuario){
        //System.out.println("Usuario: " + datosAutenticacionUsuario.login());
        Authentication authtoken = new UsernamePasswordAuthenticationToken(
                datosAutenticacionUsuario.login(),
                datosAutenticacionUsuario.clave());
        var usuarioAutenticado = authenticationManager.authenticate(authtoken);
        var JWTtoken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
        return ResponseEntity.ok(new DatosJWTtoken(JWTtoken));
        /*
        Este try-catch lo utilice en reemplazo del authenticationManager, para encontrar el error que no
        dejaba loguear, me devolvio que mi entidad Usuario no tenia un constructor para argumentos vacios
         y el problema estaba era que no tenia cargado el @NoArgsConstructor de lomboks !!!
        try {
            Authentication auth = authenticationManager.authenticate(token);
            System.out.println("Autenticación exitosa para: " + datosAutenticacionUsuario.login());
            return ResponseEntity.ok().build();
        } catch (AuthenticationException e) {
            System.out.println("Error al autenticar: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }*/
    }
}