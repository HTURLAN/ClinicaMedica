package com.axelT.Medclinic.domain.paciente;

import com.axelT.Medclinic.domain.direccion.DatosDireccion;

public record DatosRespuestaPaciente(Long id, String nombre, String documento, String email,
                                     String telefono, String obra_social, String numero_afiliado,
                                     DatosDireccion direccion) {
}
