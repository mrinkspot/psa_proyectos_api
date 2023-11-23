package psa.api_proyectos.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import psa.api_proyectos.application.dtos.ProyectoDto;
import psa.api_proyectos.application.dtos.TareaDto;
import psa.api_proyectos.application.exceptions.NoExistenProyectosException;
import psa.api_proyectos.application.exceptions.ProyectoInvalidoException;
import psa.api_proyectos.domain.models.Proyecto;
import psa.api_proyectos.domain.models.Tarea;
import psa.api_proyectos.application.exceptions.ErrorMessage;
import psa.api_proyectos.application.services.ProyectoService;
import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/proyecto")
public class ProyectoController {
    @Autowired
    ProyectoService proyectoService;

    @GetMapping()
    public ResponseEntity<?> getProyectos() {
        try {
            ArrayList<Proyecto> proyectos = proyectoService.getProyectos();
            return new ResponseEntity<>(proyectos, HttpStatus.OK);
        } catch (NoExistenProyectosException e) {
            return new ResponseEntity<>(new ErrorMessage(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{proyectoId}/tarea")
    public ArrayList<Tarea> getTareasByProyectoId(@PathVariable Long proyectoId) {
        return proyectoService.getTareasByProyectoId(proyectoId);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> saveProyecto(@RequestBody ProyectoDto request) {
        try {
            Proyecto proyecto = proyectoService.saveProyecto(request);
            return new ResponseEntity<>(proyecto, HttpStatus.OK);
        } catch (ProyectoInvalidoException e) {
            return new ResponseEntity<>(e.getErrores(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{proyectoId}/tarea")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> saveTarea(@RequestBody TareaDto request, @PathVariable Long proyectoId) {
        try {
            Tarea tarea = proyectoService.saveTarea(request, proyectoId);
            return new ResponseEntity<>(tarea, HttpStatus.OK);
        } catch (ProyectoInvalidoException e) {
            return new ResponseEntity<>(e.getErrores(), HttpStatus.BAD_REQUEST);
        }
    }
}