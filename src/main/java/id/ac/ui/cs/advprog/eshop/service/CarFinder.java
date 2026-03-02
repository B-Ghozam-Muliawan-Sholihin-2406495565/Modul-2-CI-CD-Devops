package id.ac.ui.cs.advprog.eshop.service;

import java.util.List;

import id.ac.ui.cs.advprog.eshop.model.Car;

public interface CarFinder {
    public List<Car> findAll();
    public Car findById(String carId);
}
