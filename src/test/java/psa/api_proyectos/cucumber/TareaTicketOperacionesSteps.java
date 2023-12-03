package psa.api_proyectos.cucumber;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import psa.api_proyectos.application.dtos.ProyectoRequestDto;
import psa.api_proyectos.application.dtos.TareaRequestDto;
import psa.api_proyectos.application.dtos.TareaTicketRequestDto;
import psa.api_proyectos.application.exceptions.YaExisteRelacionTareaTicketException;
import psa.api_proyectos.application.services.ProyectoService;
import psa.api_proyectos.application.services.TareaTicketService;
import psa.api_proyectos.domain.models.*;

import java.sql.Date;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
public class TareaTicketOperacionesSteps extends CucumberBootstrap {

    @Autowired
    private TareaTicketService tareaTicketService;
    @Autowired
    private ProyectoService proyectoService;

    private TareaTicketRequestDto tareaTicketRequestDto;

    private final Long ticketId = 1L;
    private Long tareaId;

    @Given("^existe una tarea y un ticket y no estan relacionados$")
    public void creacionTareaYTicket() throws JsonProcessingException {
        ProyectoRequestDto proyectoDto = new ProyectoRequestDto();
        proyectoDto.setLiderId(1L);
        proyectoDto.setNombre("Proyecto de prueba");
        proyectoDto.setDescripcion("Esta es la descripcion de un proyecto");
        proyectoDto.setEstadoIdm(ProyectoEstado.NO_INICIADO_IDM);
        proyectoDto.setFechaInicio(Date.valueOf("2012-12-12"));
        proyectoDto.setFechaFin(Date.valueOf("2022-12-12"));
        Long proyectoId = proyectoService.saveProyecto(proyectoDto).getId();

        TareaRequestDto tarea = new TareaRequestDto();
        tarea.setDescripcion("Una tarea");
        tarea.setEstadoIdm(TareaEstado.NUEVA_IDM);
        tarea.setFechaInicio(Date.valueOf("2023-05-01"));
        tarea.setFechaFin(Date.valueOf("2030-12-12"));
        tarea.setColaboradorAsignadoId(1L);
        tareaId = proyectoService.saveTarea(tarea, proyectoId).id;
    }

    @Given("^existe una tarea y un ticket y  estan relacionados$")
    public void creacionYrelacionDeTareaTicket() throws JsonProcessingException {
        ProyectoRequestDto proyectoDto = new ProyectoRequestDto();
        proyectoDto.setLiderId(1L);
        proyectoDto.setNombre("Proyecto de prueba");
        proyectoDto.setDescripcion("Esta es la descripcion de un proyecto");
        proyectoDto.setEstadoIdm(ProyectoEstado.NO_INICIADO_IDM);
        proyectoDto.setFechaInicio(Date.valueOf("2012-12-12"));
        proyectoDto.setFechaFin(Date.valueOf("2022-12-12"));
        Long proyectoId = proyectoService.saveProyecto(proyectoDto).getId();

        TareaRequestDto tarea = new TareaRequestDto();
        tarea.setDescripcion("Una tarea");
        tarea.setEstadoIdm(TareaEstado.NUEVA_IDM);
        tarea.setFechaInicio(Date.valueOf("2023-05-01"));
        tarea.setFechaFin(Date.valueOf("2030-12-12"));
        tarea.setColaboradorAsignadoId(1L);
        tareaId = proyectoService.saveTarea(tarea, proyectoId).id;

        TareaTicketRequestDto tareaTicketRequestDto = new TareaTicketRequestDto();
        tareaTicketRequestDto.tareaId = tareaId;
        tareaTicketRequestDto.ticketId = ticketId;
        tareaTicketService.createAsociacionTareaTicket(tareaTicketRequestDto);
    }

    @When("^los relaciono$")
    public void relacionTicketTarea() {
        tareaTicketRequestDto = new TareaTicketRequestDto();
        tareaTicketRequestDto.tareaId = tareaId;
        tareaTicketRequestDto.ticketId = ticketId;
        tareaTicketService.createAsociacionTareaTicket(tareaTicketRequestDto);
    }

    @When("^los relaciono de vuelta$")
    public void relacionDuplicada() {
        tareaTicketRequestDto = new TareaTicketRequestDto();
        tareaTicketRequestDto.tareaId = tareaId;
        tareaTicketRequestDto.ticketId = ticketId;
    }

    @Then("^puedo obtener informacion de uno o del otro mediante esta relacion$")
    public void validacionRelacionTicker() {
        ArrayList<TareaTicket> tareaTickets = tareaTicketService.getAllTareaTicketByTareaId(tareaId);
        assertEquals(tareaTickets.get(0).getTicketId(), ticketId);
        assertEquals(tareaTickets.get(0).getTareaId(), tareaId);
    }

    @Then("^El sistema me indica que ya estan relacionados$")
    public void validacionRelacionDuplicada() {
        assertThrows(YaExisteRelacionTareaTicketException.class,
                () -> tareaTicketService.createAsociacionTareaTicket(tareaTicketRequestDto));
    }
}
