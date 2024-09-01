package com.hackerrank.controller;

import com.hackerrank.dto.CovidDto;
import com.hackerrank.entities.Covid;
import com.hackerrank.services.ICovidService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class CovidController {

    private final ICovidService iCovidService;

    @GetMapping("/covid-details")
    public List<Covid> getCovidDetails() {
        return iCovidService.getAllCovidDetails();
    }

    @PostMapping("/covid-details")
    @ResponseStatus(HttpStatus.CREATED)
    public Covid createCovidDetails(@RequestBody CovidDto covidDto) {
        return iCovidService.createCovidDetails(covidDto);
    }


    @DeleteMapping("/covid-details/{id}")
    public Covid deleteCovidDetails(@PathVariable Long id) {
        return iCovidService.deleteCovidDetails(id);
    }


    @PutMapping("/covid-details/{id}")
    public Covid updateCovidDetails(@PathVariable Long id, @RequestBody CovidDto covidDto) {
        return iCovidService.updateCovidDetails(id, covidDto);
    }

    @GetMapping("/top5By")
    public List<Covid> getTop5CovidDetails(@RequestParam String top5By) {
        return iCovidService.getTop5CovidDetails(top5By);
    }

    @GetMapping("/total-by-country")
    public Integer getTotalByCovidDetails(String countryName) {
        return iCovidService.getTotalByCovidDetails(countryName);
    }

    @GetMapping("/covid-details/{id}")
    public Optional<Covid> getCovidById(@PathVariable Long id) {
        return iCovidService.getCovidById(id);
    }
}
