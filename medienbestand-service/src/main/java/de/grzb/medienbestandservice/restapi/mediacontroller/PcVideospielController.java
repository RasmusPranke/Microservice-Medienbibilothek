package de.grzb.medienbestandservice.restapi.mediacontroller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.grzb.materialien.medien.PCVideospiel;
import de.grzb.medienbestandservice.restapi.AbstractMediumController;
import de.grzb.medienbestandservice.restapi.MedienController;

@RestController
public class PcVideospielController extends AbstractMediumController<PCVideospiel> {

    private static final String MEDIENBEZEICHNUNG = "PCVideospiel";

    public static interface Repository extends CrudRepository<PCVideospiel, Long> {
    }

    protected PcVideospielController(Repository repo, MedienController controller) {
        super(repo, controller);
    }

    @Override
    @RequestMapping(path = "/fuegeMediumEin", method = RequestMethod.POST, headers = {
            "medienBezeichnung=" + MEDIENBEZEICHNUNG })
    public ResponseEntity<PCVideospiel> medium_set(@RequestBody @Valid PCVideospiel cd) {
        return fuegeMediumEin(cd);
    }

    @Override
    @RequestMapping(path = "/getMedien", method = RequestMethod.GET, headers = {
            "medienBezeichnung=" + MEDIENBEZEICHNUNG })
    public List<PCVideospiel> medium_get() {
        return getMedien();
    }

    @Override
    @RequestMapping(path = "/enthaeltMedium", method = RequestMethod.GET, headers = {
            "medienBezeichnung=" + MEDIENBEZEICHNUNG })
    public ResponseEntity<Boolean> medium_has(@RequestParam(value = "id") Long id) {
        return enthaeltMedium(id);
    }

    @Override
    @RequestMapping(path = "/entferneMedium", method = RequestMethod.POST, headers = {
            "medienBezeichnung=" + MEDIENBEZEICHNUNG })
    public ResponseEntity<PCVideospiel> medium_remove(@RequestBody @Valid PCVideospiel cd) {
        return entferneMedium(cd);
    }

}
