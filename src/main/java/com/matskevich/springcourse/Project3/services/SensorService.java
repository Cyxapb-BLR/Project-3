package com.matskevich.springcourse.Project3.services;

import com.matskevich.springcourse.Project3.models.Sensor;
import com.matskevich.springcourse.Project3.repositories.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class SensorService {
    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public List<Sensor> findAll() {
        return sensorRepository.findAll();
    }

    public Sensor findOne(int id) {
        return sensorRepository.findById(id).orElse(null);
    }

    public void save(Sensor sensor) {
        sensorRepository.save(sensor);
    }
    public Sensor findByName(String name){
      return   sensorRepository.findByName(name);
    }
}
