package com.accenture.weatherForecastWebsite.repository;

import com.accenture.weatherForecastWebsite.model.Forecast;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForecastRepository extends CrudRepository<Forecast, Long> {
    Forecast findAllByID(Long id);
    Forecast findAllByName(String name);
}
