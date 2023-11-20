package psa.api_proyectos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import psa.api_proyectos.models.Proyecto;
import psa.api_proyectos.repositories.ProyectoRepository;

import java.util.ArrayList;

@Service
public class ProyectoService {
    @Autowired
    ProyectoRepository proyectoRepository;

    public ArrayList<Proyecto> getProyectos() {
        return (ArrayList<Proyecto>) proyectoRepository.findAll();
    }

    public Proyecto saveProyecto(Proyecto proyecto) {
        return proyectoRepository.save(proyecto);
    }
}
