package com.matskevich.springcourse.Project3.controllers;

import com.matskevich.springcourse.Project3.dto.SensorDTO;
import com.matskevich.springcourse.Project3.models.Sensor;
import com.matskevich.springcourse.Project3.services.SensorService;
import com.matskevich.springcourse.Project3.util.SensorOrMeasurementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sensors")
public class SensorController {
    private final SensorService sensorService;

    @Autowired
    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @GetMapping
    public List<SensorDTO> getSensors() {
        List<Sensor> all = sensorService.findAll();
        return sensorService.findAll().stream().map(this::convertToSensorDTO)
                .collect(Collectors.toList());      //Jackson convert objects->JSON
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registration(@RequestBody @Valid SensorDTO sensorDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError error : fieldErrors) {
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new SensorOrMeasurementException(errorMsg.toString());
        }
        sensorService.register(convertToSensor(sensorDTO));

        //sent HTTp response with empty body, status 200
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private SensorDTO convertToSensorDTO(Sensor sensor) {  //TODO modelMapper
        SensorDTO sensorDTO = new SensorDTO();
        sensorDTO.setName(sensor.getName());

        return sensorDTO;
    }

    private Sensor convertToSensor(SensorDTO sensorDTO) {
        Sensor sensor = new Sensor();
        sensor.setName(sensorDTO.getName());

        return sensor;
    }

}
