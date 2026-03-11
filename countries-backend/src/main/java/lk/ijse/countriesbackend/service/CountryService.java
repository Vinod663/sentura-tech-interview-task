package lk.ijse.countriesbackend.service;

import lk.ijse.countriesbackend.dto.Country;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import jakarta.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CountryService {
    private List<Country> cachedCountries = new ArrayList<>();
    private final RestTemplate restTemplate = new RestTemplate();

    @PostConstruct
    @Scheduled(fixedRate = 600000) // Refresh every 10 minutes
    public void fetchCountries() {
        try {
            String url = "https://restcountries.com/v3.1/all?fields=name,capital,region,population,flags";
            List<Map<String, Object>> response = restTemplate.getForObject(url, List.class);

            if (response != null) {
                List<Country> newCache = new ArrayList<>();
                for (Map<String, Object> data : response) {
                    Country country = new Country();

                    Map<String, Object> nameMap = (Map<String, Object>) data.get("name");
                    country.setName(nameMap != null ? (String) nameMap.get("common") : "N/A");

                    List<String> capitals = (List<String>) data.get("capital");
                    country.setCapital(capitals != null && !capitals.isEmpty() ? capitals.get(0) : "N/A");

                    country.setRegion((String) data.get("region"));

                    Number pop = (Number) data.get("population");
                    country.setPopulation(pop != null ? pop.longValue() : 0);

                    Map<String, Object> flags = (Map<String, Object>) data.get("flags");
                    country.setFlag(flags != null ? (String) flags.get("png") : "");

                    newCache.add(country);
                }
                cachedCountries = newCache;
                // Success message added so you know it worked in the console!
                System.out.println("API call successful! Loaded " + cachedCountries.size() + " countries.");
            }
        } catch (Exception e) {
            System.err.println("API call failed: " + e.getMessage());
        }
    }

    public List<Country> searchCountries(String query) {
        if (query == null || query.isBlank()) return cachedCountries;
        return cachedCountries.stream()
                .filter(c -> c.getName().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }
}