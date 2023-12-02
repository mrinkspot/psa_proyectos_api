package psa.api_proyectos.application.dtos;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class ProyectoRequestDto {
    private String nombre;
    private String descripcion;
    private Date fechaInicio;
    private Date fechaFin;
    private Long estadoIdm;
    private Long liderId;
}
