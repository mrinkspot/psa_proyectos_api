package psa.api_proyectos.dtos;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Optional;

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
