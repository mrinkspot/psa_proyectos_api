package psa.api_proyectos.data.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import psa.api_proyectos.domain.models.ProyectoEstado;

@Repository
public interface ProyectoEstadoRepository extends CrudRepository<ProyectoEstado, Long> {

}
