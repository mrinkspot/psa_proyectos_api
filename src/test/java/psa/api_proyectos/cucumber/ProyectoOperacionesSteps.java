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
import psa.api_proyectos.application.exceptions.ProyectoInvalidoException;
import psa.api_proyectos.application.services.ProyectoService;
import psa.api_proyectos.domain.models.Proyecto;
import psa.api_proyectos.domain.models.ProyectoEstado;
import java.sql.Date;


import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class ProyectoOperacionesSteps extends CucumberBootstrap{

    @Autowired
    private ProyectoService proyectoService;
    private ProyectoDto proyectoDto;
    private Long proyectoId;

    //this method executes after every scenario
    @After
    public void cleanUp() {
        log.info(">>> cleaning up after scenario!");
    }

    //this method executes before every scenario
    @Before
    public void before() {
        log.info(">>> Before scenario!");
        proyectoDto = new ProyectoDto();
        proyectoDto.setLiderId(1L);

    }

    //TEST ===================================================================================

    @Given("^Existe un proyecto y se conoce su Id$")
    public void creacionDeUnProyectoExistente() throws JsonProcessingException {
        proyectoDto.setNombre("Proyecto de prueba");
        proyectoDto.setDescripcion("Esta es la descripcion de un proyecto");
        proyectoDto.setEstadoIdm(ProyectoEstado.NO_INICIADO_IDM);
        proyectoDto.setFechaInicio(Date.valueOf("2012-12-12"));
        proyectoDto.setFechaFin(Date.valueOf("2022-12-12"));
        proyectoId = proyectoService.saveProyecto(proyectoDto).getId();
    }

    @When("^se intenta crear un proyecto con todos los campos asignados correctamente$")
    public void creacionDeProyectoCorrectamente(){
        //Arrange
        proyectoDto.setNombre("Proyecto de prueba");
        proyectoDto.setDescripcion("Esta es la descripcion de un proyecto");
        proyectoDto.setEstadoIdm(ProyectoEstado.NO_INICIADO_IDM);
        proyectoDto.setFechaInicio(Date.valueOf("2012-12-12"));
        proyectoDto.setFechaFin(Date.valueOf("2022-12-12"));

        //Act
        assertDoesNotThrow( () -> {proyectoId = (proyectoService.saveProyecto(proyectoDto)).getId();});
    }

    @When("^se intenta crear un proyecto con todos los campos asignados correctamente menos las fechas$")
    public void cracionDeProyectoSinFechas() {
        //Arrange
        proyectoDto.setNombre("Proyecto de prueba");
        proyectoDto.setDescripcion("Esta es la descripcion de un proyecto");
        proyectoDto.setEstadoIdm(ProyectoEstado.NO_INICIADO_IDM);

        //Act
        assertDoesNotThrow( () -> {proyectoId = (proyectoService.saveProyecto(proyectoDto)).getId();});
    }

    @When("^se intenta crear un proyecto sin indicar el nombre correctamente$")
    public void creacionConNombreIncorrectoDeProyecto(){
        //Arrange
        proyectoDto.setDescripcion("Esta es la descripcion de un proyecto");
        proyectoDto.setEstadoIdm(ProyectoEstado.NO_INICIADO_IDM);
    }

    @When("^se intenta crear un proyecto sin indicar la descripción correctamente$")
    public void creacionConDescripcionIncorrectaDeProyecto(){
        //Arrenge
        proyectoDto.setNombre("Siu Guarani 2");
        proyectoDto.setEstadoIdm(ProyectoEstado.NO_INICIADO_IDM);
    }

    @When("^se intenta crear un proyecto sin indicar un estado correctamente$")
    public void creacionConEstadoIncorrectoDeProyecto(){
        proyectoDto.setNombre("Proyecto proyectoso");
        proyectoDto.setDescripcion("Literal el proyecto super proyectoso");
    }

    @When("^se intenta crear un proyecto con una fecha final anterior a su fecha de inicio$")
    public void creacionConFechasIncorrectasDeProyecto() {
        proyectoDto.setNombre("Proyecto Fechoso");
        proyectoDto.setDescripcion("FECHAS FECHAS FECHAS");
        proyectoDto.setEstadoIdm(ProyectoEstado.NO_INICIADO_IDM);
        Date fechaFin = Date.valueOf("2015-03-31");
        Date fechaInicio = Date.valueOf("2022-12-19");
        proyectoDto.setFechaFin(fechaFin);
        proyectoDto.setFechaInicio(fechaInicio);
    }

    @When("^Este se le intentan modifica su nombre con un nombre valido$")
    public void modificacionDeNombreDeProyecto() throws JsonProcessingException {
        ProyectoDto proyectoModificadoDto = new ProyectoDto();

        //Se mantienen los campos anteriores
        Proyecto proyectoAnterior = proyectoService.getProyectoById(proyectoId);
        proyectoModificadoDto.setDescripcion(proyectoAnterior.getDescripcion());
        proyectoModificadoDto.setFechaInicio(proyectoAnterior.getFechaInicio());
        proyectoModificadoDto.setFechaFin(proyectoAnterior.getFechaFin());
        proyectoModificadoDto.setLiderId(proyectoAnterior.getLiderAsignadoId());
        proyectoModificadoDto.setEstadoIdm(proyectoAnterior.getEstado().idm);

        //Modificamos
        proyectoModificadoDto.setNombre("Proyecto modificado");

        proyectoService.updateProyecto(proyectoModificadoDto, proyectoId);
    }

    @Then("^El proyecto no es creado, y se informa del error$")
    public void validacionDeProyectoNoCreado() {
        assertThrows(
                ProyectoInvalidoException.class,
                () -> proyectoService.saveProyecto(proyectoDto)
        );
    }

    @Then("^El Proyecto se crea correctamente$")
    public void validacionDeProyectoCreado() {
        assertNotNull(proyectoService.getProyectoById(proyectoId));
    }

    @Then("^El proyecto se actualiza y ahora tiene su nombre modificado$")
    public void validacionDeModificacionDeNombreDeProyecto() {
        Proyecto proyectoModificado = proyectoService.getProyectoById(proyectoId);
        assertEquals(proyectoModificado.getNombre(), "Proyecto modificado");
    }
}
