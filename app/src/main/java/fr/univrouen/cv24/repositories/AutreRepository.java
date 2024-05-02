package fr.univrouen.cv24.repositories;

import fr.univrouen.cv24.entities.AutreType;
import fr.univrouen.cv24.entities.Cv24Type;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutreRepository extends CrudRepository<AutreType, Long> {
}
