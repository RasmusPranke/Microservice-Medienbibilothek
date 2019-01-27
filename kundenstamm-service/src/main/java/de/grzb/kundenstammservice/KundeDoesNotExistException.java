package de.grzb.kundenstammservice;

public class KundeDoesNotExistException extends RuntimeException {

    public KundeDoesNotExistException(String string) {
        super(string);
    }

    /**
     * 
     */
    private static final long serialVersionUID = 3163396300863974155L;

}
