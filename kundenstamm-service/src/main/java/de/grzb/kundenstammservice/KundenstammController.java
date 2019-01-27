package de.grzb.kundenstammservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.grzb.fachwerte.Kundennummer;
import de.grzb.materialien.Kunde;

@RestController
public class KundenstammController {

    private final KundenRepository repo;

    public KundenstammController(KundenRepository repo) {
        this.repo = repo;
    }

    @RequestMapping(path = "/fuegeKundenEin", method = RequestMethod.POST)
    public ResponseEntity<Kunde> fuegeKundenEin(@RequestBody Kunde kunde) {
        kunde = repo.save(kunde);
        return new ResponseEntity<>(kunde, HttpStatus.OK);
    }

    @RequestMapping(path = "/getKunden", method = RequestMethod.GET)
    public List<Kunde> medium_get() {
        List<Kunde> kunden = new ArrayList<Kunde>();
        repo.findAll().forEach(kunde -> {
            kunden.add(kunde);
        });

        return kunden;
    }

    @RequestMapping(path = "/enthaeltKunden", method = RequestMethod.GET)
    public ResponseEntity<Boolean> medium_has(@RequestParam(value = "id") Kundennummer id) {
        return new ResponseEntity<>(repo.existsById(id), HttpStatus.OK);
    }

    @RequestMapping(path = "/entferneKunden", method = RequestMethod.POST)
    public ResponseEntity<Kunde> medium_remove(@RequestBody Kunde kunde) {
        Optional<Kunde> inRepo = repo.findById(kunde.getKundennummer());

        if(inRepo.isPresent()) {
            repo.delete(inRepo.get());
            return new ResponseEntity<Kunde>(kunde, HttpStatus.OK);
        }

        throw new KundeDoesNotExistException("Der Kunde " + kunde.toFormatiertenString() + " existiert nicht.");
    }

}
