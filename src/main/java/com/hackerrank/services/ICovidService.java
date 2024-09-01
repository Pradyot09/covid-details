package com.hackerrank.services;
import com.hackerrank.dto.CovidDto;
import com.hackerrank.entities.Covid;

import java.util.List;
import java.util.Optional;

public interface ICovidService {

    Covid getCovidDetails(String countryName);

    Covid createCovidDetails(CovidDto covidDto);

    List<Covid> getTop5CovidDetails(String top5By);

    List<Covid> getAllCovidDetails();

    Integer getTotalByCovidDetails(String totalBy);

    Covid deleteCovidDetails(Long id);


    Covid updateCovidDetails(Long id, CovidDto covidDto);

    Optional<Covid> getCovidById(Long id);


}
