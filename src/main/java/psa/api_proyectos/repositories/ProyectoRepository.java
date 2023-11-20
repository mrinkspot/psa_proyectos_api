package psa.api_proyectos.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import psa.api_proyectos.models.ProyectoModel;

@Repository
public interface ProyectoRepository extends CrudRepository<ProyectoModel, Long> {

}
