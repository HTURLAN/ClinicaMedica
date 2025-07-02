package com.axelT.Medclinic.domain.Consulta;

import com.axelT.Medclinic.domain.medico.Especialidad;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record DatosAgendarConsulta(Long id, @NotNull Long idPaciente, Long idMedico,
                                   @NotNull @Future LocalDateTime fecha, Especialidad especialidad) {
}
