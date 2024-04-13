package fr.univrouen.cv24.repositories;

import fr.univrouen.cv24.entities.ObjectifType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObjectifRepository extends CrudRepository<ObjectifType, Long> {
}
