package psa.api_proyectos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import psa.api_proyectos.dtos.ProyectoDto;
import psa.api_proyectos.models.Proyecto;
import psa.api_proyectos.models.Tarea;
import psa.api_proyectos.services.ProyectoService;

import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/proyecto")
public class ProyectoController {
    @Autowired
    ProyectoService proyectoService;

    @GetMapping()
    public ArrayList<Proyecto> getProyectos() {
        return proyectoService.getProyectos();
    }

    @GetMapping("/{proyectoId}/tarea")
    public ArrayList<Tarea> getTareasByProyectoId(@PathVariable Long proyectoId) {
        return proyectoService.getTareasByProyectoId(proyectoId);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Proyecto saveProyecto(@RequestBody ProyectoDto request) {
        return proyectoService.saveProyecto(request);
    }
}
