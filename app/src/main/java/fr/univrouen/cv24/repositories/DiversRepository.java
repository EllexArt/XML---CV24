package fr.univrouen.cv24.repositories;

import fr.univrouen.cv24.entities.DiversType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiversRepository extends CrudRepository<DiversType, Long> {
}
