package lk.ijse.countriesbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CountriesBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(CountriesBackendApplication.class, args);
    }

}
