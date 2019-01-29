package de.grzb.verleihservice.restapi;

import de.grzb.verleihservice.*;
import de.grzb.verleihservice.restapi.requestobjects.*;
import java.util.*;
import javax.validation.constraints.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class VerleihController {

    VerleihService verleihService;

    protected VerleihController(VerleihService verleihService) {
        this.verleihService = verleihService;
    }

    ;

    @RequestMapping(path = "/verleiheAn", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity verleiheAn(@RequestBody @NotNull VerleihRequest request) {
        Long kundenId = request.getKundenId();
        List<Long> medienIds = request.getMedienIds();
        Calendar datum = request.getDatum();

        verleihService.verleiheAn(kundenId, medienIds, datum);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(path = "/istVerleihenMoeglich", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity istVerleihenMoeglich(@RequestParam @NotNull Long kundenId,
                                               @RequestParam @NotNull List<Long> medienIds) {
        return ResponseEntity.ok(verleihService.istVerleihenMoeglich(kundenId, medienIds));
    }

    @RequestMapping(path = "/getEntleiherFuer", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getEntleiherFuer(@RequestParam @NotNull Long mediumId) {
        return ResponseEntity.ok(verleihService.getEntleiherFuer(mediumId));
    }

    @RequestMapping(path = "/getAusgelieheneMedienFuer", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getAusgelieheneMedienFuer(@RequestParam @NotNull Long kundenId) {
        return ResponseEntity.ok(verleihService.getAusgelieheneMedienFuer(kundenId));
    }

    @RequestMapping(path = "/getVerleihkarten", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getVerleihkarten() {
        return ResponseEntity.ok(verleihService.getVerleihkarten());
    }

    @RequestMapping(path = "/nimmZurueck", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity nimmZurueck(@RequestBody @NotNull VerleihRequest request) {
        List<Long> medienIds = request.getMedienIds();
        Calendar datum = request.getDatum();

        verleihService.nimmZurueck(medienIds, datum);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(path = "/istVerliehen", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity istVerliehen(@RequestParam @NotNull Long mediumId) {
        return ResponseEntity.ok(verleihService.istVerliehen(mediumId));
    }

    @RequestMapping(path = "/sindAlleNichtVerliehen", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity sindAlleNichtVerliehen(@RequestParam @NotNull List<Long> medienIds) {
        return ResponseEntity.ok(verleihService.sindAlleNichtVerliehen(medienIds));
    }

    @RequestMapping(path = "/sindAlleVerliehen", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity sindAlleVerliehen(@RequestParam @NotNull List<Long> medienIds) {
        return ResponseEntity.ok(verleihService.sindAlleVerliehen(medienIds));
    }

    @RequestMapping(path = "/sindAlleVerliehenAn", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity sindAlleVerliehenAn(@RequestParam @NotNull Long kundenId,
                                              @RequestParam @NotNull List<Long> medienIds) {
        return ResponseEntity.ok(verleihService.sindAlleVerliehenAn(kundenId, medienIds));
    }

    @RequestMapping(path = "/istVerliehenAn", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity istVerliehenAn(@RequestParam @NotNull Long kundenId,
                                         @RequestParam @NotNull Long mediumId) {
        return ResponseEntity.ok(verleihService.istVerliehenAn(kundenId, mediumId));
    }

    @RequestMapping(path = "/getVerleihkartenFuer", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getVerleihkartenFuer(@RequestParam @NotNull Long kundenId) {
        return ResponseEntity.ok(verleihService.getVerleihkartenFuer(kundenId));
    }

    @RequestMapping(path = "/getVerleihkarteFuer", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getVerleihkarteFuer(@RequestParam @NotNull Long mediumId) {
        return ResponseEntity.ok(verleihService.getVerleihkarteFuer(mediumId));
    }

    @RequestMapping(path = "/merkeVor", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity merkeVor(@RequestBody @NotNull VerleihRequest request) {
        Long kundenId = request.getKundenId();
        List<Long> medienIds = request.getMedienIds();

        verleihService.merkeVor(kundenId, medienIds);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(path = "/entferneVormerker", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity entferneVormerker(@RequestParam @NotNull Long kundenId,
                                            @RequestParam @NotNull List<Long> medienIds) {
        verleihService.entferneVormerker(kundenId, medienIds);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(path = "/koennenAlleVorgemerktWerdenVon", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity koennenAlleVorgemerktWerdenVon(@RequestParam @NotNull Long kundenId,
                                                         @RequestParam @NotNull List<Long> medienIds) {
        return ResponseEntity.ok(
                verleihService.koennenAlleVorgemerktWerdenVon(kundenId, medienIds));
    }

    @RequestMapping(path = "/kannVorgemerktWerdenVon", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity kannVorgemerktWerdenVon(@RequestParam @NotNull Long kundenId,
                                                  @RequestParam @NotNull Long mediumId) {
        return ResponseEntity.ok(verleihService.kannVorgemerktWerdenVon(kundenId, mediumId));
    }

    @RequestMapping(path = "/sindAlleVorgemerktVon", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity sindAlleVorgemerktVon(@RequestParam @NotNull Long kundenId,
                                                @RequestParam @NotNull List<Long> medienIds) {
        return ResponseEntity.ok(verleihService.sindAlleVorgemerktVon(kundenId, medienIds));
    }

    @RequestMapping(path = "/istVorgemerktVon", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity istVorgemerktVon(@RequestParam @NotNull Long kundenId,
                                           @RequestParam @NotNull Long mediumId) {
        return ResponseEntity.ok(verleihService.istVorgemerktVon(kundenId, mediumId));
    }

    @RequestMapping(path = "/istVorgemerkt", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity istVorgemerkt(@RequestParam @NotNull Long mediumId) {
        return ResponseEntity.ok(verleihService.istVorgemerkt(mediumId));
    }

    @RequestMapping(path = "/getVormerkkarteFuer", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getVormerkkarteFuer(@RequestParam @NotNull Long mediumId) {
        return ResponseEntity.ok(verleihService.getVormerkkarteFuer(mediumId));
    }
}
