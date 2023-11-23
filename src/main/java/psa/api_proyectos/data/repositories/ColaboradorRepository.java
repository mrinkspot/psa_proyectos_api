package psa.api_proyectos.data.repositories;

import org.springframework.data.repository.CrudRepository;
import psa.api_proyectos.domain.models.Colaborador;

public interface ColaboradorRepository extends CrudRepository<Colaborador, Long> {

}
