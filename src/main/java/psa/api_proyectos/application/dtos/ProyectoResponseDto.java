package psa.api_proyectos.application.dtos;

import java.sql.Date;
import java.util.ArrayList;

public class ProyectoResponseDto {
    public long id;
    public String nombre;
    public String descripcion;
    public Date fechaInicio;
    public Date fechaFin;
    public String estado;
    public ColaboradorDto liderAsignado;
    public ArrayList<TareaResponseDto> tareas;
}
