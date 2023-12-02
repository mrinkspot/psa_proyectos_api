package psa.api_proyectos.application.dtos;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.ArrayList;

@Getter
@Setter
public class TareaRequestDto {
    private String descripcion;
    private Date fechaInicio;
    private Date fechaFin;
    private Long estadoIdm;
    private Long colaboradorAsignadoId;
    private ArrayList<Long> ticketIds;
}
