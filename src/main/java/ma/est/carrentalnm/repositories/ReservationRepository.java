package ma.est.carrentalnm.repositories;

import ma.est.carrentalnm.entities.Reservation;

import ma.est.carrentalnm.entities.User;
import ma.est.carrentalnm.enums.ReservationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// Interface pour accéder aux données des réservations dans la base de données
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    // Trouve toutes les réservations d'un client
    List<Reservation> findByClient(User client);
    List<Reservation> findByTypeAndClient(ReservationType type, User client);
    List<Reservation> findByType(ReservationType type);



}