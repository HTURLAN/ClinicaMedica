package com.axelT.Medclinic.domain.medico;

import com.axelT.Medclinic.domain.direccion.DatosDireccion;
import jakarta.persistence.*;
import lombok.*;

@Table(name="medicos")
@Entity(name="Medico")
@Data
//@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean activo;
    private String nombre;
    private String email;
    private String documento;
    private String telefono;
    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;
    @Embedded
    private DatosDireccion direccion;

    public Medico() {
    }

    public Medico(DatosRegistroMedico datosRegistroMedico) {
        this.activo = true;
        this.nombre = datosRegistroMedico.nombre();
        this.email = datosRegistroMedico.email();
        this.documento = datosRegistroMedico.documento();
        this.telefono = datosRegistroMedico.telefono();
        this.especialidad = datosRegistroMedico.especialidad();
        this.direccion = datosRegistroMedico.direccion();
    }

    public void actualizarDatos(DatosActualizarMedico datosActualizarMedico) {
        if (datosActualizarMedico.nombre() != null ) {
            this.nombre = datosActualizarMedico.nombre();
        }
        if (datosActualizarMedico.telefono() != null) {
            this.telefono = datosActualizarMedico.telefono();
        }
        if (datosActualizarMedico.direccion() != null) {
            this.direccion = direccion.actualizarDatos(datosActualizarMedico.direccion());
        }

    }

    public void desactivarMedico(Medico medico) {
        this.activo = false;
    }
}
