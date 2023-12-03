package psa.api_proyectos.web.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import psa.api_proyectos.application.dtos.ProyectoRequestDto;
import psa.api_proyectos.application.dtos.ProyectoResponseDto;
import psa.api_proyectos.application.dtos.TareaRequestDto;
import psa.api_proyectos.application.dtos.TareaResponseDto;
import psa.api_proyectos.application.exceptions.*;
import psa.api_proyectos.application.services.TareaService;
import psa.api_proyectos.domain.models.Proyecto;
import psa.api_proyectos.domain.models.ProyectoEstado;
import psa.api_proyectos.domain.models.Tarea;
import psa.api_proyectos.application.services.ProyectoService;
import psa.api_proyectos.domain.models.TareaEstado;

import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/proyecto")
public class ProyectoController {
    @Autowired
    ProyectoService proyectoService;
    @Autowired
    TareaService tareaService;

    @GetMapping()
    public ResponseEntity<?> getProyectos() {
        ArrayList<Proyecto> proyectos = proyectoService.getProyectos();
        return new ResponseEntity<>(proyectoService.mapToResponse(proyectos), HttpStatus.OK);
    }

    @GetMapping("/{proyectoId}")
    public ResponseEntity<?> getProyectoById(@PathVariable Long proyectoId) {
        try {
            Proyecto proyecto = proyectoService.getProyectoById(proyectoId);
            return new ResponseEntity<>(proyectoService.mapToResponse(proyecto), HttpStatus.OK);
        } catch (ProyectoNoEncontradoException e) {
            return new ResponseEntity<>(new ErrorMessage(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    @DeleteMapping("/{proyectoId}")
    public ResponseEntity<?> deleteProyectoById(@PathVariable Long proyectoId) {
        try {
            proyectoService.deleteProyectoById(proyectoId);
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } catch (ProyectoNoEncontradoException e) {
            return new ResponseEntity<>(new ErrorMessage(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{proyectoId}/tarea")
    public ArrayList<TareaResponseDto> getTareasByProyectoId(@PathVariable Long proyectoId) {
        ArrayList<Tarea> tareas = proyectoService.getTareasByProyectoId(proyectoId);
        return tareaService.mapToResponse(tareas);
    }

    // TODO: ver si es necesario pasar el id de proyecto y qué utilidad podemos darle
    @GetMapping("/{proyectoId}/tarea/{tareaId}")
    public ResponseEntity<?> getTareaById(@PathVariable Long tareaId) {
        try {
            Tarea tarea = tareaService.getTareaById(tareaId);
            return new ResponseEntity<>(tareaService.mapToResponse(tarea), HttpStatus.OK);
        } catch (TareaNoEncontradaException e) {
            return new ResponseEntity<>(new ErrorMessage(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    // TODO: idem GET, probablemente podamos definir alguna regla de negocio que involucre el estado del proyecto
    // o de la tarea misma
    @DeleteMapping("/{proyectoId}/tarea/{tareaId}")
    public ResponseEntity<?> deleteTareaById(@PathVariable Long proyectoId, @PathVariable Long tareaId) {
        try {
            tareaService.deleteTareaById(tareaId);
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } catch (TareaNoEncontradaException e) {
            return new ResponseEntity<>(new ErrorMessage(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> saveProyecto(@RequestBody ProyectoRequestDto request) {
        try {
            Proyecto proyecto = proyectoService.saveProyecto(request);
            // TODO: que cada service tenga un método abstracto map para mappear de T1 (entidad) a T2 (dto)
            return new ResponseEntity<>(proyectoService.mapToResponse(proyecto), HttpStatus.OK);
        } catch (ProyectoInvalidoException e) {
            return new ResponseEntity<>(e.getErrores(), HttpStatus.BAD_REQUEST);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/{proyectoId}/tarea")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> saveTarea(@RequestBody TareaRequestDto request, @PathVariable Long proyectoId) {
        try {
            Tarea tarea = proyectoService.saveTarea(request, proyectoId);
            // TODO: que cada service tenga un método abstracto map para mappear de T1 (entidad) a T2 (dto)
            return new ResponseEntity<>(tareaService.mapToResponse(tarea), HttpStatus.OK);
        } catch (TareaInvalidaException e) {
            return new ResponseEntity<>(e.getErrores(), HttpStatus.BAD_REQUEST);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/{proyectoId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> updateProyecto(@RequestBody ProyectoRequestDto request, @PathVariable Long proyectoId) {
        if (!proyectoService.existsProyecto(proyectoId)){ return new ResponseEntity<>(HttpStatus.NOT_FOUND);}

        try {
            proyectoService.updateProyecto(request, proyectoId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ProyectoInvalidoException e) {
            return new ResponseEntity<>(e.getErrores(), HttpStatus.BAD_REQUEST);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/{proyectoId}/tarea/{tareaId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> updateTarea(@RequestBody TareaRequestDto request, @PathVariable Long proyectoId, @PathVariable Long tareaId) {
        if (!proyectoService.existsProyecto(proyectoId)){ return new ResponseEntity<>(HttpStatus.NOT_FOUND);}
        if (!tareaService.existsTarea(tareaId)){ return new ResponseEntity<>(HttpStatus.NOT_FOUND);}

        try {
            proyectoService.updateTarea(request, proyectoId, tareaId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (TareaInvalidaException e) {
            return new ResponseEntity<>(e.getErrores(), HttpStatus.BAD_REQUEST);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/estado")
    public ResponseEntity<?> getProyectoEstados() {
        ArrayList<ProyectoEstado> proyectoEstados = proyectoService.getAllProyectoEstados();
        return new ResponseEntity<>(proyectoEstados, HttpStatus.OK);
    }
}
