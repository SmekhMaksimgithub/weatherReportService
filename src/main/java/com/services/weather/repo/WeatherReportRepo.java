package com.services.weather.repo;

import com.services.weather.models.WeatherReport;
import org.springframework.data.repository.CrudRepository;

public interface WeatherReportRepo extends CrudRepository<WeatherReport, Long> {

}
