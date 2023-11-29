package psa.api_proyectos.web.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import psa.api_proyectos.application.dtos.ColaboradorDto;
import psa.api_proyectos.application.exceptions.ColaboradorNoEncontradoException;
import psa.api_proyectos.application.exceptions.ErrorMessage;
import psa.api_proyectos.application.services.RecursosService;

import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/recursos")
public class RecursosController {
    @Autowired
    RecursosService recursosService;

    @GetMapping("/colaborador")
    public ResponseEntity<?> getColaboradores() {
        try {
            ArrayList<ColaboradorDto> colaboradores = recursosService.getColaboradores();
            return new ResponseEntity<>(colaboradores, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/colaborador/{colaboradorLegajo}")
    public ResponseEntity<?> getColaboradorByLegajo(@PathVariable Long colaboradorLegajo) {
        try {
            ColaboradorDto colaboradores = recursosService.getColaboradorByLegajo(colaboradorLegajo);
            return new ResponseEntity<>(colaboradores, HttpStatus.OK);
        } catch (ColaboradorNoEncontradoException e) {
            return new ResponseEntity<>(new ErrorMessage(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
