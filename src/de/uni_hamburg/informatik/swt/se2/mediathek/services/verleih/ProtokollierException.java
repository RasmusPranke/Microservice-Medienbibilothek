package de.uni_hamburg.informatik.swt.se2.mediathek.services.verleih;

/**
 * TODO vor Blatt 5: löschen, für das Blatt 5 drinnen lassen
 * 
 * Eine ProtokollierException signalisiert, dass das Protokollieren eines
 * Verleihvorgangs fehlgeschlagen ist.
 * 
 * @author SE2-Team
 * @version SoSe 2014
 */
public class ProtokollierException extends Exception
{

    private static final long serialVersionUID = 1L;

    /**
     * Initilaisert eine neue Exception mit der übergebenen Fehlermeldung.
     * 
     * @param message Eine Fehlerbeschreibung.
     */
    public ProtokollierException(String message)
    {
        super(message);
    }
}
