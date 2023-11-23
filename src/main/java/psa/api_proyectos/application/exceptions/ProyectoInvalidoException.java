package psa.api_proyectos.application.exceptions;

import java.util.HashMap;

public class ProyectoInvalidoException extends BaseException {

    public ProyectoInvalidoException(String mensaje) {
        super(mensaje);
    }

    public ProyectoInvalidoException(HashMap<String, String> errores) {
        super("Errores");
        this.errores = errores;
    }

}
