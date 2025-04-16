package ma.est.carrentalnm.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import ma.est.carrentalnm.enums.CarType;

import java.time.LocalDate;

// Cette classe représente une voiture dans notre système
@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int seats;
    private String transmission ;
    private String fuel;
    private String power;
    private Double price;
    private Boolean available = true;
    private String imageName;
    private String imageType;
    @Lob
    private byte[] imageData;
    @Enumerated(EnumType.STRING)
    private CarType type;


    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }


    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }



    public Boolean getAvailable() { return available; }
    public void setAvailable(Boolean available) { this.available = available; }

    public CarType getType() { return type; }
    public void setType(CarType type) { this.type = type; }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

}