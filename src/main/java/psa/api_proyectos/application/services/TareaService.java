package psa.api_proyectos.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import psa.api_proyectos.application.exceptions.TareaNoEncontradaException;
import psa.api_proyectos.data.repositories.TareaEstadoRepository;
import psa.api_proyectos.domain.models.Tarea;
import psa.api_proyectos.data.repositories.TareaRepository;
import psa.api_proyectos.domain.models.TareaEstado;

import java.util.Optional;

@Service
public class TareaService {
    @Autowired
    TareaRepository tareaRepository;
    @Autowired
    TareaEstadoRepository tareaEstadoRepository;

    public Tarea saveTarea(Tarea tarea) {
        return tareaRepository.save(tarea);
    }

    public Tarea getTareaById(Long tareaId) {
        Tarea tarea = tareaRepository.findById(tareaId).orElse(null);
        if (tarea == null) {
            throw new TareaNoEncontradaException("No existe la Tarea " + tareaId + ".");
        }
        return tarea;
    }

    public void deleteTareaById(Long tareaId) {
        Tarea tarea = this.getTareaById(tareaId);
        tareaRepository.delete(tarea);
    }
    public boolean existsTarea(Long tareaId) {
        return tareaRepository.existsById(tareaId);
    }

    public TareaEstado getTareaEstadoByIdm(Long estadoIdm) {
        Optional<TareaEstado> estadoOptional = tareaEstadoRepository.findById(estadoIdm);
        return estadoOptional.orElse(null);
    }

    public boolean existsTareaEstado(Long tareaId) {
        return tareaEstadoRepository.existsById(tareaId);
    }
}
