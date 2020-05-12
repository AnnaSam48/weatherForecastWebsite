package com.accenture.weatherForecastWebsite.repository;

import com.accenture.weatherForecastWebsite.model.Forecast;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ForecastRepository extends CrudRepository<Forecast, String> {

 /*  Optional<Forecast>findByDateOnMachine(long dateOnMachine);
   Optional<Forecast>findByCityName(String cityName);
    List<Forecast> findAllByCityId(long id);
    List<Forecast> findAllByCityName(String cityName);
    List<Forecast> findAllByDateOnMachine(long dateOnMachine);

  */



    Forecast findAllById(String id);
    Forecast findByCityName(String cityName);
}
