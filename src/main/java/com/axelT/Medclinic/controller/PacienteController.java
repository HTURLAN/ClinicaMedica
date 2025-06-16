package com.axelT.Medclinic.controller;

import com.axelT.Medclinic.domain.direccion.DatosDireccion;
import com.axelT.Medclinic.domain.paciente.*;
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
@RequestMapping("/paciente")
public class PacienteController {
            @Autowired
        private PacienteRepository pacienteRepository;

        //Segun los standard de Rest, para un get se deberia devolver:
        // 201 Created,
        // y ademas deberia devolver la URL del medico creado con un metodo GET:
        //GET http://localhost:8080/paciente/xx(id del medico)

        @PostMapping
        public ResponseEntity<DatosRespuestaPaciente> registrarMedico(
                @RequestBody @Valid DatosRegistroPaciente DatosRegistroPaciente,
                UriComponentsBuilder uriComponentsBuilder) {
            Paciente paciente = pacienteRepository.save(new Paciente(DatosRegistroPaciente));
            DatosRespuestaPaciente datosRespuestaPaciente = new DatosRespuestaPaciente(paciente.getId(),
                    paciente.getNombre(), paciente.getTelefono(), paciente.getDocumento(),
                    paciente.getEmail(), paciente.getObra_social(),
                    paciente.getNumero_afiliado(), new DatosDireccion(paciente.getDireccion().calle(),
                            paciente.getDireccion().numero(),
                            paciente.getDireccion().distrito(),
                            paciente.getDireccion().ciudad(),
                            paciente.getDireccion().complemento()));
            URI url = uriComponentsBuilder.path("paciente/{id}").buildAndExpand(paciente.getId()).toUri();
            return ResponseEntity.created(url).body(datosRespuestaPaciente);
        }


        @GetMapping
        public  ResponseEntity<Page<DatosListadoPaciente>> listadoPaciente (
                @PageableDefault(size = 10, sort = "nombre") Pageable paginacion) {
//        return medicoRepository.findAll(paginacion).map(DatosListadoMedico::new);
            return ResponseEntity.ok(pacienteRepository
                    .findByActivoTrue(paginacion)
                    .map(DatosListadoPaciente::new));
        }

        //Cuando hacemos un Update es una buena practica devolver
        // el body de la entidad que se actualizo

        @PutMapping
        @Transactional  // opcion para indica el inicio y para que finalmente haga el commit a nivel de DB
        public ResponseEntity actualizarPaciente(
                @RequestBody @Valid DatosActualizarPaciente datosActualizarPaciente){
            //Aqui traigo los datos del paciente que ingreso el cliente desde mi base de datos(medicoRepository)
            Paciente paciente = pacienteRepository.getReferenceById(datosActualizarPaciente.id());

            //Aqui le datos la instruccion de lo que debe hacer con la instancia "paciente" que
            //trajo desde mi DB.
            paciente.actualizarDatos(datosActualizarPaciente);
            return ResponseEntity.ok(new DatosRespuestaPaciente(paciente.getId(), paciente.getNombre(),
                    paciente.getTelefono(), paciente.getDocumento(), paciente.getEmail(),
                    paciente.getObra_social(), paciente.getNumero_afiliado(),
                    new DatosDireccion(paciente.getDireccion().calle(),
                            paciente.getDireccion().numero(),
                            paciente.getDireccion().distrito(),
                            paciente.getDireccion().ciudad(),
                            paciente.getDireccion().complemento())));
        }
        @DeleteMapping("/{id}") //dentro del parentesis mapeamos la URL que viene de nuestro cliente
        @Transactional
        //Para controlar el codigo de respuesta utilizamos el ResponseEntity
        public ResponseEntity eliminarPaciente(@PathVariable Long id ){
            Paciente paciente = pacienteRepository.getReferenceById(id);
            paciente.desactivarPaciente(paciente);
            return ResponseEntity.noContent().build();
        }

        @GetMapping("/{id}") //dentro del parentesis mapeamos la URL que viene de nuestro cliente
        //Para controlar el codigo de respuesta utilizamos el ResponseEntity
        public ResponseEntity<DatosRespuestaPaciente> retornaDatosPaciente(@PathVariable Long id ) {
            Paciente paciente = pacienteRepository.getReferenceById(id);
            var datosPaciente = new DatosRespuestaPaciente(paciente.getId(), paciente.getNombre(),
                    paciente.getTelefono(), paciente.getDocumento(), paciente.getEmail(),
                    paciente.getObra_social(), paciente.getNumero_afiliado(),
                    new DatosDireccion(paciente.getDireccion().calle(),
                            paciente.getDireccion().numero(),
                            paciente.getDireccion().distrito(),
                            paciente.getDireccion().ciudad(),
                            paciente.getDireccion().complemento()));
            return ResponseEntity.ok(datosPaciente);
        }
}

