package ar.edu.utn.frc.tup.lciii.controllers;

import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import ar.edu.utn.frc.tup.lciii.service.CountryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CountryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CountryServiceImpl countryService;

    List<CountryDTO> countries = new ArrayList<CountryDTO>();
    @BeforeEach
    void setUp() {
        CountryDTO countryDTO1 = new CountryDTO("SGS", "South Georgia");
        CountryDTO countryDTO2 = new CountryDTO("GRD", "Grenada");

       countries = List.of(countryDTO1, countryDTO2);

    }

    @Test
    void testGetAllCountries() throws Exception {



        when(countryService.getCountriesDTO(null,null)).thenReturn(countries);


        mockMvc.perform(get("/countries"))
                .andExpect(status().isOk());


    }

    @Test
    void testGetCountryName() throws Exception {
        CountryDTO countryDTO1 = new CountryDTO("SGS", "South Georgia");
       List<CountryDTO> countriesFilter = List.of(countryDTO1);

        when(countryService.getCountriesDTO("South Georgia", null)).thenReturn(countriesFilter);
        mockMvc.perform(get("/countries").param("name", String.valueOf("South Georgia")))
                .andExpect(status().isOk());


    }

   @Test
    void testMostBorder() throws Exception {
       CountryDTO countryDTO1 = new CountryDTO("SGS", "South Georgia");

       mockMvc.perform(get("/countries/most-borders"))
               .andExpect(status().isOk());

   }

   @Test
    void testCountriesByContinent() throws Exception {

       mockMvc.perform(get("/countries/Africa/continent"))
               .andExpect(status().isOk());

   }

    @Test
    void testCountriesByLanguage() throws Exception {

        mockMvc.perform(get("/countries/English/language"))
                .andExpect(status().isOk());

    }

}
