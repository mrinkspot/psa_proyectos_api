package psa.api_proyectos.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TareaEstados")
public class TareaEstado {
    @Id
    private long idm;
    private String descripcion;

    public TareaEstado(long idm, String descripcion) {
        this.idm = idm;
        this.descripcion = descripcion;
    }

    public static final long NUEVA_IDM = 1;
    public static final long EN_CURSO_IDM = 2;
    public static final long TERMINADA_IDM = 3;
    public static final long EN_ESPERA_IDM = 4;

    public TareaEstado() {
    }
}
