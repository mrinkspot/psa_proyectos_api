package psa.api_proyectos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import psa.api_proyectos.models.ProyectoModel;
import psa.api_proyectos.services.ProyectoService;

import java.util.ArrayList;

@RestController
@RequestMapping("/proyecto")
public class ProyectoController {
    @Autowired
    ProyectoService proyectoService;

    @GetMapping()
    public ArrayList<ProyectoModel> getProyectos() {
        return proyectoService.getProyectos();
    }

    @PostMapping()
    public ProyectoModel saveProyecto(@RequestBody ProyectoModel proyecto) {
        return proyectoService.saveProyecto(proyecto);
    }
}
