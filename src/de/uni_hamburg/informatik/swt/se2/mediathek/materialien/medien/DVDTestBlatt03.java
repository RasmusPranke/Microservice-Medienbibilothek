package de.uni_hamburg.informatik.swt.se2.mediathek.materialien.medien;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DVDTestBlatt03
{
    private static final String KOMMENTAR = "Kommentar";
    private static final String TITEL = "Titel";
    private static final String BEZEICHNUNG = "DVD";
    private static final int LAENGE = 100;
    private static final String REGISSEUR = "DVD Regisseur";
    private DVDBlatt03 _dvd1;
    private DVDBlatt03 _dvd2;

    public DVDTestBlatt03()
    {
        _dvd1 = getMedium();
        _dvd2 = getMedium();
    }

    @Test
    public void testGetMedienBezeichnung()
    {
        assertEquals(BEZEICHNUNG, _dvd1.getMedienBezeichnung());
    }

    @Test
    public void testDVD()
    {
        assertEquals(TITEL, _dvd1.getTitel());
        assertEquals(KOMMENTAR, _dvd1.getKommentar());
        assertEquals(LAENGE, _dvd1.getLaufzeit());
        assertEquals(REGISSEUR, _dvd1.getRegisseur());
    }

    @Test
    public final void testSetter()
    {
        _dvd1.setKommentar("Kommentar2");
        assertEquals(_dvd1.getKommentar(), "Kommentar2");
        _dvd1.setTitel("Titel2");
        assertEquals(_dvd1.getTitel(), "Titel2");
        _dvd1.setLaufzeit(90);
        assertEquals(90, _dvd1.getLaufzeit());
        _dvd1.setRegisseur("Regisseur2");
        assertEquals("Regisseur2", _dvd1.getRegisseur());
    }

    @Test
    /*
     * Von ein und der selben DVD kann es mehrere Exemplare geben, die von
     * unterschiedlichen Personen ausgeliehen werden können.
     */
    public void testEquals()
    {
        assertFalse("Mehrere Exemplare der gleichen DVD sollten ungleich",
                _dvd1.equals(_dvd2));
        assertTrue("Dasselbe Exemplar der gleichen DVD sollte gleich",
                _dvd1.equals(_dvd1));
    }

    protected DVDBlatt03 getMedium()
    {
        return new DVDBlatt03(TITEL, KOMMENTAR, REGISSEUR, LAENGE);
    }

}
