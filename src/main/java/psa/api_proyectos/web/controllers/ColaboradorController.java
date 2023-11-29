package psa.api_proyectos.web.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import psa.api_proyectos.application.dtos.ColaboradorDto;
import psa.api_proyectos.application.exceptions.ColaboradorNoEncontradoException;
import psa.api_proyectos.application.exceptions.ErrorMessage;
import psa.api_proyectos.application.services.ColaboradorService;

import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/colaborador")
public class ColaboradorController {
    @Autowired
    ColaboradorService colaboradorService;

    @GetMapping()
    public ResponseEntity<?> getColaboradores() {
        try {
            ArrayList<ColaboradorDto> colaboradores = colaboradorService.getColaboradores();
            return new ResponseEntity<>(colaboradores, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{colaboradorLegajo}")
    public ResponseEntity<?> getColaboradorByLegajo(@PathVariable Long colaboradorLegajo) {
        try {
            ColaboradorDto colaboradores = colaboradorService.getColaboradorByLegajo(colaboradorLegajo);
            return new ResponseEntity<>(colaboradores, HttpStatus.OK);
        } catch (ColaboradorNoEncontradoException e) {
            return new ResponseEntity<>(new ErrorMessage(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
