package psa.api_proyectos.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ProyectoEstados")
public class ProyectoEstado {
    @Id
    private long idm;
    private String descripcion;

    public ProyectoEstado(long idm, String descripcion) {
        this.idm = idm;
        this.descripcion = descripcion;
    }

    public static final long NO_INICIADO_IDM = 1;
    public static final long EN_PROGRESO_IDM = 2;
    public static final long TERMINADO_IDM = 3;

    public ProyectoEstado() {
    }
}
