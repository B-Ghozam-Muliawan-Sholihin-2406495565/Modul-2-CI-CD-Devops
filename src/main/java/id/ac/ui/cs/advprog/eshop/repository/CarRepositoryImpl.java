package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Repository
public class CarRepositoryImpl implements CarRepository {
    private List<Car> carData = new ArrayList<>();

    public Car create(Car car){
        if(car.getCarId() == null){
            UUID uuid = UUID.randomUUID();
            car.setCarId(uuid.toString());
        }
        carData.add(car);
        return car;
    }

    public Iterator<Car> findAll(){
        return carData.iterator();
    }

    public Car findById(String id) {
        for (Car car : carData) {
            if (car.getCarId().equals(id)) {
                return car;
            }
        }
        return null;
    }

    public void edit(Car updatedCar) {
        try{
            Car existingCar = findById(updatedCar.getCarId());
            existingCar.setCarName(updatedCar.getCarName());
            existingCar.setCarQuantity(updatedCar.getCarQuantity());
        }catch(NoSuchElementException e){
            System.out.println("Product not found - " + e.getMessage());
            throw e;
        }
    }

    public void delete(String id) {
        carData.removeIf(car -> car.getCarId().equals(id));
    }
}