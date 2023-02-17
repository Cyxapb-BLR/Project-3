package com.matskevich.springcourse.Project3.util;

import com.matskevich.springcourse.Project3.models.Sensor;
import com.matskevich.springcourse.Project3.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class SensorValidator implements Validator {
    private final SensorService sensorService;

    @Autowired
    public SensorValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Sensor.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Sensor sensor = (Sensor) target;

        Optional<Sensor> sensorFromDB = sensorService.findByName(sensor.getName());

        if (sensorFromDB.isPresent())
            errors.rejectValue("name", "", "This name already exists");
    }
}
