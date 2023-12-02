package psa.api_proyectos.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import psa.api_proyectos.application.dtos.TareaTicketRequestDto;
import psa.api_proyectos.application.dtos.TareaTicketResponseDto;
import psa.api_proyectos.application.exceptions.YaExisteRelacionTareaTicketException;
import psa.api_proyectos.data.repositories.TareaTicketRepository;
import psa.api_proyectos.domain.models.TareaTicket;

import java.util.ArrayList;

@Service
public class TareaTicketService {
    @Autowired
    TareaTicketRepository tareaTicketRepository;

    // TODO: ver si es necesario validar que exista Tarea y Ticket
    public TareaTicket createAsociacionTareaTicket(TareaTicketRequestDto request) {
        boolean existsTareaTicket = tareaTicketRepository.findByTareaIdAndTicketId(request.tareaId, request.ticketId) != null;
        if (existsTareaTicket)
            throw new YaExisteRelacionTareaTicketException(String.format("Ya existe relación entre la Tarea %d y el Ticket %d", request.tareaId, request.ticketId));

        TareaTicket tareaTicket = new TareaTicket();
        tareaTicket.tareaId = request.tareaId;
        tareaTicket.ticketId = request.ticketId;
        return tareaTicketRepository.save(tareaTicket);
    }

    // TODO: tendria que hacer tres deletes por id?

    public ArrayList<TareaTicket> getAllTareaTicketByTicketId(long ticketId) {
        return (ArrayList<TareaTicket>) tareaTicketRepository.findAllByTicketId(ticketId);
    }

    public ArrayList<TareaTicket> getAllTareaTicketByTareaId(long tareaId) {
        return (ArrayList<TareaTicket>) tareaTicketRepository.findAllByTareaId(tareaId);
    }

    public void deleteTareaTicketByTareaId(Long tareaId) {
        ArrayList<TareaTicket> tareaTickets = (ArrayList<TareaTicket>) tareaTicketRepository.findAllByTareaId(tareaId);
        tareaTicketRepository.deleteAll(tareaTickets);
    }

    public void deleteTareaTicketByTicketId(Long ticketId) {
        ArrayList<TareaTicket> tareaTickets = (ArrayList<TareaTicket>) tareaTicketRepository.findAllByTicketId(ticketId);
        tareaTicketRepository.deleteAll(tareaTickets);
    }

    public TareaTicketResponseDto mapToResponse(TareaTicket tareaTicket) {
        TareaTicketResponseDto response = new TareaTicketResponseDto();
        response.tareaId = tareaTicket.tareaId;
        response.ticketId = tareaTicket.ticketId;
        return response;
    }

    public ArrayList<TareaTicketResponseDto> mapToResponse(ArrayList<TareaTicket> tareaTickets) {
        ArrayList<TareaTicketResponseDto> tareaTicketResponseDtos = new ArrayList<>();

        for (TareaTicket tareaTicket : tareaTickets) {
                TareaTicketResponseDto tareaTicketResponseDto = mapToResponse(tareaTicket);
                tareaTicketResponseDtos.add(tareaTicketResponseDto);
        }
        return tareaTicketResponseDtos;
    }
}

