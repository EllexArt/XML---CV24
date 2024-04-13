package fr.univrouen.cv24.repositories;

import fr.univrouen.cv24.entities.ProfType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfRepository extends CrudRepository<ProfType, Long> {
}
