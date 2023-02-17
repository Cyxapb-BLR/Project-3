package com.matskevich.springcourse.Project3.dto;

import java.util.List;

public class SensorResponse {
    private List<SensorDTO> sensors;

    public SensorResponse(List<SensorDTO> sensors) {
        this.sensors = sensors;
    }

    public List<SensorDTO> getSensors() {
        return sensors;
    }

    public void setSensors(List<SensorDTO> sensors) {
        this.sensors = sensors;
    }
}
