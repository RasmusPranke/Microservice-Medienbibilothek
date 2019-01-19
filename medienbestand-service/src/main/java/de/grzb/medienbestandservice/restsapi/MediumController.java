package de.grzb.medienbestandservice.restsapi;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.grzb.materialien.medien.CD;
import de.grzb.medienbestandservice.CDRepo;

@RestController
public class MediumController {

    private final CDRepo repository;
    private final Log log;

    public MediumController(CDRepo repository) {
        this.repository = repository;
        log = LogFactory.getLog(getClass());
        log.info("Created Medium Controller!");
    }

    @RequestMapping(name = "/medium/get", method = RequestMethod.GET)
    public List<CD> medium_get(@RequestHeader(value = "interpret", defaultValue = "Ben Briggs") String interpret) {
        return repository.getBy_interpret(interpret);
    }

    @RequestMapping(name = "/medium/set", method = RequestMethod.POST)
    public ResponseEntity<CD> medium_set(@RequestHeader(value = "titel") String titel,
                                         @RequestHeader(value = "interpret") String interpret,
                                         @RequestHeader(value = "kommentar", defaultValue = "") String kommentar,
                                         @RequestHeader(value = "spiellaenge") int spieallaenge,
                                         @RequestHeader(value = "override", required = false) boolean override) {

        if(!override && repository.findById(new CD.CDId(titel, interpret)).isPresent()) {
            return new ResponseEntity<CD>(HttpStatus.CONFLICT);
        }

        CD result = repository.save(new CD(titel, kommentar, interpret, spieallaenge));
        return new ResponseEntity<CD>(result, HttpStatus.OK);
    }
}
