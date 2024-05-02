package fr.univrouen.cv24.repositories;

import fr.univrouen.cv24.entities.CertifType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertifRepository extends CrudRepository<CertifType, Long> {
}
