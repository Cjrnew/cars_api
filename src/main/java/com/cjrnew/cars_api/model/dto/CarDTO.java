package com.cjrnew.cars_api.model.dto;

import com.cjrnew.cars_api.model.Car;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
public class CarDTO {

    private Long id;
    private String name;
    private String type;

    /*
    public CarDTO(Car car){
        this.id = car.getId();
        this.name = car.getName();
        this.type = car.getType();
    }
     */

    public static CarDTO create (Car car) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(car, CarDTO.class);
    }
}
