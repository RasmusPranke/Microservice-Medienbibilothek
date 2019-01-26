package de.grzb.medienbestandservice.restsapi;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import de.grzb.materialien.medien.CD;
import de.grzb.materialien.medien.Medium;
import de.grzb.medienbestandservice.CDRepository;

@RestController
public class MediumController {

    private final CDRepository cdRepository;
    private final Log log;

    public MediumController(CDRepository repository) {
        this.cdRepository = repository;
        log = LogFactory.getLog(getClass());
        log.info("Created Medium Controller!");
    }

    @RequestMapping(name = "getMedien", method = RequestMethod.GET)
    public List<Medium> medium_get() {
        List<Medium> medien = new ArrayList<Medium>();
        cdRepository.findAll().forEach(cd -> {
            medien.add(cd);
        });
        return medien;
    }

    @RequestMapping(name = "fuegeMediumEin/cd", method = RequestMethod.POST)
    public ResponseEntity<CD> medium_set(@RequestHeader(value = "titel") String titel,
                                         @RequestHeader(value = "interpret") String interpret,
                                         @RequestHeader(value = "kommentar", defaultValue = "") String kommentar,
                                         @RequestHeader(value = "spiellaenge") int spieallaenge,
                                         @RequestHeader(value = "override", required = false) boolean override) {

        if(!override && cdRepository.findById(new CD.CDId(titel, interpret)).isPresent()) {
            return new ResponseEntity<CD>(HttpStatus.CONFLICT);
        }

        CD result = cdRepository.save(new CD(titel, kommentar, interpret, spieallaenge));
        return new ResponseEntity<CD>(result, HttpStatus.OK);
    }
}
