package com.axelT.Medclinic.domain.Consulta;

import java.time.LocalDateTime;

public record DatosDetalleConsulta(Long id, Long idPaciente, Long idMedico, LocalDateTime fecha) {
}
