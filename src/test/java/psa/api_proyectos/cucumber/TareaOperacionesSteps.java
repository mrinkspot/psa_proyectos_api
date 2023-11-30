package psa.api_proyectos.cucumber;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import psa.api_proyectos.application.dtos.ProyectoDto;
import psa.api_proyectos.application.dtos.TareaDto;
import psa.api_proyectos.application.exceptions.TareaInvalidaException;
import psa.api_proyectos.application.exceptions.TareaNoEncontradaException;
import psa.api_proyectos.application.services.ProyectoService;
import psa.api_proyectos.application.services.TareaService;
import psa.api_proyectos.domain.models.ProyectoEstado;
import psa.api_proyectos.domain.models.Tarea;
import psa.api_proyectos.domain.models.TareaEstado;

import java.sql.Date;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class TareaOperacionesSteps extends CucumberBootstrap{


    @Autowired
    private ProyectoService proyectoService;
    @Autowired
    private TareaService tareaService;
    private ProyectoDto proyectoDto;
    private Long proyectoId;
    private TareaDto tareaDto;
    private Long tareaId;

    @After
    public void cleanUp() {
        log.info(">>> cleaning up after scenario!");
    }

    //this method executes before every scenario
    @Before
    public void before() {
        log.info(">>> Before scenario!");
        tareaDto = new TareaDto();
    }

    //TEST =======================================================================


    @Given("^Que existe un proyecto crado y conozco su Id$")
    public void creacionDeProyecto() throws JsonProcessingException {
        if (proyectoDto == null) {
            proyectoDto = new ProyectoDto();
            proyectoDto.setNombre("a");
            proyectoDto.setDescripcion("a");
            proyectoDto.setEstadoIdm(ProyectoEstado.NO_INICIADO_IDM);
            proyectoDto.setLiderId(1L);
            proyectoId = proyectoService.saveProyecto(proyectoDto).getId();
        }
    }

    @Given("^Que tengo un id de un proyecto que no existe$")
    public void asignacionDeIdIncorrectoAProyecto(){
        proyectoId = 8573L;
    }

    @Given("^Existe una tarea, esta pertenece a un proyecto y se conoce su Id y el Id del proyecto$")
    public void creacionDeTarea() throws JsonProcessingException {
        creacionDeProyecto();

        tareaDto.setDescripcion("Una super descripcion de una tarea");
        tareaDto.setEstadoIdm(TareaEstado.NUEVA_IDM);
        tareaDto.setColaboradorAsignadoId(1L);
        tareaDto.setFechaInicio(Date.valueOf("2015-12-12"));
        tareaDto.setFechaFin(Date.valueOf("2022-02-02"));

        tareaId = proyectoService.saveTarea(tareaDto, proyectoId).id;
    }

    @When("^Se intenta crear una tarea con todos los campos asignados correctamente$")
    public void creacionCorrectaDeUnaTarea() {
        //Arrange
        tareaDto.setDescripcion("Una super descripcion de una tarea");
        tareaDto.setEstadoIdm(TareaEstado.NUEVA_IDM);
        tareaDto.setColaboradorAsignadoId(1L);
        tareaDto.setFechaInicio(Date.valueOf("2015-12-12"));
        tareaDto.setFechaFin(Date.valueOf("2022-02-02"));

        assertDoesNotThrow( () -> {tareaId = proyectoService.saveTarea(tareaDto, proyectoId).id;});
    }

    @When("^Se intenta crear una tarea con todos los campos asignados correctamente menos las fechas$")
    public void creacionCorrectaDeUnaTareaSinFechas() {
        //Arrange
        tareaDto.setDescripcion("Una super descripcion de una tarea");
        tareaDto.setEstadoIdm(TareaEstado.NUEVA_IDM);
        tareaDto.setColaboradorAsignadoId(1L);

        assertDoesNotThrow( () -> {tareaId = proyectoService.saveTarea(tareaDto, proyectoId).id;});
    }

    @When("^Se intenta crear una tarea sin indicar la descripción correctamente$")
    public void creacionIncorrectaDeUnaTareaPorDescripcionEquivocada() {
        //Arrange
        tareaDto.setEstadoIdm(TareaEstado.NUEVA_IDM);
        tareaDto.setColaboradorAsignadoId(1L);
    }

    @When("^Se intenta crear una tarea sin indicar un estado correctamente$")
    public void creacionIncorrectaDeUnaTareaPorEstadoVacio() {
        //Arrange
        tareaDto.setDescripcion("Hola reyes");
        tareaDto.setColaboradorAsignadoId(1L);
    }

    @When("^se intenta crear una tarea con una fecha final anterior a su fecha de inicio$")
    public void creacionIncorrectaDeUnaTareaPorFechasIncorrectas() {
        //Arrange
        tareaDto.setDescripcion("Una super descripcion de una tarea");
        tareaDto.setEstadoIdm(TareaEstado.NUEVA_IDM);
        tareaDto.setColaboradorAsignadoId(1L);
        tareaDto.setFechaFin(Date.valueOf("2015-12-12"));
        tareaDto.setFechaInicio(Date.valueOf("2022-02-02"));
    }

    @When(("^Se intenta crear una tarea para dicho proyecto$"))
    public void creacionIncorrectaDeUnaTareaPorIdDeproyectoIncorrecto(){
        tareaDto.setDescripcion("Una super descripcion de una tarea");
        tareaDto.setEstadoIdm(TareaEstado.NUEVA_IDM);
        tareaDto.setColaboradorAsignadoId(1L);
    }

    @When("^Se le intentan modificar algún campo de la tarea con un dato válido$")
    public void modificacionCorrectaDeTarea() throws JsonProcessingException {
        TareaDto tareaModificada = new TareaDto();

        tareaModificada.setDescripcion("descripcionModificada");
        tareaModificada.setFechaInicio(Date.valueOf("2010-10-10"));
        tareaModificada.setFechaFin(Date.valueOf("2023-03-03"));
        tareaModificada.setEstadoIdm(TareaEstado.EN_CURSO_IDM);
        tareaModificada.setColaboradorAsignadoId(2L);

        //Modificamos

        proyectoService.updateTarea(tareaModificada, proyectoId, tareaId);
    }

    @When("^Se le intentan modificar algún campo de la tarea con un dato inválido$")
    public void modificacionIncorrectaDeTarea() {
        TareaDto tareaModificada = new TareaDto();

        tareaModificada.setDescripcion(null);
        tareaModificada.setFechaInicio(null);
        tareaModificada.setFechaFin(Date.valueOf("2023-03-03"));
        tareaModificada.setEstadoIdm(TareaEstado.EN_CURSO_IDM);
        tareaModificada.setColaboradorAsignadoId(2L);

        //Modificamos
        assertThrows(TareaInvalidaException.class, () ->proyectoService.updateTarea(tareaModificada, proyectoId, tareaId));
    }
    @When("^Se le intenta eliminar a la tarea$")
    public void EliminacionDeTarea(){
        tareaService.deleteTareaById(tareaId);
    }

    @Then("^La tarea se crea correctamente$")
    public void validacionDeCorrectaCreacionDeTarea(){
        ArrayList<Tarea> tareasObtenidas = proyectoService.getTareasByProyectoId(proyectoId);
        Tarea tareaFinal = tareasObtenidas.stream().filter(t -> tareaId == t.id).findAny().orElse(null);
        assertNotNull(tareaFinal);
    }

    @Then("^La tarea no es creada, y se informa del error$")
    public void validacionDeIncorrectaCreacionDeTarea(){
        assertThrows(
                TareaInvalidaException.class,
                () -> proyectoService.saveTarea(tareaDto, proyectoId)
        );
    }

    @Then("^La tarea no es creada porque el proyecto no existe$")
    public void validacionDeQueLaTareaNoHaSidoCreadaPorProyectoInexistente(){
        assertThrows(
                TareaInvalidaException.class,
                () -> proyectoService.saveTarea(tareaDto, proyectoId)
        );
    }
    
    @Then("^La tarea se actualiza y ahora tiene sus campos modificados$")
    public void validacionDeModificacionCorrectaDeTarea(){
        Tarea tareaModificada = proyectoService.getTareasByProyectoId(proyectoId).stream().filter(
                tarea -> tareaId == tarea.id
        ).findAny().orElse(null);

        assertEquals(tareaModificada.descripcion, "descripcionModificada");
        assertEquals(tareaModificada.estado.idm, ProyectoEstado.EN_PROGRESO_IDM);
        assertEquals(tareaModificada.fechaInicio, Date.valueOf("2010-10-10"));
        assertEquals(tareaModificada.fechaFin, Date.valueOf("2023-03-03"));
        assertEquals(tareaModificada.colaboradorAsignadoId, 2L);
    }

    @Then("^La tarea no se actualiza y se recibe una excepción$")
    public void validacionDeModificacionIncorrectaDeTarea(){
        Tarea tareaModificada = proyectoService.getTareasByProyectoId(proyectoId).stream().filter(
                tarea -> tareaId == tarea.id
        ).findAny().orElse(null);

        assertEquals(tareaModificada.descripcion, tareaDto.getDescripcion());
        assertEquals(tareaModificada.estado.idm, tareaDto.getEstadoIdm());
        assertEquals(tareaModificada.fechaInicio, tareaDto.getFechaInicio());
        assertEquals(tareaModificada.fechaFin, tareaDto.getFechaFin());
        assertEquals(tareaModificada.colaboradorAsignadoId, tareaDto.getColaboradorAsignadoId());
    }

    @Then("^La tarea es eliminada y ya no puede ser obtenida$")
    public void validacionTareaEliminada(){
        assertThrows(TareaNoEncontradaException.class, () -> tareaService.getTareaById(tareaId));
    }

}
