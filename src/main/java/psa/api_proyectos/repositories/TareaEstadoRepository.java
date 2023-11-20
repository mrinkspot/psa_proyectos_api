package psa.api_proyectos.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import psa.api_proyectos.models.TareaEstado;

@Repository
public interface TareaEstadoRepository extends CrudRepository<TareaEstado, Long> {

}