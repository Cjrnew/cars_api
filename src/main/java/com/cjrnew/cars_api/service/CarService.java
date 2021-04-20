package com.cjrnew.cars_api.service;

import com.cjrnew.cars_api.model.Car;
import com.cjrnew.cars_api.model.dto.CarDTO;
import com.cjrnew.cars_api.repository.CarRepository;
import org.modelmapper.internal.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarService {

    @Autowired
    CarRepository repo;

    /*
    public List<CarDTO> getCars() {                     same thing ::create
        List<CarDTO> list = repo.findAll().stream().map(car -> CarDTO.create(car)).collect(Collectors.toList());
        return list;
     */
    public List<CarDTO> getCars() {
        List<CarDTO> list = repo.findAll().stream().map(CarDTO::create).collect(Collectors.toList());
        return list;
    }

    public Optional<CarDTO> getById(Long id) {
        Optional<Car> car = repo.findById(id);
        return car.map(CarDTO::create);
    }

    public List<CarDTO> getByType(String type) {
        return repo.findByType(type).stream().map(CarDTO::create).collect(Collectors.toList());
    }

    public CarDTO insert(Car car) {
        Assert.isNull(car.getId(),"Não foi possível inserir o registro");

        return CarDTO.create(repo.save(car));
    }

    public CarDTO update(Car car, Long id) {
        Assert.notNull(id,"Não foi possível atualizar o registro");

        // Busca o carro no banco de dados
        Optional<Car> optional = repo.findById(id);
        if(optional.isPresent()) {
            Car db = optional.get();
            // Copiar as propriedades
            db.setName(car.getName());
            db.setType(car.getType());
            System.out.println("Car id " + db.getId());

            // Atualiza o carro
            repo.save(db);

            return CarDTO.create(db);
        } else {
            return null;
            //throw new RuntimeException("Não foi possível atualizar o registro");
        }
    }

    public boolean delete(Long id) {
        return getById(id).map(c -> {
            repo.deleteById(id);
            return true;
        }).orElse(false);
    }
}
