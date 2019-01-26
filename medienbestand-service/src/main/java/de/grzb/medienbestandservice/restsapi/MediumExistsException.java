package de.grzb.medienbestandservice.restsapi;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class MediumExistsException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 309841014228389879L;

    public MediumExistsException(String reason) {
        super(reason);
    }

}
