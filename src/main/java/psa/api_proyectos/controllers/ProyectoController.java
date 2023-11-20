package psa.api_proyectos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import psa.api_proyectos.models.Proyecto;
import psa.api_proyectos.services.ProyectoService;

import java.util.ArrayList;

@RestController
@RequestMapping("/proyecto")
public class ProyectoController {
    @Autowired
    ProyectoService proyectoService;

    @GetMapping()
    public ArrayList<Proyecto> getProyectos() {
        return proyectoService.getProyectos();
    }

    @PostMapping()
    public Proyecto saveProyecto(@RequestBody Proyecto proyecto) {
        return proyectoService.saveProyecto(proyecto);
    }
}
