package psa.api_proyectos.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ProyectoInvalidoException extends RuntimeException {
    public ProyectoInvalidoException(String mensaje) {
        super(mensaje);
    }
}
