package de.grzb;

import java.util.List;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.grzb.materialien.medien.CD;

@RestController
public class MediumController {

    private final CDRepo repository;
    private final MediumSubscribable sub;

    public MediumController(CDRepo repository, MediumSubscribable subscribers) {
        this.repository = repository;
        this.sub = subscribers;
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
        Message<CD> message = MessageBuilder.withPayload(result).build();
        sub.onMediumChanged().send(message);
        return new ResponseEntity<CD>(result, HttpStatus.OK);
    }

    private interface MediumSubscribable {
        @Output
        MessageChannel onMediumChanged();
    }
}
