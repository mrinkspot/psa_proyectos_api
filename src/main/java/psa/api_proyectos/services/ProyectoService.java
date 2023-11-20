package psa.api_proyectos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import psa.api_proyectos.models.ProyectoModel;
import psa.api_proyectos.repositories.ProyectoRepository;

import java.util.ArrayList;

@Service
public class ProyectoService {
    @Autowired
    ProyectoRepository proyectoRepository;

    public ArrayList<ProyectoModel> getProyectos() {
        return (ArrayList<ProyectoModel>) proyectoRepository.findAll();
    }

    public ProyectoModel saveProyecto(ProyectoModel proyecto) {
        return proyectoRepository.save(proyecto);
    }
}
