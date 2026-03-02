package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;

public interface CarWriter extends BaseWriter<Car, String>{
    public Car create(Car car);
    public void edit(Car car);
    public void delete(String carId);
} 