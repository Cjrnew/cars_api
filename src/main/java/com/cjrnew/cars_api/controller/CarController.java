package com.cjrnew.cars_api.controller;

import com.cjrnew.cars_api.model.Car;
import com.cjrnew.cars_api.model.dto.CarDTO;
import com.cjrnew.cars_api.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/cars")
public class CarController {

    @Autowired
    CarService service;

    @GetMapping()
    public ResponseEntity getAll() {
        List<CarDTO> cars = service.getCars();
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable("id") Long id) {
        Optional<CarDTO> car = service.getById(id);

        return car
                .map(c -> ResponseEntity.ok(car))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/type/{type}")
    public ResponseEntity getByType(@PathVariable("model") String type) {
        List<CarDTO> cars = service.getByType(type);

        return cars.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(cars);
    }

    @PostMapping
    public ResponseEntity post(@RequestBody Car car) {

        CarDTO c = service.insert(car);

        URI location = getUri(c.getId());
        return ResponseEntity.created(location).build();
    }

    private URI getUri(Long id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(id).toUri();
    }

    @PutMapping("/{id}")
    public ResponseEntity put(@PathVariable("id") Long id, @RequestBody Car car) {

        car.setId(id);

        CarDTO c = service.update(car, id);

        return c != null ?
                ResponseEntity.ok(c) :
                ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        service.delete(id);

        return ResponseEntity.ok().build();
    }
}
