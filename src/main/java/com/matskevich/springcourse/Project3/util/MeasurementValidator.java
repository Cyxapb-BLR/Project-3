package com.matskevich.springcourse.Project3.util;

import com.matskevich.springcourse.Project3.models.Measurement;
import com.matskevich.springcourse.Project3.models.Sensor;
import com.matskevich.springcourse.Project3.services.MeasurementService;
import com.matskevich.springcourse.Project3.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class MeasurementValidator implements Validator {
    private final MeasurementService measurementService;
    private final SensorService sensorService;

    @Autowired
    public MeasurementValidator(MeasurementService measurementService, SensorService sensorService) {
        this.measurementService = measurementService;
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Measurement.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Measurement measurement = (Measurement) target;
        Sensor sensor = measurement.getSensor();

        if (sensor == null) {
            return;
        }

        Optional<Sensor> sensorFromDB = sensorService.findByName(sensor.getName());
        if (sensorFromDB.isEmpty()) {
            errors.rejectValue("sensor", "", "No sensors with this name");
        }
    }
}
