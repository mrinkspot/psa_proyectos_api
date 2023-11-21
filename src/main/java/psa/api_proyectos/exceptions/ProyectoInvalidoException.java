package psa.api_proyectos.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.HashMap;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ProyectoInvalidoException extends RuntimeException {
    private HashMap<String, String> errores = new HashMap<>();

    public ProyectoInvalidoException(String mensaje) {
        super(mensaje);
    }

    public ProyectoInvalidoException(HashMap<String, String> errores) {
        super("Errores");
        this.errores = errores;
    }

    public HashMap<String, String> getErrores() {
        return errores;
    }
}
