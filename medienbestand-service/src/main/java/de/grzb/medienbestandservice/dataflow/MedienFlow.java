package de.grzb.medienbestandservice.dataflow;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

import de.grzb.materialien.medien.CD;
import de.grzb.medienbestandservice.CDRepo;

@EnableBinding(Sink.class)
public class MedienFlow {

    private final CDRepo repository;

    public MedienFlow(CDRepo repository) {
        this.repository = repository;
    }

    @StreamListener(MedienSink.MEDIUM_IN)
    public void fuegeMediumEin(CD medium) {
        System.out.println("Fuege ein: " + medium);
        repository.save(medium);
    }
}
