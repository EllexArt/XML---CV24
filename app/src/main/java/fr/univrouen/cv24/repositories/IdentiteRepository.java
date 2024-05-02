package fr.univrouen.cv24.repositories;

import fr.univrouen.cv24.entities.GenreType;
import fr.univrouen.cv24.entities.IdentiteType;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IdentiteRepository extends CrudRepository<IdentiteType, Long>  {

    Optional<IdentiteType> findByNomAndPrenomAndGenreAndTel(
            final String nom,
            final String prenom,
            final GenreType genre,
            final String tel);

}
