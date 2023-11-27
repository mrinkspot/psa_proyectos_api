package psa.api_proyectos.cucumber;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import psa.api_proyectos.application.dtos.ProyectoDto;
import psa.api_proyectos.application.dtos.TareaDto;
import psa.api_proyectos.application.exceptions.NoExisteElProyectoPedidoException;
import psa.api_proyectos.application.exceptions.TareaInvalidaException;
import psa.api_proyectos.application.services.ProyectoService;
import psa.api_proyectos.domain.models.ProyectoEstado;
import psa.api_proyectos.domain.models.Tarea;
import psa.api_proyectos.domain.models.TareaEstado;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class TareaOperacionesSteps extends CucumberBootstrap{


    @Autowired
    private ProyectoService proyectoService;
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
    public void creacionDeProyecto() {
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

    @When("^Se intenta crear una tarea con todos los campos asignados correctamente$")
    public void creacionCorrectaDeUnaTarea() {
        //Arrange
        tareaDto.setDescripcion("Una super descripcion de una tarea");
        tareaDto.setEstadoIdm(TareaEstado.NUEVA_IDM);
        tareaDto.setAsignadoId(1L);
        tareaDto.setFechaInicio(Date.valueOf("2015-12-12"));
        tareaDto.setFechaFin(Date.valueOf("2022-02-02"));

        assertDoesNotThrow( () -> {tareaId = proyectoService.saveTarea(tareaDto, proyectoId).id;});
    }

    @When("^Se intenta crear una tarea con todos los campos asignados correctamente menos las fechas$")
    public void creacionCorrectaDeUnaTareaSinFechas() {
        //Arrange
        tareaDto.setDescripcion("Una super descripcion de una tarea");
        tareaDto.setEstadoIdm(TareaEstado.NUEVA_IDM);
        tareaDto.setAsignadoId(1L);

        assertDoesNotThrow( () -> {tareaId = proyectoService.saveTarea(tareaDto, proyectoId).id;});
    }

    @When("^Se intenta crear una tarea sin indicar la descripción correctamente$")
    public void creacionIncorrectaDeUnaTareaPorDescripcionEquivocada() {
        //Arrange
        tareaDto.setEstadoIdm(TareaEstado.NUEVA_IDM);
        tareaDto.setAsignadoId(1L);
    }

    @When("^Se intenta crear una tarea sin indicar un estado correctamente$")
    public void creacionIncorrectaDeUnaTareaPorEstadoVacio() {
        //Arrange
        tareaDto.setDescripcion("Hola reyes");
        tareaDto.setAsignadoId(1L);
    }

    @When("^se intenta crear una tarea con una fecha final anterior a su fecha de inicio$")
    public void creacionIncorrectaDeUnaTareaPorFechasIncorrectas() {
        //Arrange
        tareaDto.setDescripcion("Una super descripcion de una tarea");
        tareaDto.setEstadoIdm(TareaEstado.NUEVA_IDM);
        tareaDto.setAsignadoId(1L);
        tareaDto.setFechaFin(Date.valueOf("2015-12-12"));
        tareaDto.setFechaInicio(Date.valueOf("2022-02-02"));
    }

    @When(("^Se intenta crear una tarea para dicho proyecto$"))
    public void creacionIncorrectaDeUnaTareaPorIdDeproyectoIncorrecto(){
        tareaDto.setDescripcion("Una super descripcion de una tarea");
        tareaDto.setEstadoIdm(TareaEstado.NUEVA_IDM);
        tareaDto.setAsignadoId(1L);
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
                NoExisteElProyectoPedidoException.class,
                () -> proyectoService.saveTarea(tareaDto, proyectoId)
        );
    }


}
