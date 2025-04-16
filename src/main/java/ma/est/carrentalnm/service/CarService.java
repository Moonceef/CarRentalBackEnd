package ma.est.carrentalnm.service;

import jakarta.transaction.Transactional;
import ma.est.carrentalnm.entities.Car;
import ma.est.carrentalnm.enums.CarType;
import ma.est.carrentalnm.repositories.CarRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    // Obtenir toutes les voitures disponibles à la location
    public List<Car> getAvailableRentalCars() {
        return carRepository.findByTypeAndAvailable(CarType.RENTAL, true);
    }

    public List<Car> getAllRentalCars() {
        return carRepository.findByType(CarType.RENTAL);
    }

    public List<Car> getAllSaleCars() {
        return carRepository.findByType(CarType.SALE);
    }


    // Obtenir toutes les voitures disponibles à la vente
    public List<Car> getAvailableSaleCars() {
        return carRepository.findByTypeAndAvailable(CarType.SALE, true);
    }



    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }

    @Transactional
    public Car addCar(Car car, MultipartFile imageFile) throws IOException {
        car.setImageName(imageFile.getOriginalFilename());
        car.setImageType(imageFile.getContentType());
        car.setImageData(imageFile.getBytes());
        return carRepository.save(car);
    }

    public Car getCarById(long id) {
      return   carRepository.findById(id)
              .orElseThrow(() -> new RuntimeException("Voiture non trouvée avec l'ID : " + id));

    }

    public Car updateCar(long id, Car car, MultipartFile imageFile) throws IOException {
        if (imageFile != null){
            car.setImageData(imageFile.getBytes());
            car.setImageName(imageFile.getOriginalFilename());
            car.setImageType(imageFile.getContentType());
        }
        car.setId(id);
        return carRepository.save(car);
    }
    public List<Car> allCars(){
        return carRepository.findAll();
    }
}
