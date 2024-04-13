package fr.univrouen.cv24.repositories;

import fr.univrouen.cv24.entities.CV;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CVRepository extends CrudRepository<CV, Long> {
    CV findById(long id);

    @Query(value = "SELECT * FROM CV WHERE" +
            " CAST((xpath('/cv24:cv24/cv24:identite/cv24:nom/text()', content, array[array['cv24','http://univ.fr/cv24']]))[1] as varchar) = ?1" +
            " and CAST((xpath('/cv24:cv24/cv24:identite/cv24:prenom/text()', content, array[array['cv24','http://univ.fr/cv24']]))[1] as varchar) = ?2" +
            " and CAST((xpath('/cv24:cv24/cv24:identite/cv24:genre/text()', content, array[array['cv24','http://univ.fr/cv24']]))[1] as varchar) = ?3" +
            " and CAST((xpath('/cv24:cv24/cv24:identite/cv24:tel/text()', content, array[array['cv24','http://univ.fr/cv24']]))[1] as varchar) = ?4",
            nativeQuery = true)
    Optional<CV> findByNameAndFirstNameAndGenderAndPhone(String name, String firstName, String gender, String phone);
}
