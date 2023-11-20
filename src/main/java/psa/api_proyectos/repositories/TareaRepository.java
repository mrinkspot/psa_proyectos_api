package psa.api_proyectos.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import psa.api_proyectos.models.Proyecto;
import psa.api_proyectos.models.Tarea;

import java.util.Collection;

@Repository
public interface TareaRepository extends CrudRepository<Tarea, Long> {
    Collection<Tarea> findAllByProyecto_Id(Long proyectoId);
}
