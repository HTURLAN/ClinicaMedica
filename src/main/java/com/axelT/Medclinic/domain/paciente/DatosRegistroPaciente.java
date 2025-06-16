package com.axelT.Medclinic.domain.paciente;

import com.axelT.Medclinic.domain.direccion.DatosDireccion;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DatosRegistroPaciente(
    @NotBlank
    String nombre,
    @NotBlank
    @Email
    String email,
    @NotBlank
    @Pattern(regexp = "\\d{4,8}")
    String documento,
    @NotBlank
    String telefono,
    String obra_social,
    @Pattern(regexp = "\\d{10}")
    String numero_afiliado,
    @NotNull
    @Valid
    DatosDireccion direccion) {
}
