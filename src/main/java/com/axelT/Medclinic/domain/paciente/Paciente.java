package com.axelT.Medclinic.domain.paciente;

import com.axelT.Medclinic.domain.direccion.DatosDireccion;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name="pacientes")
@Entity(name="Paciente")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean activo;
    private String nombre;
    private String email;
    private String documento;
    private String telefono;
    private String obra_social;
    private String numero_afiliado;
    @Embedded
    private DatosDireccion direccion;

   //

    public Paciente(DatosRegistroPaciente datosRegistroPaciente) {
        this.activo = true;
        this.nombre = datosRegistroPaciente.nombre();
        this.email = datosRegistroPaciente.email();
        this.documento = datosRegistroPaciente.documento();
        this.telefono = datosRegistroPaciente.telefono();
        this.obra_social = datosRegistroPaciente.obra_social();
        this.numero_afiliado = datosRegistroPaciente.numero_afiliado();
        this.direccion = datosRegistroPaciente.direccion();
    }

    public void actualizarDatos(@Valid DatosActualizarPaciente datosActualizarPaciente) {
        if (datosActualizarPaciente.nombre() != null ) {
            this.nombre = datosActualizarPaciente.nombre();
        }
        if (datosActualizarPaciente.telefono() != null) {
            this.telefono = datosActualizarPaciente.telefono();
        }
        if (datosActualizarPaciente.obra_social() != null) {
            this.obra_social = datosActualizarPaciente.obra_social();
        }
        if (datosActualizarPaciente.numero_afiliado() != null) {
            this.numero_afiliado = datosActualizarPaciente.numero_afiliado();
        }
        if (datosActualizarPaciente.direccion() != null) {
            this.direccion = direccion.actualizarDatos(datosActualizarPaciente.direccion());
        }

    }

    public void desactivarPaciente(Paciente paciente) {
        this.activo = false;
    }
}

