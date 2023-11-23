package psa.api_proyectos.domain.models;

import jakarta.persistence.*;

@Entity
@Table(name = "ColaboradorTareas")
public class ColaboradorTarea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    @ManyToOne
    public Tarea tarea;
    @ManyToOne
    public Colaborador colaborador;
    public double horasDedicadas;
}
