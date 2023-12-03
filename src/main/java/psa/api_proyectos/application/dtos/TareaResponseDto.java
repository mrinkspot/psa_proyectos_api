package psa.api_proyectos.application.dtos;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.ArrayList;

public class TareaResponseDto {
    public long id;
    public String descripcion;
    public Date fechaInicio;
    public Date fechaFin;
    public Long estadoIdm;
    public String estado;
    public ColaboradorDto colaboradorAsignado;
    public ArrayList<Long> ticketIds;
}
