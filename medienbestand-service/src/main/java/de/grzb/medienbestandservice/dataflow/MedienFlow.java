package de.grzb.medienbestandservice.dataflow;

import org.springframework.cloud.stream.annotation.StreamListener;

import de.grzb.materialien.medien.CD;

//@EnableBinding(MedienSink.class)
public class MedienFlow {

    @StreamListener(MedienSink.MEDIUM_IN)
    public void fuegeMediumEin(CD medium) {
    }
}
