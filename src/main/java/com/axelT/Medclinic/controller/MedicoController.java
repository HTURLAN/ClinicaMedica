package com.axelT.Medclinic.controller;

import com.axelT.Medclinic.domain.direccion.DatosDireccion;
import com.axelT.Medclinic.domain.medico.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
    @Autowired
    private MedicoRepository medicoRepository;

    //Segun los standard de Rest, para un get se deberia devolver:
    // 201 Created,
    // y ademas deberia devolver la URL del medico creado con un metodo GET:
    //GET http://localhost:8080/medicos/xx(id del medico)

    @PostMapping
    public ResponseEntity<DatosRespuestaMedico> registrarMedico(@RequestBody @Valid DatosRegistroMedico DatosRegistroMedico,
                                                                UriComponentsBuilder uriComponentsBuilder) {
           Medico medico = medicoRepository.save(new Medico(DatosRegistroMedico));
           DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(medico.getId(), medico.getNombre(),
                   medico.getTelefono(), medico.getDocumento(), medico.getEmail(),
                   medico.getEspecialidad(),
                   new DatosDireccion(medico.getDireccion().calle(),
                           medico.getDireccion().numero(),
                           medico.getDireccion().distrito(),
                           medico.getDireccion().ciudad(),
                           medico.getDireccion().complemento()));
        URI url = uriComponentsBuilder.path("medicos/{id}").buildAndExpand(medico.getId()).toUri();
           return ResponseEntity.created(url).body(datosRespuestaMedico);
    }


    @GetMapping
    public  ResponseEntity<Page<DatosListadoMedico>> listadoMedicos (@PageableDefault(size = 10, sort = "nombre") Pageable paginacion) {
//        return medicoRepository.findAll(paginacion).map(DatosListadoMedico::new);
        return ResponseEntity.ok(medicoRepository.findByActivoTrue(paginacion).map(DatosListadoMedico::new));
    }

    //Cuando hacemos un Update es una buena practica devolver
    // el body de la entidad que se actualizo

@PutMapping
@Transactional  // opcion para indica el inicio y para que finalmente haga el commit a nivel de DB
    public ResponseEntity actualizarMedico(@RequestBody @Valid DatosActualizarMedico datosActualizarMedico){
        //Aqui traigo los datos del medico que ingreso el cliente desde mi base de datos(medicoRepository)
        Medico medico = medicoRepository.getReferenceById(datosActualizarMedico.id());

        //Aqui le datos la instruccion de lo que debe hacer con la instancia "medico" que
        //trajo desde mi DB.
        medico.actualizarDatos(datosActualizarMedico);
    return ResponseEntity.ok(new DatosRespuestaMedico(medico.getId(), medico.getNombre(),
            medico.getTelefono(), medico.getDocumento(), medico.getEmail(),
            medico.getEspecialidad(),
            new DatosDireccion(medico.getDireccion().calle(),
                    medico.getDireccion().numero(),
                    medico.getDireccion().distrito(),
                    medico.getDireccion().ciudad(),
                    medico.getDireccion().complemento())));
    }
    @DeleteMapping("/{id}") //dentro del parentesis mapeamos la URL que viene de nuestro cliente
    @Transactional
    //Para controlar el codigo de respuesta utilizamos el ResponseEntity
    public ResponseEntity eliminarMedico(@PathVariable Long id ){
        Medico medico = medicoRepository.getReferenceById(id);
        medico.desactivarMedico(medico);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}") //dentro del parentesis mapeamos la URL que viene de nuestro cliente
    //Para controlar el codigo de respuesta utilizamos el ResponseEntity
    public ResponseEntity<DatosRespuestaMedico> retornaDatosMedico(@PathVariable Long id ) {
        Medico medico = medicoRepository.getReferenceById(id);
        var datosMedico = new DatosRespuestaMedico(medico.getId(), medico.getNombre(),
                medico.getTelefono(), medico.getDocumento(), medico.getEmail(),
                medico.getEspecialidad(),
                new DatosDireccion(medico.getDireccion().calle(),
                        medico.getDireccion().numero(),
                        medico.getDireccion().distrito(),
                        medico.getDireccion().ciudad(),
                        medico.getDireccion().complemento()));
        return ResponseEntity.ok(datosMedico);
    }
}