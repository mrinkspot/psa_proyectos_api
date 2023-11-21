package psa.api_proyectos.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NoExistenProyectosException extends RuntimeException {
    public NoExistenProyectosException(String mensaje) {
        super(mensaje);
    }
}
