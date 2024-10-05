package ar.edu.utn.frc.tup.lciii.controllers;
import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.service.CountryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CountryController {

    private final CountryServiceImpl countryServiceImpl;


    @GetMapping("/countries")
    public ResponseEntity<List<CountryDTO>> getCountries(@RequestParam(value = "name", required = false)  String name,
                                                         @RequestParam(value = "code", required = false)  String code) {
        List<CountryDTO> countries = countryServiceImpl.getCountriesDTO(name, code);
        return ResponseEntity.ok(countries);

    }


    @GetMapping("/countries/{continent}/continent")
    public ResponseEntity<List<CountryDTO>> getCountriesByContinent(
            @PathVariable (value = "continent", required = true)  String continent)
            {
        List<CountryDTO> countries = countryServiceImpl.getCountriesByContinent(continent);
        return ResponseEntity.ok(countries);

    }

    @GetMapping("/countries/{language}/language")
    public ResponseEntity<List<CountryDTO>> getCountriesByLanguage(
            @PathVariable (value = "language", required = true)  String language)
    {
        List<CountryDTO> countries = countryServiceImpl.getCountriesByLanguage(language);
        return ResponseEntity.ok(countries);

    }

    @GetMapping("/countries/most-borders")
    public ResponseEntity<CountryDTO> getMostBorders()
    {
        CountryDTO country = countryServiceImpl.getCountriesMostBorders();
        return ResponseEntity.ok(country);

    }



}