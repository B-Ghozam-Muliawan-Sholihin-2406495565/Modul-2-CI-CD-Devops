package id.ac.ui.cs.advprog.eshop.service;

import org.springframework.stereotype.Service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepository;

@Service
public class CarWriterImpl implements CarWriter{
    private final CarRepository carRepository;

    public CarWriterImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public Car create(Car car) {
        carRepository.create(car);
        return car;
    }

    @Override
    public void edit(Car car) {
        carRepository.edit(car);
    }

    @Override
    public void delete(String carId) {
        carRepository.delete(carId);
    }
}
