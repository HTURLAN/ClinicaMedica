package com.axelT.Medclinic.domain.paciente;

public record DatosListadoPaciente(Long id, String nombre, String documento, String email,
                                   String telefono, String obra_social, String numero_afiliado) {

    public DatosListadoPaciente(Paciente paciente){
        this(paciente.getId(), paciente.getNombre(),
                paciente.getDocumento(), paciente.getEmail(),
                paciente.getTelefono(),paciente.getObra_social(),
                paciente.getNumero_afiliado());
    }
}
