package com.cjrnew.cars_api.repository;

import com.cjrnew.cars_api.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByType(String type);
}
