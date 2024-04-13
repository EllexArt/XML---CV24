package fr.univrouen.cv24.repositories;

import fr.univrouen.cv24.entities.CV;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CVRepository extends CrudRepository<CV, Long> {
    Optional<CV> findById(long id);
}
