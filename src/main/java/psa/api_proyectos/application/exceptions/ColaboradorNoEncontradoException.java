package psa.api_proyectos.application.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ColaboradorNoEncontradoException extends RuntimeException {
    public ColaboradorNoEncontradoException(String mensaje) { super(mensaje);}
}