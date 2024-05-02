package fr.univrouen.cv24.repositories;

import fr.univrouen.cv24.entities.DiplomeType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiplomeRepository extends CrudRepository<DiplomeType, Long> {
}
