package fr.univrouen.cv24.repositories;

import fr.univrouen.cv24.entities.DetailType;
import org.springframework.data.repository.CrudRepository;

public interface DetailRepository extends CrudRepository<DetailType, Long> {
}
