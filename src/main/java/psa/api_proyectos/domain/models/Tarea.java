package psa.api_proyectos.domain.models;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "Tareas")
public class Tarea {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    public Long id;
    public String descripcion;
    public Date fechaInicio;
    public Date fechaFin;
    public Long colaboradorAsignadoId;

    @ManyToOne
    public Proyecto proyecto;

    @ManyToOne
    public TareaEstado estado;
}
