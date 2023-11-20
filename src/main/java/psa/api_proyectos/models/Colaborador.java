package psa.api_proyectos.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Colaboradores")
public class Colaborador {
    @Id
    public long legajo;
    public String nombre;
}
