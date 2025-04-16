package ma.est.carrentalnm.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ma.est.carrentalnm.entities.Car;
import ma.est.carrentalnm.service.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {
    private final CarService carService;
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/rental")
    public List<Car> getRentalCars() {
        return carService.getAvailableRentalCars();
    }
    @GetMapping("/all-rental")
    public List<Car> getCars(){
        return carService.getAllRentalCars();
    }
    @GetMapping("/all")
    public List<Car> allCars() {
        return carService.allCars();
    }

    @GetMapping("/sale")
    public List<Car> getCarsForSale() {
        return carService.getAvailableSaleCars();
    }
    @GetMapping("/all-sale")
    public List<Car> getAllCarsForSale() {
        return carService.getAllSaleCars();
    }


    @PostMapping
    public ResponseEntity<?> addCar(@RequestPart Car car ,
                                        @RequestPart MultipartFile imageFile){
        try{
           Car car1 = carService.addCar(car , imageFile);
            return new ResponseEntity<>(car1, HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{id}")
    public Car getCarById(@PathVariable long id){
        return carService.getCarById(id);

    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCar(
            @PathVariable Long id,
            @RequestPart("car") String carJson, // Reçoit l'objet Car sous forme de JSON
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile // Reçoit le fichier image (optionnel)
    ) {
        try {
            // Convertir la chaîne JSON en objet Car
            ObjectMapper objectMapper = new ObjectMapper();
            Car car = objectMapper.readValue(carJson, Car.class);

            // Mettre à jour la voiture
                Car updatedCar = carService.updateCar(id, car, imageFile);

            if (updatedCar != null) {
                return new ResponseEntity<>("updated", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("failed to update", HttpStatus.BAD_REQUEST);
            }
        } catch (IOException e) {
            return new ResponseEntity<>("failed to update: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCar(@PathVariable Long id) {
        Car car = carService.getCarById(id);
        if(car != null){
            carService.deleteCar(id);
            return new ResponseEntity<>("Car deleted" ,HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("Product not found",HttpStatus.NOT_FOUND);
    }

}