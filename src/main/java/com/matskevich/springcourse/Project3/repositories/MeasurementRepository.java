package com.matskevich.springcourse.Project3.repositories;

import com.matskevich.springcourse.Project3.models.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {
    List<Measurement> findByRaining(boolean raining);
}
