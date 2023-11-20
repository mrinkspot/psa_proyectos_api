package psa.api_proyectos.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ColaboradorTareas")
public class ColaboradorTarea {
    @Id
    public long id;
    @ManyToOne
    public Tarea tarea;
    @ManyToOne
    public Colaborador colaborador;
    public double horasDedicadas;
}
