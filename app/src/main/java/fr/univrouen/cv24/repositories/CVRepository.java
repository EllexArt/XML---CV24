package fr.univrouen.cv24.repositories;

import fr.univrouen.cv24.entities.Cv24Type;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CVRepository extends CrudRepository<Cv24Type, Long> {
    Cv24Type findById(long id);
}
