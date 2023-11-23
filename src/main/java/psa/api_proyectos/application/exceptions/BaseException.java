package psa.api_proyectos.application.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BaseException extends RuntimeException {
    protected HashMap<String, String> errores = new HashMap<>();

    public BaseException(String mensaje) {
        super(mensaje);
    }

    public BaseException(HashMap<String, String> errores) {
        super("Errores");
        this.errores = errores;
    }

    public HashMap<String, String> getErrores() {
        return errores;
    }
}
