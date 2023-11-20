package psa.api_proyectos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import psa.api_proyectos.models.Tarea;
import psa.api_proyectos.repositories.TareaRepository;

@Service
public class TareaService {
    @Autowired
    TareaRepository tareaRepository;
    public Tarea saveTarea(Tarea tarea) {
        return tareaRepository.save(tarea);
    }
}
