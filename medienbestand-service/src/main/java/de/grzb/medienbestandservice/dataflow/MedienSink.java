package de.grzb.medienbestandservice.dataflow;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.SubscribableChannel;

public interface MedienSink {

    String MEDIUM_IN = "mediumin";

    @Input(Sink.INPUT)
    SubscribableChannel mediumin();

}
