package psa.api_proyectos.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.sql.Date;

@Entity
@Table(name = "Tareas")
public class Tarea {
    @Id
    public long id;

    public String descripcion;

    public Date fechaInicio;
    public Date fechaFin;
    @ManyToOne
    public Proyecto Proyecto;

    @ManyToOne
    public TareaEstado Estado;
}
