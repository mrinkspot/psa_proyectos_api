package psa.api_proyectos.data.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import psa.api_proyectos.domain.models.Tarea;
import psa.api_proyectos.domain.models.TareaTicket;

import java.util.Collection;

@Repository
public interface TareaTicketRepository extends CrudRepository<TareaTicket, Long> {
    Collection<TareaTicket> findAllByTicketId(Long ticketId);
    Collection<TareaTicket> findAllByTareaId(Long tareaId);
    TareaTicket findByTareaIdAndTicketId(Long tareaId, Long ticketId);
}