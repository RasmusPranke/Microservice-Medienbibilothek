package de.grzb.medienbestandservice.restsapi;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import de.grzb.materialien.medien.Medium;

public abstract class AbstractMediumController<K extends Medium> implements IMediumController<K> {

    private final CrudRepository<K, Long> repo;
    private final Log log;

    protected AbstractMediumController(CrudRepository<K, Long> repo, MedienController controller) {
        this.repo = repo;
        log = LogFactory.getLog(getClass());
        controller.RegisterController(this);
    }

    /**
     * @param cd
     * @return
     */
    public List<K> getMedien() {
        List<K> medien = new ArrayList<K>();
        repo.findAll().forEach(cd -> {
            medien.add(cd);
        });
        return medien;
    }

    public ResponseEntity<K> enthaeltMedium(@RequestBody @Valid K medium) {
        Optional<K> result = repo.findById(medium.getId());
        if(result.isPresent()) {
            if(result.get().equals(medium)) {
                return new ResponseEntity<K>(result.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<K>(HttpStatus.NO_CONTENT);
    }

    /**
     * Fügt ein weiteres, neu angeschafftes Medium in den Bestand ein. Jedes
     * Exemplar im Bestand repräsentiert ein real existierendes Medium. Ist ein
     * Medium mehrfach angeschafft worden, so muss ein weiteres Exemplar mit
     * denselben Eigenschaften eingepflegt werden.
     * 
     * @throws MediumExistsException
     *             Das Medium existiert bereits.
     * @param medium
     * @return
     */
    protected ResponseEntity<K> fuegeMediumEin(K medium) {
        Optional<K> inRepo = repo.findById(medium.getId());

        if(inRepo.isPresent()) {
            throw new MediumExistsException("Das Medium vom Typ " + medium.getMedienBezeichnung() + " mit der Id "
                    + String.valueOf(medium.getId()) + "existiert bereits.");
        }

        K result = repo.save(medium);
        return new ResponseEntity<K>(result, HttpStatus.CREATED);
    }

    /**
     * Entfernt das gegebene Medium aus der Datenbank. Gibt HTTP Status 204 OK
     * sowie das gelöschte Medium zurück, falls das löschen erfolgreich war.
     * Gibt HTTP Status 204 NO_CONTENT zurückt, falls die gegebene
     * 
     * @param medium
     * @return
     */
    protected ResponseEntity<K> entferneMedium(K medium) {
        Optional<K> inRepo = repo.findById(medium.getId());

        if(inRepo.isPresent()) {
            repo.delete(medium);
            return new ResponseEntity<K>(medium, HttpStatus.OK);
        }

        throw new MediumDoesNotExistException("Das Medium " + medium.toFormatiertenString() + " existiert nicht.");
    }
}
