package psa.api_proyectos.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import psa.api_proyectos.application.dtos.TareaResponseDto;
import psa.api_proyectos.application.services.TareaService;
import psa.api_proyectos.domain.models.Proyecto;
import psa.api_proyectos.domain.models.Tarea;
import psa.api_proyectos.domain.models.TareaEstado;

import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/tarea")
public class TareaController {
    @Autowired
    TareaService tareaService;

    @GetMapping()
    public ArrayList<TareaResponseDto> getTareas() {
        ArrayList<Tarea> tareas = tareaService.getTareas();
        return tareaService.mapToResponse(tareas);
    }

    @GetMapping("/estados")
    public ResponseEntity<?> getTareaEstados() {
        ArrayList<TareaEstado> tareaEstados = tareaService.getAllTareaEstados();
        return new ResponseEntity<>(tareaEstados, HttpStatus.OK);
    }
}
