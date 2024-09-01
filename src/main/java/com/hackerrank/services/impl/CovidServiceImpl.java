package com.hackerrank.services.impl;

import com.hackerrank.dto.CovidDto;
import com.hackerrank.entities.Covid;
import com.hackerrank.exceptions.BadRequestException;
import com.hackerrank.exceptions.ElementNotFoundException;
import com.hackerrank.exceptions.ResourceAlreadyExistsException;
import com.hackerrank.repository.CovidRepository;
import com.hackerrank.services.ICovidService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;

@Service
public class CovidServiceImpl implements ICovidService {

    @Autowired
    private CovidRepository covidRepository;

    @Override
    public Covid getCovidDetails(String countryName) {
        return null;
    }

    @Override
    public Covid createCovidDetails(CovidDto covidDto) {
        Covid covid = new Covid();

        BeanUtils.copyProperties(covidDto, covid);

        Optional<Covid> covidDetails = covidRepository.findById(covid.getId());
        if(covidDetails.isPresent()){

            throw new ResourceAlreadyExistsException("Covid Details Already Exists");
        }
        return covidRepository.save(covid);
    }

    @Override
    public List<Covid> getTop5CovidDetails(String top5By) {
        List<Covid> covidList = covidRepository.findAll();

        if(top5By.equalsIgnoreCase("country")) {
            return covidList.stream().sorted(Comparator.comparing(Covid::getCountry)).limit(5)
                    .collect(Collectors.toList());
        } else if (top5By.equalsIgnoreCase("cases")) {
            return covidList.stream().sorted(Comparator.comparing(Covid::getCases)).limit(5)
                    .collect(Collectors.toList());

        } else if (top5By.equalsIgnoreCase("recovered")) {
            return covidList.stream().sorted(Comparator.comparing(Covid::getRecovered)).limit(5)
                    .collect(Collectors.toList());

        } else if (top5By.equalsIgnoreCase("deaths")) {
            return covidList.stream().sorted(Comparator.comparing(Covid::getDeaths)).limit(5)
                    .collect(Collectors.toList());

        }
        throw new BadRequestException("Invalid Request");
    }

    @Override
    public List<Covid> getAllCovidDetails() {
        return covidRepository.findAll();
    }

    @Override
    public Integer getTotalByCovidDetails(String totalBy) {
        List<Covid> covidList = covidRepository.findAll();
        if (totalBy.equalsIgnoreCase("cases")) {
            return covidList.stream().mapToInt(Covid::getCases).sum();
        } else if (totalBy.equalsIgnoreCase("recovered")) {
            return covidList.stream().mapToInt(Covid::getRecovered).sum();
        } else if (totalBy.equalsIgnoreCase("deaths")) {
            return covidList.stream().mapToInt(Covid::getDeaths).sum();
        }
        throw new BadRequestException("Invalid Request");
    }

    @Override
    public Covid deleteCovidDetails(Long id) {

        Optional<Covid> covid = covidRepository.findById(id);
        if(covid.isEmpty()) {
            throw new ElementNotFoundException("Covid Details Not Found");
        }
        covidRepository.deleteById(id);
        return covid.get();
    }

    @Override
    public Covid updateCovidDetails(Long id, CovidDto covidDto) {
        Optional<Covid> covidDetails = covidRepository.findById(id);
        if(covidDetails.isEmpty()){
            throw new ElementNotFoundException("Covid Details not found");
        }

        Covid covid = new Covid();
        BeanUtils.copyProperties(covidDto, covid);
        return covidRepository.save(covid);

    }

    @Override
    public Optional<Covid> getCovidById(Long id) {
        Optional<Covid> covid = covidRepository.findById(id); //Optional<Covid>

        covidRepository.findById(id);

        if(covid.isEmpty()) {
            throw new ElementNotFoundException("Covid Details Not Found");
        }
        return covid;
    }
}
