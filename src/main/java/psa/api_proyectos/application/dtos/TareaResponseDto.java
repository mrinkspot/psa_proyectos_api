package psa.api_proyectos.application.dtos;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

public class TareaResponseDto {
    public long id;
    public String descripcion;
    public Date fechaInicio;
    public Date fechaFin;
    public String estado;
    public ColaboradorDto colaboradorAsignado;
    // AGREGAR IDS DE TICKETS
    // REVISAR QUE CAMPOS DE PROYECTO SE MUESTRAN EN TAREA
}
