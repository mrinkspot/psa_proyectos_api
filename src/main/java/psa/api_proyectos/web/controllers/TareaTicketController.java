package psa.api_proyectos.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import psa.api_proyectos.application.dtos.TareaTicketRequestDto;
import psa.api_proyectos.application.dtos.TareaTicketResponseDto;
import psa.api_proyectos.application.exceptions.ErrorMessage;
import psa.api_proyectos.application.exceptions.YaExisteRelacionTareaTicketException;
import psa.api_proyectos.application.services.TareaTicketService;
import psa.api_proyectos.domain.models.TareaTicket;

import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/tareaTicket")
public class TareaTicketController {
    @Autowired
    TareaTicketService tareaTicketService;

    @GetMapping("/tarea/{tareaId}")
    public ResponseEntity<?> getTareaTicketByTareaId(@PathVariable Long tareaId) {
        ArrayList<TareaTicket> tareaTickets = tareaTicketService.getAllTareaTicketByTareaId(tareaId);
        return new ResponseEntity<>(tareaTicketService.mapToResponse(tareaTickets), HttpStatus.OK);
    }

    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity<?> getTareaTicketByTicketId(@PathVariable Long ticketId) {
        try {
            ArrayList<TareaTicketResponseDto> tareaTickets = tareaTicketService.getAllTareaTicketByTicketId(ticketId);
            return new ResponseEntity<>(tareaTickets, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(new ErrorMessage(ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> saveTareaTicket(@RequestBody TareaTicketRequestDto request) {
        try {
            TareaTicket tareaTicket = tareaTicketService.createAsociacionTareaTicket(request);
            // TODO: que cada service tenga un método abstracto map para mappear de T1 (entidad) a T2 (dto)
            return new ResponseEntity<>(tareaTicketService.mapToResponse(tareaTicket), HttpStatus.OK);
        } catch (YaExisteRelacionTareaTicketException e) {
            return new ResponseEntity<>(new ErrorMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/tarea/{tareaId}")
    public ResponseEntity<?> deleteTareaTicketByTareaId(@PathVariable Long tareaId) {
        tareaTicketService.deleteTareaTicketByTareaId(tareaId);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/ticket/{ticketId}")
    public ResponseEntity<?> deleteTareaTicketByTicketId(@PathVariable Long ticketId) {
        tareaTicketService.deleteTareaTicketByTicketId(ticketId);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
