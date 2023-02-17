package com.matskevich.springcourse.Project3.controllers;

import com.matskevich.springcourse.Project3.dto.MeasurementDTO;
import com.matskevich.springcourse.Project3.models.Measurement;
import com.matskevich.springcourse.Project3.services.MeasurementService;
import com.matskevich.springcourse.Project3.util.ErrorResponse;
import com.matskevich.springcourse.Project3.util.MeasurementValidator;
import com.matskevich.springcourse.Project3.util.SensorOrMeasurementException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static com.matskevich.springcourse.Project3.util.ErrorUtil.returnErrorsToClient;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {
    private final MeasurementService measurementService;
    private final ModelMapper modelMapper;
    private final MeasurementValidator measurementValidator;

    @Autowired
    public MeasurementController(MeasurementService measurementService, ModelMapper modelMapper, MeasurementValidator measurementValidator) {
        this.measurementService = measurementService;
        this.modelMapper = modelMapper;
        this.measurementValidator = measurementValidator;
    }

    @GetMapping
    public List<MeasurementDTO> getMeasurements() {
        List<Measurement> measurements = measurementService.findAll();

        return measurements.stream().map(this::convertToMeasurementDTO)
                .collect(Collectors.toList());      //Jackson convert objects->JSON

    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO,
                                                     BindingResult bindingResult) {
        Measurement measurement = convertToMeasurement(measurementDTO);

        measurementValidator.validate(measurement, bindingResult);

        if (bindingResult.hasErrors()) {
            returnErrorsToClient(bindingResult);
        }
        measurementService.add(measurement);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/rainyDaysCount")
    public Long rainyDaysCount() {
        return measurementService.findAll().stream().filter(Measurement::isRaining).count();
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(SensorOrMeasurementException e) {
        ErrorResponse response = new ErrorResponse(e.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        return modelMapper.map(measurementDTO, Measurement.class);
    }

}
