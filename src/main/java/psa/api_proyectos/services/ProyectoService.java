package psa.api_proyectos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import psa.api_proyectos.models.Proyecto;
import psa.api_proyectos.models.Tarea;
import psa.api_proyectos.repositories.ProyectoRepository;
import psa.api_proyectos.repositories.TareaRepository;

import java.util.ArrayList;

@Service
public class ProyectoService {
    @Autowired
    private ProyectoRepository proyectoRepository;
    @Autowired
    private TareaRepository tareaRepository;

    public ArrayList<Tarea> getTareasByProyectoId(Long proyectoId) {
        return (ArrayList<Tarea>) tareaRepository.findAllByProyecto_Id(proyectoId);
    }

    public ArrayList<Proyecto> getProyectos() {
        return (ArrayList<Proyecto>) proyectoRepository.findAll();
    }

    public Proyecto saveProyecto(Proyecto proyecto) {
        // Faltan reglas de negocio y hacer el save si todo está ok, sino lanzar excepción
        return proyectoRepository.save(proyecto);
    }
}
