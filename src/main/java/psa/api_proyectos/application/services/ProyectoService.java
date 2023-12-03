package psa.api_proyectos.application.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import psa.api_proyectos.application.dtos.ProyectoRequestDto;
import psa.api_proyectos.application.dtos.ProyectoResponseDto;
import psa.api_proyectos.application.dtos.TareaRequestDto;
import psa.api_proyectos.application.dtos.TareaTicketRequestDto;
import psa.api_proyectos.application.exceptions.*;
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
    private TareaRepository tareaRepository;
    @Autowired
    private TareaService tareaService;
    @Autowired
    private ColaboradorService colaboradorService;
    @Autowired
    private TareaTicketService tareaTicketService;

    public ArrayList<Tarea> getTareasByProyectoId(Long proyectoId) {
        return (ArrayList<Tarea>) tareaRepository.findAllByProyecto_Id(proyectoId);
    }

    public ArrayList<Proyecto> getProyectos() {
        return (ArrayList<Proyecto>) proyectoRepository.findAll();
    }

    private void validarDatosProyecto(ProyectoRequestDto dto) throws JsonProcessingException {
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

        if (dto.getLiderId() == null || !colaboradorService.colaboradorExists(dto.getLiderId())) {
            errores.put("liderId", "No existe un 'Lider' con legajo " + dto.getLiderId());
        }

        if (!errores.isEmpty()) {
            throw new ProyectoInvalidoException(errores);
        }
    }

    public Proyecto saveProyecto(ProyectoRequestDto dto) throws JsonProcessingException {
        validarDatosProyecto(dto);

        Proyecto proyecto = new Proyecto();
        mapProyectoDtoToProyecto(proyecto, dto);
        proyectoRepository.save(proyecto);

        return proyecto;
    }

    public ProyectoEstado getProyectoEstadoByIdm(long estadoIdm) {
        Optional<ProyectoEstado> estadoOptional = proyectoEstadoRepository.findById(estadoIdm);
        return estadoOptional.orElse(null);
    }

    private void validarDatosTarea(TareaRequestDto dto, Long proyectoId) throws JsonProcessingException {
        HashMap<String, String> errores = new HashMap<>();

        if (!existsProyecto(proyectoId))
            errores.put("proyecto", "No existe un 'Proyecto' con Id " + proyectoId);
        if (dto.getDescripcion() == null) {
            errores.put("descripcion", "'Descripcion' es obligatorio");
        } else if (dto.getDescripcion().length() > 500) {
            errores.put("descripcion", "'Descripcion' debe tener menos de 500 caracteres");
        }
        if (dto.getEstadoIdm() == null) {
            errores.put("estado", "'Estado' es obligatorio");
        } else {
            if (!tareaService.existsTareaEstado(dto.getEstadoIdm()))
                errores.put("estado", "No existe un 'Estado' de Tarea con Idm " + dto.getEstadoIdm());
        }

        // Por el momento se considera que fechaInicio y fechaFin no son obligatorios
        if (dto.getFechaInicio() != null && dto.getFechaFin() != null && dto.getFechaInicio().after(dto.getFechaFin())) {
            errores.put("fechaInicio", "'Fecha de Inicio' no puede ser posterior a 'Fecha de Fin'");
        }

        if (!colaboradorService.colaboradorExists(dto.getColaboradorAsignadoId())) {
            errores.put("colaboradorAsignadoId", "No existe un 'Colaborador' con legajo " + dto.getColaboradorAsignadoId());
        }

        if (!errores.isEmpty()) {
            throw new TareaInvalidaException(errores);
        }
    }

    public Tarea saveTarea(TareaRequestDto dto, Long proyectoId) throws JsonProcessingException {
        validarDatosTarea(dto, proyectoId);

        TareaEstado estado = tareaService.getTareaEstadoByIdm(dto.getEstadoIdm());

        Proyecto proyecto = this.getProyectoById(proyectoId);

        Tarea nuevaTarea = new Tarea();
        nuevaTarea.descripcion = dto.getDescripcion();
        nuevaTarea.estado = estado;
        nuevaTarea.proyecto = proyecto;
        nuevaTarea.fechaInicio = dto.getFechaInicio();
        nuevaTarea.fechaFin = dto.getFechaFin();
        nuevaTarea.colaboradorAsignadoId = dto.getColaboradorAsignadoId();

        tareaRepository.save(nuevaTarea);

        // TODO: pegarle a la API de Soporte para validar existencia de Ticket
        // TODO: validar existencia de Tarea
        if (dto.getTicketIds() != null) {
            for (Long ticketId:
                    dto.getTicketIds()) {
                TareaTicketRequestDto tareaTicket = new TareaTicketRequestDto();
                tareaTicket.tareaId = nuevaTarea.id;
                tareaTicket.ticketId = ticketId;
                tareaTicketService.createAsociacionTareaTicket(tareaTicket);
            }
        }

        return nuevaTarea;
    }

    public Proyecto getProyectoById(Long proyectoId) {
        Proyecto proyectoADevolver = proyectoRepository.findById(proyectoId).orElse(null);
        if (proyectoADevolver == null) {
            throw new ProyectoNoEncontradoException("No existe el proyecto " + proyectoId + ".");
        }
        return proyectoADevolver;
    }

    public boolean existsProyecto(Long proyectoId) {
        return (proyectoRepository.existsById(proyectoId));
    }

    public void updateProyecto(ProyectoRequestDto dto, Long proyectoId) throws JsonProcessingException {
        validarDatosProyecto(dto);

        Proyecto proyecto = getProyectoById(proyectoId);
        mapProyectoDtoToProyecto(proyecto, dto);
        proyectoRepository.save(proyecto);
    }

    public void updateTarea(TareaRequestDto dto, Long proyectoId, Long tareaId) throws JsonProcessingException {
        validarDatosTarea(dto, proyectoId);

        TareaEstado estado = tareaService.getTareaEstadoByIdm(dto.getEstadoIdm());

        Proyecto proyecto = this.getProyectoById(proyectoId);
        Tarea tarea = tareaService.getTareaById(tareaId);

        tarea.descripcion = dto.getDescripcion();
        tarea.estado = estado;
        tarea.proyecto = proyecto;
        tarea.fechaInicio = dto.getFechaInicio();
        tarea.fechaFin = dto.getFechaFin();
        tarea.colaboradorAsignadoId = dto.getColaboradorAsignadoId();

        tareaRepository.save(tarea);

        // elimino todas las tareatickets persistidas y doy de alta todas las recibidas
         tareaTicketService.deleteTareaTicketByTareaId(tareaId);
        // TODO: pegarle a la API de Soporte para validar existencia de Ticket
        // TODO: validar existencia de Tareas
        if (dto.getTicketIds() != null) {
            for (Long ticketId:
                    dto.getTicketIds()) {
                TareaTicketRequestDto tareaTicket = new TareaTicketRequestDto();
                tareaTicket.tareaId = tarea.id;
                tareaTicket.ticketId = ticketId;
                tareaTicketService.createAsociacionTareaTicket(tareaTicket);
        }
        }
    }

    private void mapProyectoDtoToProyecto(Proyecto proyecto, ProyectoRequestDto dto) {
        proyecto.setNombre(dto.getNombre());
        proyecto.setDescripcion(dto.getDescripcion());
        proyecto.setFechaInicio(dto.getFechaInicio());
        proyecto.setFechaFin(dto.getFechaFin());
        ProyectoEstado estado = this.getProyectoEstadoByIdm(dto.getEstadoIdm());
        proyecto.setEstado(estado);
        if (dto.getLiderId() != null) proyecto.setLiderAsignadoId(dto.getLiderId());
    }

    public void deleteProyectoById(Long proyectoId) {
        ArrayList<Tarea> tareas = this.getTareasByProyectoId(proyectoId);
        tareaRepository.deleteAll(tareas);
        Proyecto proyecto = this.getProyectoById(proyectoId);
        proyectoRepository.delete(proyecto);
    }

    public ArrayList<ProyectoEstado> getAllProyectoEstados() {
        return (ArrayList<ProyectoEstado>) proyectoEstadoRepository.findAll();
    }

    public ProyectoResponseDto mapToResponse(Proyecto proyecto) throws JsonProcessingException {
        ProyectoResponseDto response = new ProyectoResponseDto();

        response.id = proyecto.getId();
        response.nombre = proyecto.getNombre();
        response.descripcion = proyecto.getDescripcion();
        response.fechaInicio = proyecto.getFechaInicio();
        response.fechaFin = proyecto.getFechaFin();
        response.estadoIdm = proyecto.getEstado() != null ? proyecto.getEstado().idm : null;
        response.estado = proyecto.getEstado() != null ? proyecto.getEstado().descripcion : null;
        response.liderAsignado = proyecto.getLiderAsignadoId() != null ? colaboradorService.getColaboradorByLegajo(proyecto.getLiderAsignadoId()) : null;

        ArrayList<Tarea> tareas = getTareasByProyectoId(proyecto.getId());

        response.tareas = tareaService.mapToResponse(tareas);

        return response;
    }

    public ArrayList<ProyectoResponseDto> mapToResponse(ArrayList<Proyecto> proyectos) {
        ArrayList<ProyectoResponseDto> proyectoResponseDtos = new ArrayList<>();

        for (Proyecto proyecto : proyectos) {
            try {
                ProyectoResponseDto proyectoResponseDto = mapToResponse(proyecto);
                proyectoResponseDtos.add(proyectoResponseDto);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        return proyectoResponseDtos;
    }

}
