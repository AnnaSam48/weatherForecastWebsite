package com.accenture.weatherForecastWebsite.newVersion.controller;

import com.accenture.weatherForecastWebsite.newVersion.model.Cities;
import com.accenture.weatherForecastWebsite.newVersion.repository.ForecastsByCityRepository;
import com.accenture.weatherForecastWebsite.newVersion.weatherAPI.WeatherAPIService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(ForecastRestController.class)
public class ForecastRestControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    WeatherAPIService weatherAPIService;

    @MockBean
    ForecastsByCityRepository mockRepository;


    @Test
    public void testGetWeatherForecastByCityAsJson() {

        Cities tokyo = new Cities("1850144", "tokyo", "JP", 20.2,
                "22:34", "12:41");

        given(weatherAPIService.getForecastByCity("tokyo")).willReturn(tokyo);

        try {
            mockMvc.perform(get("/forecast/tokyo")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", Matchers.is("1850144")))
                    .andExpect(jsonPath("$.cityName", Matchers.is("tokyo")))
                    .andExpect(jsonPath("$.country", Matchers.is("JP")))
                    .andExpect(jsonPath("$.temp", Matchers.is(20.2)))
                    .andExpect(jsonPath("$.sunrise", Matchers.is("22:34")))
                    .andExpect(jsonPath("$.sunset", Matchers.is("12:41")));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testPostWeatherForecastByCity() {

        //language=JSON
        String jsonResponse = "{\"id\":\"6167865\",\"cityName\":\"Toronto\"," +
                "\"country\":\"CA\",\"temp\":12.4,\"sunrise\":\"12:49\",\"sunset\":\"03:39\"}";

        //when(mockRepository.findByCityName(eq("Toronto"))).thenReturn( new Cities())

        try {
            mockMvc.perform(post("/forecast/tokyo")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonResponse))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", Matchers.is("6167865")))
                    .andExpect(jsonPath("$.cityName", Matchers.is("Toronto")))
                    .andExpect(jsonPath("$.country", Matchers.is("CA")))
                    .andExpect(jsonPath("$.temp", Matchers.is(12.4)))
                    .andExpect(jsonPath("$.sunrise", Matchers.is("12:49")))
                    .andExpect(jsonPath("$.sunset", Matchers.is("03:39")));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}