package psa.api_proyectos.application.exceptions;

import java.util.HashMap;

public class TareaInvalidaException extends BaseException {
    public TareaInvalidaException(String mensaje) {
        super(mensaje);
    }

    public TareaInvalidaException(HashMap<String, String> errores) {
        super("Errores");
        this.errores = errores;
    }
}
