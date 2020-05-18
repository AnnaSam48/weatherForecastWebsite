package com.accenture.weatherForecastWebsite.newVersion.controller;

import com.accenture.weatherForecastWebsite.newVersion.model.Cities;
import com.accenture.weatherForecastWebsite.newVersion.repository.ForecastsByCityRepository;
import com.accenture.weatherForecastWebsite.newVersion.weatherAPI.WeatherAPIService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(ForecastRestController.class)
public class ForecastRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    WeatherAPIService weatherAPIService;

    @MockBean
    ForecastsByCityRepository mockRepository;


    @Test
    public void testGetWeatherForecastByCityJson() throws Exception {

        Cities tokyo = new Cities("1850144" ,"tokyo" ,"JP" ,20.2, "22:34","12:41");

        given(weatherAPIService.getForecastByCity("tokyo")).willReturn(tokyo);

        mockMvc.perform(get("/forecast/tokyo")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is("1850144")))
                .andExpect(jsonPath("$.cityName", Matchers.is("tokyo")))
                .andExpect(jsonPath("$.country", Matchers.is("JP")))
                .andExpect(jsonPath("$.temp", Matchers.is(20.2)))
                .andExpect(jsonPath("$.sunrise", Matchers.is("22:34")))
                .andExpect(jsonPath("$.sunset", Matchers.is("12:41")));
    }


}