package com.axelT.Medclinic.domain.medico;

import com.axelT.Medclinic.domain.direccion.DatosDireccion;

public record DatosRespuestaMedico(Long id, String nombre, String documento, String email,
                                   String telefono, Especialidad especialidad,
                                   DatosDireccion direccion) {
}
