package lk.ijse.countriesbackend.controller;

import lk.ijse.countriesbackend.dto.Country;
import lk.ijse.countriesbackend.service.CountryService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/countries")
@CrossOrigin(origins = "*")
public class CountryController {
    private final CountryService service;

    public CountryController(CountryService service) {
        this.service = service;
    }

    @GetMapping
    public List<Country> getCountries(@RequestParam(required = false) String search) {
        return service.searchCountries(search);
    }
}