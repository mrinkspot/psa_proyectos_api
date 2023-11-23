package psa.api_proyectos.data.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import psa.api_proyectos.domain.models.Tarea;

import java.util.Collection;

@Repository
public interface TareaRepository extends CrudRepository<Tarea, Long> {
    Collection<Tarea> findAllByProyecto_Id(Long proyectoId);
}
