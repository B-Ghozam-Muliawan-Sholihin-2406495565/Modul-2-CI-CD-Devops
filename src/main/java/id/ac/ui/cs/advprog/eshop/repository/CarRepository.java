package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;

import java.util.Iterator;

public interface CarRepository extends BaseRepository<Car, String>{
    public Car create(Car car);
    public Iterator<Car> findAll();
    public Car findById(String id);
    public void edit(Car updatedCar) ;
    public boolean delete(String id);
}