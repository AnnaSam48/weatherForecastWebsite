package com.accenture.weatherForecastWebsite.version2.repository;


import com.accenture.weatherForecastWebsite.version2.model.City;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ForecastsByCityRepository extends CrudRepository<City, String> {

    Optional<City> findById(String id);
    Streamable<City> findByCityName(String cityName);
}
