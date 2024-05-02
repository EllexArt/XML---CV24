package fr.univrouen.cv24.repositories;

import fr.univrouen.cv24.entities.CompetencesType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompetencesRepository extends CrudRepository<CompetencesType, Long> {
}
