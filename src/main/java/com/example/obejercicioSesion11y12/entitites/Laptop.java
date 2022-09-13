package com.example.obejercicioSesion11y12.entitites;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Laptop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String model;
    private Double price;
    private String OperatingSystem;
    private Boolean gamer;

    public Laptop(Long id, String model, Double price, String operatingSystem, Boolean gamer) {
        this.id = id;
        this.model = model;
        this.price = price;
        this.OperatingSystem = operatingSystem;
        this.gamer = gamer;
    }

    public Laptop() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getOperatingSystem() {
        return OperatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        OperatingSystem = operatingSystem;
    }

    public Boolean getGamer() {
        return gamer;
    }

    public void setGamer(Boolean gamer) {
        this.gamer = gamer;
    }

    @Override
    public String toString() {
        return "Laptop{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", price=" + price +
                ", OperatingSystem='" + OperatingSystem + '\'' +
                ", gamer=" + gamer +
                '}';
    }
}
