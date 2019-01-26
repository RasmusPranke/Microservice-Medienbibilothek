package de.grzb.medienbestandservice.restapi.mediacontroller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.grzb.materialien.medien.KonsolenVideospiel;
import de.grzb.medienbestandservice.restapi.AbstractMediumController;
import de.grzb.medienbestandservice.restapi.MedienController;

@RestController
public class KonsolenVideospielController extends AbstractMediumController<KonsolenVideospiel> {

    private static final String MEDIENBEZEICHNUNG = "KonsolenVideospiel";

    public static interface Repository extends CrudRepository<KonsolenVideospiel, Long> {
    }

    protected KonsolenVideospielController(Repository repo, MedienController controller) {
        super(repo, controller);
    }

    @Override
    @RequestMapping(path = "/fuegeMediumEin", method = RequestMethod.POST, headers = {
            "medienBezeichnung=" + MEDIENBEZEICHNUNG })
    public ResponseEntity<KonsolenVideospiel> medium_set(@RequestBody @Valid KonsolenVideospiel cd) {
        return fuegeMediumEin(cd);
    }

    @Override
    @RequestMapping(path = "/getMedien", method = RequestMethod.GET, headers = {
            "medienBezeichnung=" + MEDIENBEZEICHNUNG })
    public List<KonsolenVideospiel> medium_get() {
        return getMedien();
    }

    @Override
    @RequestMapping(path = "/enthaeltMedium", method = RequestMethod.GET, headers = {
            "medienBezeichnung=" + MEDIENBEZEICHNUNG })
    public ResponseEntity<KonsolenVideospiel> medium_has(@RequestBody @Valid KonsolenVideospiel cd) {
        return enthaeltMedium(cd);
    }

    @Override
    @RequestMapping(path = "/entferneMedium", method = RequestMethod.POST, headers = {
            "medienBezeichnung=" + MEDIENBEZEICHNUNG })
    public ResponseEntity<KonsolenVideospiel> medium_remove(@RequestBody @Valid KonsolenVideospiel cd) {
        return entferneMedium(cd);
    }

}
