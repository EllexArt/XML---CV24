package fr.univrouen.cv24.repositories;

import fr.univrouen.cv24.entities.LvType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LvRepository extends CrudRepository<LvType, Long> {
}
