package psa.api_proyectos.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import psa.api_proyectos.models.ProyectoEstado;

@Repository
public interface ProyectoEstadoRepository extends CrudRepository<ProyectoEstado, Long> {

}
