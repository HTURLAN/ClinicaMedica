package com.axelT.Medclinic.domain.medico;

import com.axelT.Medclinic.domain.direccion.DatosDireccion;
import jakarta.validation.constraints.NotNull;

public record DatosActualizarMedico(@NotNull Long id, String nombre,
                                    String telefono, DatosDireccion direccion) {

}
