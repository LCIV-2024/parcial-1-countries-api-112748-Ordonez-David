package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.model.Country;

import java.util.List;
import java.util.Map;

public interface CountryService {

    List<Country> getAllCountries();

    Country mapToCountry(Map<String, Object> countryData);

    CountryDTO mapToDTO(Country country);

    List<CountryDTO> getCountriesDTO(String name, String code);

    String mapCode (Country country);

    List<CountryDTO> getCountriesByContinent(String continent);

    List<CountryDTO> getCountriesByLanguage(String language);

    CountryDTO getCountriesMostBorders();
}
