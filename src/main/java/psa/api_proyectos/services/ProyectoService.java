package psa.api_proyectos.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import psa.api_proyectos.dtos.ProyectoDto;
import psa.api_proyectos.exceptions.ProyectoInvalidoException;
import psa.api_proyectos.models.Proyecto;
import psa.api_proyectos.models.ProyectoEstado;
import psa.api_proyectos.models.Tarea;
import psa.api_proyectos.repositories.ProyectoEstadoRepository;
import psa.api_proyectos.repositories.ProyectoRepository;
import psa.api_proyectos.repositories.TareaRepository;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class ProyectoService {
    @Autowired
    private ProyectoRepository proyectoRepository;
    @Autowired
    private ProyectoEstadoRepository proyectoEstadoRepository;
    @Autowired
    private TareaRepository tareaRepository;

    public ArrayList<Tarea> getTareasByProyectoId(Long proyectoId) {
        return (ArrayList<Tarea>) tareaRepository.findAllByProyecto_Id(proyectoId);
    }

    public ArrayList<Proyecto> getProyectos() {
        return (ArrayList<Proyecto>) proyectoRepository.findAll();
    }
    @Transactional
    public Proyecto saveProyecto(ProyectoDto dto) {
        if (dto.getNombre().isEmpty()) {
            throw new ProyectoInvalidoException("'Nombre' es obligatorio");
        }
        if (dto.getDescripcion().isEmpty()) {
            throw new ProyectoInvalidoException("'Descripcion' es obligatorio");
        }
        if (dto.getEstadoIdm() == null) {
            throw new ProyectoInvalidoException("'Estado' es obligatorio");
        }
//        if (dto.getFechaInicio() == null) {
//            throw new ProyectoInvalidoException("'Fecha de Inicio' es obligatorio");
//        }
//        if (dto.getFechaFin() == null) {
//            throw new ProyectoInvalidoException("'Fecha de Fin' es obligatorio");
//        }

        // Por el momento se considera que fechaInicio y fechaFin no son obligatorios
        if (dto.getFechaInicio() != null && dto.getFechaFin() != null && dto.getFechaInicio().after(dto.getFechaFin())) {
            throw new ProyectoInvalidoException("'Fecha de Inicio' no puede ser posterior a 'Fecha de Fin'");
        }

//        Buscamos el estado seleccionado
        ProyectoEstado estado = this.getProyectoEstadoByIdm(dto.getEstadoIdm());

        Proyecto proyecto = new Proyecto();
        proyecto.setNombre(dto.getNombre());
        proyecto.setDescripcion(dto.getDescripcion());
        proyecto.setFechaInicio(dto.getFechaInicio());
        proyecto.setFechaFin(dto.getFechaFin());
        proyecto.setEstado(estado);

        return proyectoRepository.save(proyecto);
    }

    public ProyectoEstado getProyectoEstadoByIdm(long estadoIdm) {
        Optional<ProyectoEstado> estadoOptional = proyectoEstadoRepository.findById(estadoIdm);
        return estadoOptional.orElse(null);
    }
}
