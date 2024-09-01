package com.hackerrank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackerrank.dto.CovidDto;
import com.hackerrank.entities.Covid;
import com.hackerrank.repository.CovidRepository;
import com.hackerrank.services.impl.CovidServiceImpl;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@WebMvcTest(controllers = CovidController.class)
@ExtendWith(MockitoExtension.class)
public class CovidControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CovidServiceImpl covidServiceImpl;

    @Autowired
    private ObjectMapper objectMapper;

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

    @DisplayName("GetAllCovidDetails")
    @Test
    public void getAllCovidDetails() throws Exception {

        Covid covid1 = new Covid();
        BeanUtils.copyProperties(covidDto1, covid1);

        Covid covid2 = new Covid();
        BeanUtils.copyProperties(covidDto2, covid2);

        List<Covid> covidList = List.of(covid1, covid2);

        when(covidServiceImpl.getAllCovidDetails()).thenReturn(covidList);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/covid-details")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(System.out::println);

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(covidList)));
    }

    @DisplayName("CreateCovidDetails")
    @Test
    public void createCovidDetails() throws Exception {

        Covid covid1 = new Covid();
        BeanUtils.copyProperties(covidDto1, covid1);

        when(covidServiceImpl.createCovidDetails(Mockito.any(CovidDto.class))).thenReturn(covid1);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/covid-details")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(covidDto1)))
                        .andDo(System.out::println);

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(covid1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.country", CoreMatchers.is(covid1.getCountry())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cases", CoreMatchers.is(covid1.getCases())));
    }

    @DisplayName("GetCovidDetailsById")
    @Test
    public void getCovidDetailsForCountry() throws Exception {
        Long id  = 123456L;
        String country = "USA";

        Covid covid1 = new Covid();
        covid1.setCountry(country);
        covid1.setId(id);

        when(covidServiceImpl.getCovidById(id)).thenReturn(Optional.of(covid1));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/covid-details/123456")
//                        .param("country", country)
//                        .param("id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(covid1)))
                        .andDo(System.out::println);

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.country", CoreMatchers.is(covid1.getCountry())));
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(covid1.getId())))
    }

    @DisplayName("DeleteCovidDetailsById")
    @Test
    public void deleteCovidDetailsById() throws Exception{
        Long id = 12345L;
        String country = "India";

        Covid covid = new Covid();
        covid.setId(id);
        covid.setCountry(country);

        when(covidServiceImpl.deleteCovidDetails(id)).thenReturn(covid);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/covid-details/12345")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(covid)))
                            .andDo(System.out::println);

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @DisplayName("UpdateCovidDetails")
    @Test
    public void updateCovidDetails() throws Exception {
        Long id = 12345L;
        Covid covid1 = new Covid();
        BeanUtils.copyProperties(covidDto1,covid1);

        when(covidServiceImpl.updateCovidDetails(id, covidDto1)).thenReturn(covid1);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/covid-details/12345")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(covidDto1)))
                        .andDo(System.out::println);

        response.andExpect(MockMvcResultMatchers.status().isOk());
//                .andExpect(MockMvcResultMatchers.jsonPath("$.country", CoreMatchers.is(covid1.getCountry())));
//                .andExpect(MockMvcResultMatchers.jsonPath("$.cases", CoreMatchers.is(covid1.getCases())));
    }

}
