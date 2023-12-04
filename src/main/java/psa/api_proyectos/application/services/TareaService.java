package psa.api_proyectos.application.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import psa.api_proyectos.application.dtos.TareaResponseDto;
import psa.api_proyectos.application.exceptions.TareaNoEncontradaException;
import psa.api_proyectos.data.repositories.TareaEstadoRepository;
import psa.api_proyectos.domain.models.Tarea;
import psa.api_proyectos.data.repositories.TareaRepository;
import psa.api_proyectos.domain.models.TareaEstado;
import psa.api_proyectos.domain.models.TareaTicket;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TareaService {
    @Autowired
    TareaRepository tareaRepository;
    @Autowired
    TareaEstadoRepository tareaEstadoRepository;
    @Autowired
    ColaboradorService colaboradorService;
    @Autowired
    TareaTicketService tareaTicketService;

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

    public ArrayList<Tarea> getTareas() {
        return (ArrayList<Tarea>) tareaRepository.findAll();
    }

    public void deleteTareaById(Long tareaId) {
        tareaTicketService.deleteTareaTicketByTareaId(tareaId);
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

    public TareaResponseDto mapToResponse(Tarea tarea) throws JsonProcessingException {
        TareaResponseDto response = new TareaResponseDto();

        response.id = tarea.id; // TODO: consultar si es necesario el id para los test, preferiria no exponerlo
        response.descripcion = tarea.descripcion;
        response.fechaInicio = tarea.fechaInicio;
        response.fechaFin = tarea.fechaFin;
        response.estadoIdm = tarea.estado != null ? tarea.estado.idm : null;
        response.estado = tarea.estado != null ? tarea.estado.descripcion : null;
        response.colaboradorAsignado = tarea.colaboradorAsignadoId != null  && tarea.colaboradorAsignadoId != 0 ? colaboradorService.getColaboradorByLegajo(tarea.colaboradorAsignadoId) : null;

        // obtengo los TareaTicket asociados a la Tarea actual y me quedo solo con los TicketId
        response.ticketIds = (ArrayList<Long>) tareaTicketService.getAllTareaTicketByTareaId(tarea.id)
                .stream()
                .map(TareaTicket::getTicketId)
                .collect(Collectors.toList());

        return response;
    }

    public ArrayList<TareaEstado> getAllTareaEstados() {
        return (ArrayList<TareaEstado>) tareaEstadoRepository.findAll();
    }

    public ArrayList<TareaResponseDto> mapToResponse(ArrayList<Tarea> tareas) {
        ArrayList<TareaResponseDto> tareaResponseDtos = new ArrayList<>();

        for (Tarea tarea : tareas) {
            try {
                TareaResponseDto tareaResponseDto = mapToResponse(tarea);
                tareaResponseDtos.add(tareaResponseDto);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        return tareaResponseDtos;
    }

}
