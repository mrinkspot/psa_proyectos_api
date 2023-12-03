package psa.api_proyectos.domain.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TareaEstados")
public class TareaEstado {
    @Id
    public Long idm;
    public String descripcion;

    public TareaEstado(long idm, String descripcion) {
        this.idm = idm;
        this.descripcion = descripcion;
    }

    // TODO: 20231120 - CB - Revisar si existe una definición de los estados de Tarea
    public static long NUEVA_IDM = 1;
    public static long EN_CURSO_IDM = 2;
    public static long TERMINADA_IDM = 3;
    public static long EN_ESPERA_IDM = 4;
    public static String NUEVA_DESCRIPCION = "Nueva";
    public static String EN_CURSO_DESCRIPCION = "En curso";
    public static String TERMINADA_DESCRIPCION = "Terminada";
    public static String EN_ESPERA_DESCRIPCION = "En espera";
    public TareaEstado() {
    }
}
