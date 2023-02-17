package com.matskevich.springcourse.Project3.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class SensorDTO {

    @NotEmpty(message = "Название не должно быть пустым!")
    @Size(min = 3, max = 30, message = "Name should be between 3 and 30 characters")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
