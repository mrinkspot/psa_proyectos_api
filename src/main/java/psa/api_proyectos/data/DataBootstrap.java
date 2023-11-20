package psa.api_proyectos.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import psa.api_proyectos.models.ProyectoEstado;
import psa.api_proyectos.models.TareaEstado;
import psa.api_proyectos.repositories.ProyectoEstadoRepository;
import psa.api_proyectos.repositories.TareaEstadoRepository;

@Component
public class DataBootstrap implements CommandLineRunner {

    @Autowired
    private ProyectoEstadoRepository proyectoEstadoRepository;
    @Autowired
    private TareaEstadoRepository tareaEstadoRepository;

    @Override
    public void run(String...args) {
        proyectoEstadoRepository.save(new ProyectoEstado(ProyectoEstado.NO_INICIADO_IDM, ProyectoEstado.NO_INICIADO_DESCRIPCION));
        proyectoEstadoRepository.save(new ProyectoEstado(ProyectoEstado.EN_PROGRESO_IDM, ProyectoEstado.EN_PROGRESO_DESCRIPCION));
        proyectoEstadoRepository.save(new ProyectoEstado(ProyectoEstado.TERMINADO_IDM, ProyectoEstado.TERMINADO_DESCRIPCION));

        tareaEstadoRepository.save(new TareaEstado(TareaEstado.NUEVA_IDM, TareaEstado.NUEVA_DESCRIPCION));
        tareaEstadoRepository.save(new TareaEstado(TareaEstado.EN_CURSO_IDM, TareaEstado.EN_CURSO_DESCRIPCION));
        tareaEstadoRepository.save(new TareaEstado(TareaEstado.TERMINADA_IDM, TareaEstado.TERMINADA_DESCRIPCION));
        tareaEstadoRepository.save(new TareaEstado(TareaEstado.EN_ESPERA_IDM, TareaEstado.EN_ESPERA_DESCRIPCION));
    }
}