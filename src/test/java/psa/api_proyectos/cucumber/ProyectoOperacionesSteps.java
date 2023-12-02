package psa.api_proyectos.cucumber;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import psa.api_proyectos.application.dtos.ProyectoRequestDto;
import psa.api_proyectos.application.exceptions.ProyectoInvalidoException;
import psa.api_proyectos.application.exceptions.ProyectoNoEncontradoException;
import psa.api_proyectos.application.services.ProyectoService;
import psa.api_proyectos.domain.models.Proyecto;
import psa.api_proyectos.domain.models.ProyectoEstado;
import java.sql.Date;


import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class ProyectoOperacionesSteps extends CucumberBootstrap{

    @Autowired
    private ProyectoService proyectoService;
    private ProyectoRequestDto proyectoDto;
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
        proyectoDto = new ProyectoRequestDto();
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

    @When("^Se le intentan modificar algún campo con un dato válido$")
    public void modificacionDeProyecto() throws JsonProcessingException {
        ProyectoRequestDto proyectoModificadoDto = new ProyectoRequestDto();

        proyectoModificadoDto.setNombre("Proyecto modificado");
        proyectoModificadoDto.setDescripcion("descripcionModificada");
        proyectoModificadoDto.setFechaInicio(Date.valueOf("2010-10-10"));
        proyectoModificadoDto.setFechaFin(Date.valueOf("2023-03-03"));
        proyectoModificadoDto.setLiderId(2L);
        proyectoModificadoDto.setEstadoIdm(ProyectoEstado.EN_PROGRESO_IDM);

        //Modificamos

        proyectoService.updateProyecto(proyectoModificadoDto, proyectoId);
    }

    @When("^Se le intentan modificar algún campo con un dato inválido$")
    public void modificacionConCamposInvalidos() throws JsonProcessingException{
        ProyectoRequestDto proyectoModificadoDto = new ProyectoRequestDto();

        proyectoModificadoDto.setNombre("");
        proyectoModificadoDto.setDescripcion("");
        proyectoModificadoDto.setFechaInicio(Date.valueOf("2010-10-10"));
        proyectoModificadoDto.setFechaFin(Date.valueOf("2023-03-03"));
        proyectoModificadoDto.setLiderId(900L);
        proyectoModificadoDto.setEstadoIdm(ProyectoEstado.EN_PROGRESO_IDM);

        //Modificamos
        assertThrows(
                ProyectoInvalidoException.class,
                () -> proyectoService.updateProyecto(proyectoModificadoDto, proyectoId)
        );
    }

    @When("^Se le intenta eliminar$")
    public void borradoDeProyectoCreado(){
        proyectoService.deleteProyectoById(proyectoId);
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

    @Then("^El proyecto se actualiza y ahora tiene sus campos modificados$")
    public void validacionDeModificacionDeProyecto() {
        Proyecto proyectoModificado = proyectoService.getProyectoById(proyectoId);
        assertEquals(proyectoModificado.getNombre(), "Proyecto modificado");
        assertEquals(proyectoModificado.getDescripcion(), "descripcionModificada");
        assertEquals(proyectoModificado.getEstado().idm, ProyectoEstado.EN_PROGRESO_IDM);
        assertEquals(proyectoModificado.getFechaInicio(), Date.valueOf("2010-10-10"));
        assertEquals(proyectoModificado.getFechaFin(), Date.valueOf("2023-03-03"));
        assertEquals(proyectoModificado.getLiderAsignadoId(), 2L);
    }

    @Then("^El proyecto no se actualiza y se recibe una excepción$")
    public void validacionDeNoModificacionDeProyecto() {
        Proyecto proyectoNoModificado = proyectoService.getProyectoById(proyectoId);
        assertEquals(proyectoNoModificado.getNombre(), proyectoDto.getNombre());
        assertEquals(proyectoNoModificado.getDescripcion(), proyectoDto.getDescripcion());
        assertEquals(proyectoNoModificado.getEstado().idm, proyectoDto.getEstadoIdm());
        assertEquals(proyectoNoModificado.getFechaInicio(), proyectoDto.getFechaInicio());
        assertEquals(proyectoNoModificado.getFechaFin(), proyectoDto.getFechaFin());
        assertEquals(proyectoNoModificado.getLiderAsignadoId(), proyectoDto.getLiderId());
    }

    @Then("^El proyecto es eliminado y ya no puede ser obtenido$")
    public void validacionDeProyectoEliminado(){
        assertThrows(ProyectoNoEncontradoException.class, () -> proyectoService.getProyectoById(proyectoId));
    }
}
