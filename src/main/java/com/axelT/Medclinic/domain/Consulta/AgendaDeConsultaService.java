package com.axelT.Medclinic.domain.Consulta;

import com.axelT.Medclinic.domain.medico.Medico;
import com.axelT.Medclinic.domain.medico.MedicoRepository;
import com.axelT.Medclinic.domain.paciente.PacienteRepository;
import com.axelT.Medclinic.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendaDeConsultaService {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    public void agendar(DatosAgendarConsulta datos){

        if(pacienteRepository.findById(datos.idPaciente()).isPresent()){
            throw new ValidacionDeIntegridad("Este id de paciente no fue encuentrado");
        }

        if(datos.idMedico()!= null && medicoRepository.existsById(datos.idMedico())){
            throw new ValidacionDeIntegridad("Este id de médico no fue encuentrado");
        }

        var paciente = pacienteRepository.findById(datos.idPaciente()).get();

        var medico = seleccionarMedico(datos);

        var consulta = new Consulta(null, medico,paciente,datos.fecha());

        consultaRepository.save(consulta);
    }

    private Medico seleccionarMedico(DatosAgendarConsulta datos) {
        if(datos.idMedico()!=null){
        return medicoRepository.getReferenceById(datos.idMedico());
    }
        if(datos.especialidad() == null){
            throw new ValidacionDeIntegridad("Debe seleccionar una especialidad" +
                    " para poder asignarle un médico");
        }

        return medicoRepository.seleccionarMedicoPorEspecialidadEnFecha(datos.especialidad(), datos.fecha());
    }
}
