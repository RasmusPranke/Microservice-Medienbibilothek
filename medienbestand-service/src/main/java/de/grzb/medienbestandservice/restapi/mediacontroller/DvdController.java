package de.grzb.medienbestandservice.restapi.mediacontroller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.grzb.materialien.medien.DVD;
import de.grzb.medienbestandservice.restapi.AbstractMediumController;
import de.grzb.medienbestandservice.restapi.MedienController;

@RestController
public class DvdController extends AbstractMediumController<DVD> {

    private static final String MEDIENBEZEICHNUNG = "DVD";

    public static interface Repository extends CrudRepository<DVD, Long> {
    }

    protected DvdController(Repository repo, MedienController controller) {
        super(repo, controller);
    }

    @Override
    @RequestMapping(path = "/fuegeMediumEin", method = RequestMethod.POST, headers = {
            "medienBezeichnung=" + MEDIENBEZEICHNUNG })
    public ResponseEntity<DVD> medium_set(@RequestBody @Valid DVD cd) {
        return fuegeMediumEin(cd);
    }

    @Override
    @RequestMapping(path = "/getMedien", method = RequestMethod.GET, headers = {
            "medienBezeichnung=" + MEDIENBEZEICHNUNG })
    public List<DVD> medium_get() {
        return getMedien();
    }

    @Override
    @RequestMapping(path = "/enthaeltMedium", method = RequestMethod.GET, headers = {
            "medienBezeichnung=" + MEDIENBEZEICHNUNG })
    public ResponseEntity<DVD> medium_has(@RequestBody @Valid DVD cd) {
        return enthaeltMedium(cd);
    }

    @Override
    @RequestMapping(path = "/entferneMedium", method = RequestMethod.POST, headers = {
            "medienBezeichnung=" + MEDIENBEZEICHNUNG })
    public ResponseEntity<DVD> medium_remove(@RequestBody @Valid DVD cd) {
        return entferneMedium(cd);
    }

}
