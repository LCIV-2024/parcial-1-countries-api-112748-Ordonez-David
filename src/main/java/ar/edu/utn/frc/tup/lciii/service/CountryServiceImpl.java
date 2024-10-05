package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

        private final CountryRepository countryRepository;

        private final RestTemplate restTemplate;

        @Override
        public List<Country> getAllCountries() {
                String url = "https://restcountries.com/v3.1/all";
                List<Map<String, Object>> response = restTemplate.getForObject(url, List.class);


                return response.stream().map(this::mapToCountry).collect(Collectors.toList());
        }

        /**
         * Agregar mapeo de campo cca3 (String)
         * Agregar mapeo campos borders ((List<String>))
         */

        @Override
        public String mapCode(Country country) {


                return "";
        }

        @Override
        public List<CountryDTO> getCountriesByContinent(String continent) {

                if(continent == null){
                        return null;
                }

                List<Country> countriesList = getAllCountries();

                List<CountryDTO> countryDTOList = countriesList.stream().map(this::mapToDTO).collect(Collectors.toList());

                List<CountryDTO> countryListForReturn = new ArrayList<>();

                for(Country country : countriesList){

                        if(country.getRegion().contains(continent)){
                                countryListForReturn.add(mapToDTO(country));
                        }

                }

                        return countryListForReturn;

        }

        @Override
        public List<CountryDTO> getCountriesByLanguage(String language) {

                if(language == null){
                        return null;
                }

                List<Country> countriesList = getAllCountries();

                List<CountryDTO> countryDTOList = countriesList.stream().map(this::mapToDTO).collect(Collectors.toList());

                List<CountryDTO> countryListForReturn = new ArrayList<>();

                for(Country country : countriesList){

                        if(country.getLanguages()!= null){
                                if(country.getLanguages().containsValue(language)){
                                        countryListForReturn.add(mapToDTO(country));
                                }

                        }


                }

                return countryListForReturn;


        }

        @Override
        public CountryDTO getCountriesMostBorders() {

                List<Country> countriesList = getAllCountries();

                boolean flagFirst = false;
                Country largerCountry = new Country();

                for(Country country : countriesList){

                      if(!flagFirst){
                              if(country.getBorders() != null){
                                      largerCountry = country;
                                      flagFirst = true;

                              }
                      }

                      else{
                              if(country.getBorders() != null){
                                      if(country.getBorders().size()>largerCountry.getBorders().size()){
                                              largerCountry = country;
                                      }

                              }



                      }

                }
                CountryDTO largerCountryDTO = mapToDTO(largerCountry);


                return largerCountryDTO;
        }


        @Override
        public Country mapToCountry(Map<String, Object> countryData) {
                Map<String, Object> nameData = (Map<String, Object>) countryData.get("name");
                return Country.builder()
                        .name((String) nameData.get("common"))
                        .population(((Number) countryData.get("population")).longValue())
                        .area(((Number) countryData.get("area")).doubleValue())
                        .region((String) countryData.get("region"))
                        .languages((Map<String, String>) countryData.get("languages"))
                        .code((String) countryData.get("cca3"))
                       // .continents((List<String>) countryData.get("continents"))
                        .borders((List<String>) countryData.get("borders"))
                        .build();
        }


        @Override
        public CountryDTO mapToDTO(Country country) {

                String test = mapCode(country);

                return new CountryDTO(country.getCode(), country.getName());
        }

        @Override
        public List<CountryDTO> getCountriesDTO(String name, String code) {

                List<Country> countriesList = getAllCountries();

                if (countriesList.isEmpty()) {
                        return null;
                }
                List<CountryDTO> countryDTOList = countriesList.stream().map(this::mapToDTO).collect(Collectors.toList());


                if(name == null  && code == null ) {

                        return countryDTOList;
                }

                if(name != null) {

                        countryDTOList.removeIf(countryDTO -> !countryDTO.getName().equals(name));
                        return countryDTOList;
                }

                if(code != null) {
                        countryDTOList.removeIf(countryDTO -> !countryDTO.getCode().equals(code));
                        return countryDTOList;
                }

                        return countryDTOList;

        }



}