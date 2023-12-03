package psa.api_proyectos.domain.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ProyectoEstados")
public class ProyectoEstado {
    @Id
    public Long idm;
    public String descripcion;

    public ProyectoEstado(long idm, String descripcion) {
        this.idm = idm;
        this.descripcion = descripcion;
    }

    public static final long NO_INICIADO_IDM = 1;
    public static final long EN_PROGRESO_IDM = 2;
    public static final long TERMINADO_IDM = 3;
    public static final String NO_INICIADO_DESCRIPCION = "No iniciado";
    public static final String EN_PROGRESO_DESCRIPCION = "En progreso";
    public static final String TERMINADO_DESCRIPCION = "Terminado";

    public ProyectoEstado() {
    }
}
