package psa.api_proyectos.application.dtos;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.ArrayList;

@Getter
@Setter
public class ProyectoDto {
    private String nombre;
    private String descripcion;
    private Date fechaInicio;
    private Date fechaFin;
    private Long estadoIdm;
    private ArrayList<Long> lideresIds;
    private ArrayList<Long> miembrosIds;
}