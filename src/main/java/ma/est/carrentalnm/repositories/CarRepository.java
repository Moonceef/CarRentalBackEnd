package ma.est.carrentalnm.repositories;

import ma.est.carrentalnm.entities.Car;
import ma.est.carrentalnm.enums.CarType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// Interface pour accéder aux données des voitures dans la base de données
@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    // Trouve les voitures par type et disponibilité
    List<Car> findByTypeAndAvailable(CarType type, Boolean available);
    List<Car> findByType(CarType type);

}
