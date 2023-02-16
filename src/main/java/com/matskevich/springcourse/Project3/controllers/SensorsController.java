package com.matskevich.springcourse.Project3.controllers;

import com.matskevich.springcourse.Project3.dto.SensorDTO;
import com.matskevich.springcourse.Project3.models.Sensor;
import com.matskevich.springcourse.Project3.services.SensorService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sensors")
public class SensorsController {
    private final SensorService sensorService;
    private final ModelMapper modelMapper;

    public SensorsController(SensorService sensorService, ModelMapper modelMapper) {
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<SensorDTO> getSensors() {
        return sensorService.findAll().stream().map(this::convertToSensorDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public SensorDTO getSensor(@PathVariable("id") int id) {
        return convertToSensorDTO(sensorService.findOne(id));
    }

    @PostMapping("/registration")
    @Transactional
    public ResponseEntity<HttpStatus> registration(@RequestBody @Valid SensorDTO sensorDTO,
                                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append("-").append(error.getDefaultMessage())
                        .append(";");
            }
            //throw new SensorNotRegisteredException(errorMsg.toString());  //TODO
        }
        sensorService.save(converToSensor(sensorDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private Sensor converToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }

    private SensorDTO convertToSensorDTO(Sensor sensor) {
        return modelMapper.map(sensor, SensorDTO.class);
    }
}
