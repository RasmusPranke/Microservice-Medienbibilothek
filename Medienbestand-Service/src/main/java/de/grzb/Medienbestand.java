package de.grzb;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.medien.CD;

@SpringBootApplication
public class Medienbestand implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(Medienbestand.class);

    public static void main(String args[]) {
        SpringApplication.run(Medienbestand.class, args);
    }

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... strings) throws Exception {

        log.info("Creating tables");

        jdbcTemplate.execute("DROP TABLE Medien IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE Medien (" + "titel varchar(255) NOT NULL,"
                + "kommentar varchar(255) NOT NULL," + "SPIELLAENGE int NOT NULL," + "interpret varchar(255) NOT NULL,"
                + "PRIMARY KEY (titel, interpret)" + "); ");

        // Split up the array of whole names into an array of first/last names
        List<Object[]> splitUpNames = Arrays.asList("Disturbia;gut;90;Rihanna", "Hard candy;besser;65;Madonna").stream()
                .map(name -> name.split(";")).collect(Collectors.toList());

        // Use a Java 8 stream to print out each tuple of the list
        splitUpNames.forEach(name -> log
                .info(String.format("Inserting customer record for %s %s %s %s", name[0], name[1], name[2], name[3])));

        // Uses JdbcTemplate's batchUpdate operation to bulk load data
        jdbcTemplate.batchUpdate("INSERT INTO Medien(titel, kommentar, spiellaenge, interpret) VALUES (?,?,?,?)",
                splitUpNames);

        log.info("Querying for customer records where first_name = 'Josh':");
        jdbcTemplate
                .query("SELECT titel, kommentar, interpret, spiellaenge FROM Medien WHERE spiellaenge > ?",
                        new Object[] { "20" },
                        (rs, rowNum) -> new CD(rs.getString("titel"), rs.getString("kommentar"),
                                rs.getString("interpret"), rs.getInt("spiellaenge")))
                .forEach(customer -> log.info(customer.toString()));
    }
}
