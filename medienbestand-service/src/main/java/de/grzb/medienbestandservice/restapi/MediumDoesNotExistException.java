package de.grzb.medienbestandservice.restapi;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class MediumDoesNotExistException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 309841014228389879L;

    public MediumDoesNotExistException(String reason) {
        super(reason);
    }

}
