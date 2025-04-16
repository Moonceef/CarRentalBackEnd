package ma.est.carrentalnm.repositories;

import ma.est.carrentalnm.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

    Role findByName(String roleAdmin);
}
