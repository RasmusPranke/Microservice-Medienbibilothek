package de.grzb.medienbestandservice.dataflow;

import org.springframework.cloud.stream.annotation.StreamListener;

import de.grzb.materialien.medien.CD;
import de.grzb.medienbestandservice.CDRepository;

//@EnableBinding(MedienSink.class)
public class MedienFlow {

    private final CDRepository repository;

    public MedienFlow(CDRepository repository) {
        this.repository = repository;
    }

    @StreamListener(MedienSink.MEDIUM_IN)
    public void fuegeMediumEin(CD medium) {
        System.out.println("Fuege ein: " + medium);
        repository.save(medium);
    }
}
