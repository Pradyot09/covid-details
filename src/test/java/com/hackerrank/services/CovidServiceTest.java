package com.hackerrank.services;

import com.hackerrank.dto.CovidDto;
import com.hackerrank.entities.Covid;
import com.hackerrank.repository.CovidRepository;
import com.hackerrank.services.impl.CovidServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CovidServiceTest {

    @Mock
    private CovidRepository covidRepository;

    @InjectMocks
    private CovidServiceImpl covidServiceImpl;

    private CovidDto covidDto1;

    private CovidDto covidDto2;

    @BeforeEach
    public void setup() {
        covidDto1 = CovidDto.builder()
                .country("India")
                .cases(2200)
                .recovered(2000)
                .deaths(200)
                .build();
        covidDto2 = CovidDto.builder()
                .country("US")
                .cases(3200)
                .recovered(2000)
                .deaths(1200)
                .build();
    }

    @DisplayName("CreateCovidDetails")
    @Test
    public void CovidService_CreateCovidDetails_ReturnCovidDto() {
        Covid covid1 = new Covid();
        BeanUtils.copyProperties(covidDto1, covid1);

        // Mocking the covidRepository.save method to return this instance of
        // Covid whenever any instance of Employee is passed.
        when(covidRepository.save(Mockito.any(Covid.class))).thenReturn(covid1);
//        when(covidRepository.save(covid1)).thenReturn(covid1);
        Covid saveCovidDetails = covidServiceImpl.createCovidDetails(covidDto1);
        Assertions.assertThat(saveCovidDetails).isEqualTo(covid1);

    }

    @DisplayName("GetAllCovidDetails")
    @Test
    public void CovidService_GetAllCovidDetails_ReturnCovidDto() {

        Covid covid1 = new Covid();
        Covid covid2 = new Covid();
        BeanUtils.copyProperties(covidDto1, covid1);
        BeanUtils.copyProperties(covidDto2, covid2);

        List<Covid> covidList = List.of(covid1, covid2);

        when(covidRepository.findAll()).thenReturn(covidList);

        List<Covid> allCovidDetails = covidServiceImpl.getAllCovidDetails();
        Assertions.assertThat(allCovidDetails).isEqualTo(covidList);
    }

    @DisplayName("GetCovidDetailsById")
    @Test
    public void CovidService_GetCovidDetailsById_ReturnCovidDto() {

        Covid covid1 = new Covid();
        BeanUtils.copyProperties(covidDto1, covid1);

        when(covidRepository.findById(covid1.getId())).thenReturn(Optional.of(covid1));

        Optional<Covid> covidDetails = covidServiceImpl.getCovidById(covid1.getId());
        Assertions.assertThat(covidDetails).isEqualTo(Optional.of(covid1));
    }

    @DisplayName("GetTop5CovidDetails")
    @Test
    public void CovidService_GetTop5CovidDetails_ReturnCovidDto() {

        String [] top5By = {"country", "cases", "recovered", "deaths"};
        Random random = new Random();
        int index = random.nextInt(top5By.length);

        Covid covid1 = new Covid();
        BeanUtils.copyProperties(covidDto1, covid1);

        Covid covid2 = new Covid();
        BeanUtils.copyProperties(covidDto2, covid2);

        Covid covid3 = new Covid();
        covid3.setCountry("China");
        covid3.setCases(10);
        covid3.setRecovered(5);
        covid3.setDeaths(3);

        Covid covid4 = new Covid();
        covid4.setCountry("Japan");
        covid4.setCases(20);
        covid4.setRecovered(10);
        covid4.setDeaths(5);

        Covid covid5 = new Covid();
        covid5.setCountry("Canada");
        covid5.setCases(30);
        covid5.setRecovered(15);
        covid5.setDeaths(8);

        Covid covid6 = new Covid();
        covid6.setCountry("UK");
        covid6.setCases(40);
        covid6.setRecovered(20);
        covid6.setDeaths(10);


        List<Covid> covidList = List.of(covid1, covid2, covid3, covid4, covid5, covid6);

        when(covidRepository.findAll()).thenReturn(covidList);

        List<Covid> top5CovidDetails = covidServiceImpl.getTop5CovidDetails(top5By[index]);

        Assertions.assertThat(top5CovidDetails.size()).isEqualTo(5);

    }


    @DisplayName("GetTotalByCovidDetails")
    @Test
    public void CovidService_GetTotalByCovidDetails_ReturnCovidDto() {

        String [] totalByCovidDetails = { "cases", "recovered", "deaths"};

        Random random = new Random();
        int index = random.nextInt(totalByCovidDetails.length);

        Covid covid1 = new Covid();
        covid1.setCases(10);
        covid1.setRecovered(5);
        covid1.setDeaths(3);

        Covid covid2 = new Covid();
        covid2.setCases(20);
        covid2.setRecovered(10);
        covid2.setDeaths(5);

        List<Covid> covidList = List.of(covid1, covid2);

        when(covidRepository.findAll()).thenReturn(covidList);

        int totalCovidDetails = covidServiceImpl.getTotalByCovidDetails(totalByCovidDetails[index]);

        if(totalByCovidDetails[index].equalsIgnoreCase("cases")) {
            Assertions.assertThat(totalCovidDetails).isEqualTo(30);
        } else if(totalByCovidDetails[index].equalsIgnoreCase("recovered")) {
            Assertions.assertThat(totalCovidDetails).isEqualTo(15);
        } else if(totalByCovidDetails[index].equalsIgnoreCase("deaths")) {
            Assertions.assertThat(totalCovidDetails).isEqualTo(8);
        }
    }

}





