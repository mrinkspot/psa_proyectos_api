package psa.api_proyectos.domain.models;

import jakarta.persistence.*;

@Entity
@Table(name = "ColaboradorProyectos")
public class ColaboradorProyecto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    @ManyToOne
    public Proyecto proyecto;
    @ManyToOne
    public Colaborador colaborador;
    public String rol;

}
