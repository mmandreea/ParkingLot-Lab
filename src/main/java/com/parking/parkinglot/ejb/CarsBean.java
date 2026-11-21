package com.parking.parkinglot.ejb;

import com.parking.parkinglot.common.CarDto;
import com.parking.parkinglot.entities.Car;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class CarsBean {

    private static final Logger LOG = Logger.getLogger(CarsBean.class.getName());
    @PersistenceContext
    EntityManager entityManager;

    public List<CarDto> findAllCars(){

        LOG.info("findAllCars");
        try{

            TypedQuery<Car> typedQuery= entityManager.createQuery("select c from Car c", Car.class);
            List<Car> cars = typedQuery.getResultList();
            return copyCarsToDto(cars);


        }catch(Exception e){
            throw new EJBException(e.getMessage());
        }

    }
    private List<CarDto> copyCarsToDto(List<Car> cars){
        List<CarDto> dtos = new ArrayList<>();
        for(Car car : cars){
            Long id=car.getId();
            String licensePlate=car.getLicensePlate();
            String parkingSpot=car.getParkingSpot();
            String ownerName=car.getOwner().getUsername();
            dtos.add(new CarDto(id,  licensePlate, parkingSpot, ownerName));
        }
        return dtos;
    }

    /*

    public void createCar(String licencePlate, String parkingSpot, Long userId){
        LOG.info("createCar");
        Car car=new Car();
        car.setLicensePlate(licencePlate);
        car.setParkingSpot(parkingSpot);

        User user=entityManager.find(User.class,userId);
        user.getCars().add(car);
        car.setOwner(user);

        entityManager.persist(car);

    }
    */

}
