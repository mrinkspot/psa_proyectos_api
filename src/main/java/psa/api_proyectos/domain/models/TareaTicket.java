package psa.api_proyectos.domain.models;

import jakarta.persistence.*;

// TODO: pensar si hay que agregar algun campo en la relacion
@Entity
@Table(name = "TareaTickets")
public class TareaTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    public long id;
    public long tareaId;
    public long ticketId;
}
