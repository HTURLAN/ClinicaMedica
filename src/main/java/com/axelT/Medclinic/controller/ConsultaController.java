package com.axelT.Medclinic.controller;

import com.axelT.Medclinic.domain.Consulta.AgendaDeConsultaService;
import com.axelT.Medclinic.domain.Consulta.DatosAgendarConsulta;
import com.axelT.Medclinic.domain.Consulta.DatosDetalleConsulta;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@ResponseBody
@RequestMapping("/consultas")

public class ConsultaController {

    @Autowired
    private AgendaDeConsultaService service;

    @PostMapping
    @Transactional
    public ResponseEntity agendarConsulta(@RequestBody @Valid DatosAgendarConsulta datosAgendarConsulta) {

        service.agendar(datosAgendarConsulta);
        return ResponseEntity.ok(new DatosDetalleConsulta(null,null,null,null));

    }
}
