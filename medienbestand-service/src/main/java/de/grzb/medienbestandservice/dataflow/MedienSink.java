package de.grzb.medienbestandservice.dataflow;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface MedienSink {

    String MEDIUM_IN = "mediumin";

    @Input(MedienSink.MEDIUM_IN)
    SubscribableChannel mediumin();

}
