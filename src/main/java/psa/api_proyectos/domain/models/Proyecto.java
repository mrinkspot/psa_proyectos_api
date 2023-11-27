package psa.api_proyectos.domain.models;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.ArrayList;

@Table(name = "Proyectos")
@Entity
public class Proyecto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private long id;
    private String nombre;
    private String descripcion;
    private Date fechaInicio;
    private Date fechaFin;
    @ManyToOne
    private ProyectoEstado estado;
    private Long liderAsignadoId;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public ProyectoEstado getEstado() {
        return estado;
    }

    public void setEstado(ProyectoEstado estado) {
        this.estado = estado;
    }

    public long getId() {
        return id;
    }

    public void setLiderAsignadoId(Long liderAsignadoId) {
        this.liderAsignadoId = liderAsignadoId;
    }

    public Long getLiderAsignadoId() {
        return liderAsignadoId;
    }
}
