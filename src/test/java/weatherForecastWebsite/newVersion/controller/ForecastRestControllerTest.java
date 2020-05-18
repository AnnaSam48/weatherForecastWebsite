package weatherForecastWebsite.newVersion.controller;

import com.accenture.weatherForecastWebsite.WeatherForecastWebsiteApplication;
import com.accenture.weatherForecastWebsite.newVersion.model.Cities;
import com.accenture.weatherForecastWebsite.newVersion.repository.ForecastsByCityRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

    @RunWith(SpringRunner.class)
    @SpringBootTest(classes = WeatherForecastWebsiteApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // for restTemplate
    @ActiveProfiles("test")
    public class ForecastRestControllerTest {

        private static final ObjectMapper om = new ObjectMapper();

        @Autowired
        private TestRestTemplate restTemplate;

        @MockBean
        private ForecastsByCityRepository mockRepository;

        @Before
        public void init() {

            Cities cities = new Cities("1850144", "Tokyo", "JP", 296.19, "1589571333","1589621991",
                    Timestamp.valueOf("2020-05-18 07:20:00"));
            when(mockRepository.findById("1850147")).thenReturn(Optional.of(cities));
        }
        @Test   //no use of this test because it goes straight to Api,
                // so all the variables needs to be changed,
                // not depending on input, but api output.
        public void find_cityId_OK() throws JSONException {

            String expected = "{id:\"1850144\",cityName:\"Tokyo\",country:\"JP\",temp:21.0,sunrise:\"22:36\",sunset:\"12:39\"}";

            ResponseEntity<String> response = restTemplate.getForEntity("/forecast/Tokyo", String.class);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());

            JSONAssert.assertEquals(expected, response.getBody(), false);

            verify(mockRepository, times(1)).findById("1850144");

        }

        @Test
        public void save_newForecast_OK() throws Exception {

            Cities city = new Cities("1850144", "Tokyo", "JP", 296.19, "1589571333","1589621991");

            when(mockRepository.save(any(Cities.class))).thenReturn(city);

            String expected = "{id:\"1850144\",cityName:\"Tokyo\",country:\"JP\",temp:19.8,sunrise:\"22:34\",sunset:\"12:41\"}";

            ResponseEntity<String> response = restTemplate.postForEntity("/forecast/Tokyo", city, String.class);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());


            JSONAssert.assertEquals(expected, response.getBody(), false);

            verify(mockRepository, times(1)).save(any(Cities.class));

        }
        @Test
        public void find_forecastNotFound_404() throws Exception {

            String expected = "{\"cod\":\"404\",\"message\":\"city not found\"}";

            ResponseEntity<String> response = restTemplate.getForEntity("/forecast/TooMom", String.class);

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            JSONAssert.assertEquals(expected, response.getBody(), false);

        }

    }