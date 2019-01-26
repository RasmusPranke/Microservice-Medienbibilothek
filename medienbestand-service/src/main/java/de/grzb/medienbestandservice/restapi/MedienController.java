package de.grzb.medienbestandservice.restapi;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.grzb.materialien.medien.Medium;

@RestController
public class MedienController {

    private final List<AbstractMediumController<? extends Medium>> mediumControllers = new ArrayList<AbstractMediumController<? extends Medium>>();

    @RequestMapping(value = "getAllMedien", method = RequestMethod.GET)
    public List<Medium> medium_get() {
        List<Medium> medien = new ArrayList<Medium>();
        mediumControllers.forEach(controller -> {
            medien.addAll(controller.getMedien());
        });
        return medien;
    }

    public <K extends Medium> void RegisterController(AbstractMediumController<K> controller) {
        mediumControllers.add(controller);
    }

}
