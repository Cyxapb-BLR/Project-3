package com.matskevich.springcourse.Project3.repositories;

import com.matskevich.springcourse.Project3.models.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorRepositories extends JpaRepository<Sensor, Integer> {
}
