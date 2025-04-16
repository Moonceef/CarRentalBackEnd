package ma.est.carrentalnm.repositories;

import ma.est.carrentalnm.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Interface pour accéder aux données des utilisateurs dans la base de données
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Trouve un utilisateur par son email
    Optional<User> findByEmail(String email);
    Boolean existsByEmail (String Email);
    Boolean existsByName (String name);
    Boolean existsByTel (String tel);

}