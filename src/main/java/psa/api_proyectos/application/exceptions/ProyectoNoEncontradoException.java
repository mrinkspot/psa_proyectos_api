package psa.api_proyectos.application.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ProyectoNoEncontradoException extends RuntimeException {
    public ProyectoNoEncontradoException(String mensaje) { super(mensaje);}
}
