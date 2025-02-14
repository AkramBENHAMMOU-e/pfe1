package ma.pfe.backend;

import ma.pfe.backend.entites.Filiere;
import ma.pfe.backend.entites.Salle;
import ma.pfe.backend.entites.Seance;
import ma.pfe.backend.repository.FiliereRepos;
import ma.pfe.backend.repository.SalleRepos;
import ma.pfe.backend.repository.SeanceRepos;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
@EnableTransactionManagement
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }
    @Bean
    CommandLineRunner start(
                            ){
        return args -> {

        };
    }

}
