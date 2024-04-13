package fr.univrouen.cv24.repositories;

import fr.univrouen.cv24.entities.CV;
import org.springframework.data.repository.CrudRepository;

public interface CVRepository extends CrudRepository<CV, Long> {
    CV findById(long id);
}
