package psa.api_proyectos.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ColaboradorProyectos")
public class ColaboradorProyecto {
    @Id
    public long id;
    @ManyToOne
    public Proyecto proyecto;
    @ManyToOne
    public Colaborador colaborador;
    public String rol;

}
