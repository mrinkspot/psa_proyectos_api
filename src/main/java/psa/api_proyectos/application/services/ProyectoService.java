package psa.api_proyectos.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import psa.api_proyectos.application.dtos.ProyectoDto;
import psa.api_proyectos.application.dtos.TareaDto;
import psa.api_proyectos.application.exceptions.NoExisteElProyectoPedidoException;
import psa.api_proyectos.application.exceptions.NoExistenProyectosException;
import psa.api_proyectos.application.exceptions.ProyectoInvalidoException;
import psa.api_proyectos.application.exceptions.TareaInvalidaException;
import psa.api_proyectos.data.repositories.*;
import psa.api_proyectos.domain.models.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;


@Service
public class ProyectoService {
    @Autowired
    private ProyectoRepository proyectoRepository;
    @Autowired
    private ProyectoEstadoRepository proyectoEstadoRepository;
    @Autowired
    private TareaEstadoRepository tareaEstadoRepository;
    @Autowired
    private TareaRepository tareaRepository;

    public ArrayList<Tarea> getTareasByProyectoId(Long proyectoId) {
        return (ArrayList<Tarea>) tareaRepository.findAllByProyecto_Id(proyectoId);
    }

    public ArrayList<Proyecto> getProyectos() {
        ArrayList<Proyecto> proyectos = (ArrayList<Proyecto>) proyectoRepository.findAll();
        if (proyectos.isEmpty()) throw new NoExistenProyectosException("No existen proyectos");
        return proyectos;
    }

    private void validarDatosProyecto(ProyectoDto dto) {
        HashMap<String, String> errores = new HashMap<>();

        if (dto.getNombre() == null) {
            errores.put("nombre", "'Nombre' es obligatorio");
        } else if (dto.getNombre().length() > 50) {
            errores.put("nombre", "'Nombre' debe tener menos de 50 caracteres");
        }
        if (dto.getDescripcion() == null) {
            errores.put("descripcion", "'Descripcion' es obligatorio");
        } else if (dto.getDescripcion().length() > 1000) {
            errores.put("descripcion", "'Descripcion' debe tener menos de 1000 caracteres");
        }
        if (dto.getEstadoIdm() == null) {
            errores.put("estado", "'Estado' es obligatorio");
        } else {
            if (!proyectoEstadoRepository.existsById(dto.getEstadoIdm())) errores.put("estado", "No existe un 'Estado' de Proyecto con Idm " + dto.getEstadoIdm());
        }

        // Por el momento se considera que fechaInicio y fechaFin no son obligatorios
        if (dto.getFechaInicio() != null && dto.getFechaFin() != null && dto.getFechaInicio().after(dto.getFechaFin())) {
            errores.put("fechaInicio", "'Fecha de Inicio' no puede ser posterior a 'Fecha de Fin'");
        }

        if (!errores.isEmpty()) {
            throw new ProyectoInvalidoException(errores);
        }
    }

    public Proyecto saveProyecto(ProyectoDto dto) {
        validarDatosProyecto(dto);

        Proyecto proyecto = new Proyecto();
        proyecto = mapProyectoDtoToProyecto(proyecto, dto);
        proyectoRepository.save(proyecto);

        return proyecto;
    }

    public ProyectoEstado getProyectoEstadoByIdm(long estadoIdm) {
        Optional<ProyectoEstado> estadoOptional = proyectoEstadoRepository.findById(estadoIdm);
        return estadoOptional.orElse(null);
    }

    private void validarDatosTarea(TareaDto dto, Long proyectoId) {
        HashMap<String, String> errores = new HashMap<>();

        if (!proyectoRepository.existsById(proyectoId))
            errores.put("proyecto", "No existe un 'Proyecto' con Id " + proyectoId);
        if (dto.getDescripcion() == null) {
            errores.put("descripcion", "'Descripcion' es obligatorio");
        } else if (dto.getDescripcion().length() > 500) {
            errores.put("descripcion", "'Descripcion' debe tener menos de 500 caracteres");
        }
        if (dto.getEstadoIdm() == null) {
            errores.put("estado", "'Estado' es obligatorio");
        } else {
            if (!tareaEstadoRepository.existsById(dto.getEstadoIdm()))
                errores.put("estado", "No existe un 'Estado' de Tarea con Idm " + dto.getEstadoIdm());
        }

        // Por el momento se considera que fechaInicio y fechaFin no son obligatorios
        if (dto.getFechaInicio() != null && dto.getFechaFin() != null && dto.getFechaInicio().after(dto.getFechaFin())) {
            errores.put("fechaInicio", "'Fecha de Inicio' no puede ser posterior a 'Fecha de Fin'");
        }

        if (!errores.isEmpty()) {
            throw new TareaInvalidaException(errores);
        }
    }

    public Tarea saveTarea(TareaDto dto, Long proyectoId) {
        validarDatosTarea(dto, proyectoId);

        // Buscamos el estado seleccionado
        TareaEstado estado = this.getTareaEstadoByIdm(dto.getEstadoIdm());

        Proyecto proyecto = this.getProyectoById(proyectoId);

        Tarea nuevaTarea = new Tarea();
        nuevaTarea.descripcion = dto.getDescripcion();
        nuevaTarea.estado = estado;
        nuevaTarea.proyecto = proyecto;
        nuevaTarea.fechaInicio = dto.getFechaInicio();
        nuevaTarea.fechaFin = dto.getFechaFin();

        tareaRepository.save(nuevaTarea);

        return nuevaTarea;
    }

    public Proyecto getProyectoById(Long proyectoId) {
        Proyecto proyectoADevolver = proyectoRepository.findById(proyectoId).orElse(null);
        if (proyectoADevolver == null) {
            throw new NoExisteElProyectoPedidoException("No existe el proyecto de Id: " + proyectoId + ".");
        }
        return proyectoADevolver;
    }

    public boolean existsProyecto(Long proyectoId) {
        return (proyectoRepository.findById(proyectoId).orElse(null) != null);
    }

    public void modifyProyecto(ProyectoDto dto, Long proyectoId) {
        validarDatosProyecto(dto);

        Proyecto proyecto = getProyectoById(proyectoId);
        proyecto = mapProyectoDtoToProyecto(proyecto, dto);
        proyectoRepository.save(proyecto);
    }

    private TareaEstado getTareaEstadoByIdm(Long estadoIdm) {
        Optional<TareaEstado> estadoOptional = tareaEstadoRepository.findById(estadoIdm);
        return estadoOptional.orElse(null);
    }

    private Proyecto mapProyectoDtoToProyecto(Proyecto proyecto, ProyectoDto dto) {
        proyecto.setNombre(dto.getNombre());
        proyecto.setDescripcion(dto.getDescripcion());
        proyecto.setFechaInicio(dto.getFechaInicio());
        proyecto.setFechaFin(dto.getFechaFin());
        ProyectoEstado estado = this.getProyectoEstadoByIdm(dto.getEstadoIdm());
        proyecto.setEstado(estado);
        proyecto.setLiderAsignadoId(dto.getLiderId());

        return proyecto;
    }
}
