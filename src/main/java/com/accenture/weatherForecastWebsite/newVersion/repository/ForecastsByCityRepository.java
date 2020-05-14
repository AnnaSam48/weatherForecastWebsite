package com.accenture.weatherForecastWebsite.newVersion.repository;

import com.accenture.weatherForecastWebsite.newVersion.model.Cities;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ForecastsByCityRepository  extends CrudRepository<Cities, String> {
    Optional<Cities> findById(String id);
    Cities findByCityName(String cityName);
}
