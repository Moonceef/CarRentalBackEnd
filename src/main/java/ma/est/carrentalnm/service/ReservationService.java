package ma.est.carrentalnm.service;

import ma.est.carrentalnm.entities.Car;
import ma.est.carrentalnm.entities.Reservation;
import ma.est.carrentalnm.entities.User;
import ma.est.carrentalnm.enums.ReservationStatus;
import ma.est.carrentalnm.enums.ReservationType;
import ma.est.carrentalnm.repositories.CarRepository;
import ma.est.carrentalnm.repositories.ReservationRepository;
import ma.est.carrentalnm.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final CarRepository carRepository;
    private final UserRepository userRepository;

    public ReservationService(ReservationRepository reservationRepository, CarRepository carRepository, UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.carRepository = carRepository;
        this.userRepository = userRepository;
    }


    public Reservation createRentalReservation(Long carId, Long userId, LocalDate startDate, LocalDate endDate) {
        User client = userRepository.findById(userId).orElseThrow();
        Car car = carRepository.findById(carId).orElseThrow();

        Reservation reservation = new Reservation();
        reservation.setClient(client);
        reservation.setCar(car);
        reservation.setStartDate(startDate);
        reservation.setEndDate(endDate);
        reservation.setType(ReservationType.RENTAL);

        long days = ChronoUnit.DAYS.between(startDate, endDate);
        reservation.setTotalPrice(days * car.getPrice());

        return reservationRepository.save(reservation);
    }


    public Reservation createPurchaseReservation(Long carId, Long userId) {
        User client = userRepository.findById(userId).orElseThrow();
        Car car = carRepository.findById(carId).orElseThrow();

        Reservation reservation = new Reservation();
        reservation.setClient(client);
        reservation.setCar(car);
        reservation.setType(ReservationType.PURCHASE);
        reservation.setTotalPrice(car.getPrice());

        return reservationRepository.save(reservation);
    }


    public Reservation updateStatus(Long reservationId, ReservationStatus newStatus) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow();
        reservation.setStatus(newStatus);

        if (newStatus == ReservationStatus.APPROVED) {
            Car car = reservation.getCar();
            car.setAvailable(false);
            carRepository.save(car);
        }

        return reservationRepository.save(reservation);
    }


    public List<Reservation> getRentalReservations() {
        return reservationRepository.findByType(ReservationType.RENTAL);
    }


    public List<Reservation> getSalesReservations() {
        return reservationRepository.findByType(ReservationType.PURCHASE);
    }


    public List<Reservation> getClientRentals(Long userId) {
        User client = userRepository.findById(userId).orElseThrow();
        return reservationRepository.findByTypeAndClient(ReservationType.RENTAL, client);
    }


    public List<Reservation> getClientSales(Long userId) {
        User client = userRepository.findById(userId).orElseThrow();
        return reservationRepository.findByTypeAndClient(ReservationType.PURCHASE, client);
    }
}
