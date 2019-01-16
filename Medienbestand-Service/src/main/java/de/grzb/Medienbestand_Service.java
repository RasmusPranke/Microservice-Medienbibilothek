package de.grzb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Medienbestand_Service {
    private static final Logger log = LoggerFactory.getLogger(Medienbestand_Service.class);

    public static void main(String args[]) {
        SpringApplication.run(Medienbestand_Service.class, args);
    }

    /*
     * Can be used to initialize stuff on startup
     * 
     * @Bean public CommandLineRunner demo(CDRepo repository) { return (args) ->
     * { // save a couple of customers log.info("Creating Test Data.");
     * repository.save(new CD("You Overdid it", "Chiptune, Funk", "Ben Briggs",
     * 237)); repository.save(new CD("Game Boy Color Envy", "Chiptune",
     * "Ben Briggs", 260)); repository.save(new CD("Game Boy Color Envy",
     * "Chiptune", "Someone else", 260));
     * 
     * log.info("Customers found with findAll():"); // fetch all customers
     * log.info("-------------------------------"); for(CD cd :
     * repository.findAll()) { log.info(cd.toString()); }
     * 
     * // fetch an individual customer by ID
     * log.info("Customer found with findById(1L):"); repository.findById(new
     * CD.CDId("Game Boy Color Envy", "Someone else")).ifPresent(cd -> {
     * log.info("--------------------------------"); log.info(cd.toString());
     * });
     * 
     * log.info("Customer found with findBy_interpret(Ben Briggs):");
     * repository.getBy_interpret("Ben Briggs").forEach(cd -> {
     * log.info("--------------------------------"); log.info(cd.toString());
     * }); }; }
     */
}
