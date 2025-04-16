package ma.est.carrentalnm.Controller;

import ma.est.carrentalnm.entities.Reservation;
import ma.est.carrentalnm.enums.ReservationStatus;
import ma.est.carrentalnm.service.ReservationService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/rental")
    public Reservation createRentalReservation(@RequestBody Map<String, Object> request) {
        Long carId = Long.parseLong(request.get("carId").toString());
        Long userId = Long.parseLong(request.get("userId").toString());
        LocalDate startDate = LocalDate.parse(request.get("startDate").toString());
        LocalDate endDate = LocalDate.parse(request.get("endDate").toString());

        return reservationService.createRentalReservation(carId, userId, startDate, endDate);
    }

    @PostMapping("/purchase")
    public Reservation createPurchaseReservation(@RequestBody Map<String, Object> request) {
        Long carId = Long.parseLong(request.get("carId").toString());
        Long userId = Long.parseLong(request.get("userId").toString());

        return reservationService.createPurchaseReservation(carId, userId);
    }

    @PutMapping("/{id}/status")
    public Reservation updateStatus(@PathVariable Long id, @RequestBody Map<String, String> request) {
        ReservationStatus newStatus = ReservationStatus.valueOf(request.get("status"));
        return reservationService.updateStatus(id, newStatus);
    }

    @GetMapping("/admin/getRentalReservations")
    public List<Reservation> getRentalReservations() {
        return reservationService.getRentalReservations();
    }

    @GetMapping("/admin/getSalesReservations")
    public List<Reservation> getSalesReservations() {
        return reservationService.getSalesReservations();
    }

    @GetMapping("/client/{userId}/rentals")
    public List<Reservation> getClientRentals(@PathVariable Long userId) {
        return reservationService.getClientRentals(userId);
    }

    @GetMapping("/client/{userId}/sales")
    public List<Reservation> getClientSales(@PathVariable Long userId) {
        return reservationService.getClientSales(userId);
    }
}
