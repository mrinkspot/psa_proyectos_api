package psa.api_proyectos.cucumber;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import psa.api_proyectos.domain.models.Proyecto;
import psa.api_proyectos.data.repositories.ProyectoRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
public class ProyectoOperacionesSteps extends CucumberBootstrap{
    @Autowired
    private ProyectoRepository proyectoRepository;

    private Proyecto proyecto;

    //this method executes after every scenario
    @After
    public void cleanUp() {
        log.info(">>> cleaning up after scenario!");
        //placeholder for after scenario logic
    }

    //this method executes after every step
    @AfterStep
    public void afterStep() {
        log.info(">>> AfterStep!");
        //placeholder for after step logic
    }

    //this method executes before every scenario
    @Before
    public void before() {
        log.info(">>> Before scenario!");
        //placeholder for before scenario logic
    }

    //this method executes before every step
    @BeforeStep
    public void beforeStep() {
        log.info(">>> BeforeStep!");
        //placeholder for before step logic
    }

    @When("^se intenta crear crear un proyecto con todos los campos asignados correctamente$")
    public void creacionDeProyecto(){
        proyecto = new Proyecto();
    }

    @Then("^El Proyecto se crea correctamente$")
    public void validacionDeProyectoCreado() {
        assertNotNull(proyecto);
    }
}
