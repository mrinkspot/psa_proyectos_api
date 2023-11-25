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
    @Autowired
    private ColaboradorService colaboradorService;
    @Autowired
    private ColaboradorProyectoRepository colaboradorProyectoRepository;

    public ArrayList<Tarea> getTareasByProyectoId(Long proyectoId) {
        return (ArrayList<Tarea>) tareaRepository.findAllByProyecto_Id(proyectoId);
    }

    public ArrayList<Proyecto> getProyectos() {
        ArrayList<Proyecto> proyectos = (ArrayList<Proyecto>) proyectoRepository.findAll();
        if (proyectos.isEmpty()) throw new NoExistenProyectosException("No existen proyectos");
        return proyectos;
    }

    public Proyecto saveProyecto(ProyectoDto dto) {
        HashMap<String, String> errores = new HashMap<>();

        if (dto.getNombre() == null) {
            errores.put("nombre", "'Nombre' es obligatorio");
        }
        if (dto.getDescripcion() == null) {
            errores.put("descripcion", "'Descripcion' es obligatorio");
        }
        if (dto.getEstadoIdm() == null) {
            errores.put("estado", "'Estado' es obligatorio");
        }

        // Por el momento se considera que fechaInicio y fechaFin no son obligatorios
        if (dto.getFechaInicio() != null && dto.getFechaFin() != null && dto.getFechaInicio().after(dto.getFechaFin())) {
            errores.put("fechaInicio", "'Fecha de Inicio' no puede ser posterior a 'Fecha de Fin'");
        }

        if (!errores.isEmpty()) {
            throw new ProyectoInvalidoException(errores);
        }
//        Buscamos el estado seleccionado
        ProyectoEstado estado = this.getProyectoEstadoByIdm(dto.getEstadoIdm());

        Proyecto proyecto = new Proyecto();
        proyecto.setNombre(dto.getNombre());
        proyecto.setDescripcion(dto.getDescripcion());
        proyecto.setFechaInicio(dto.getFechaInicio());
        proyecto.setFechaFin(dto.getFechaFin());
        proyecto.setEstado(estado);

        ArrayList<ColaboradorProyecto> colaboradoresProyecto = new ArrayList<>();

        colaboradoresProyecto.add(colaboradorService.createLiderProyecto(dto.getLiderId(), proyecto));

        for (Long miembroId : dto.getMiembrosIds()) {
            colaboradoresProyecto.add(colaboradorService.createMiembroProyecto(miembroId, proyecto));
        }

        proyectoRepository.save(proyecto);
        colaboradorProyectoRepository.saveAll(colaboradoresProyecto);

        return proyecto;
    }

    public ProyectoEstado getProyectoEstadoByIdm(long estadoIdm) {
        Optional<ProyectoEstado> estadoOptional = proyectoEstadoRepository.findById(estadoIdm);
        return estadoOptional.orElse(null);
    }

    public Tarea saveTarea(TareaDto dto, Long proyectoId) {
        HashMap<String, String> errores = new HashMap<>();

        if (dto.getDescripcion() == null) {
            errores.put("descripcion", "'Descripcion' es obligatorio");
        }
        if (dto.getEstadoIdm() == null) {
            errores.put("estado", "'Estado' es obligatorio");
        }

        // Por el momento se considera que fechaInicio y fechaFin no son obligatorios
        if (dto.getFechaInicio() != null && dto.getFechaFin() != null && dto.getFechaInicio().after(dto.getFechaFin())) {
            errores.put("fechaInicio", "'Fecha de Inicio' no puede ser posterior a 'Fecha de Fin'");
        }

        if (!errores.isEmpty()) {
            throw new TareaInvalidaException(errores);
        }
//        Buscamos el estado seleccionado
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

    private TareaEstado getTareaEstadoByIdm(Long estadoIdm) {
        Optional<TareaEstado> estadoOptional = tareaEstadoRepository.findById(estadoIdm);
        return estadoOptional.orElse(null);
    }
}
