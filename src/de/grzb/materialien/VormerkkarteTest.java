package de.grzb.materialien;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import de.grzb.fachwerte.Kundennummer;
import de.grzb.materialien.medien.CD;
import de.grzb.materialien.medien.Medium;

public class VormerkkarteTest
{

    @Test
    public void testeInitialisierung()
    {
        Medium medium = new CD("titel", "kommentar", "interpret", 10);

        Kunde vormerker = new Kunde(new Kundennummer(123456), "vorname",
                "nachname");
        List<Kunde> vormerkerListe = new ArrayList<>();
        vormerkerListe.add(vormerker);

        Vormerkkarte karte = new Vormerkkarte(medium, vormerker);

        assertEquals(medium, karte.getMedium());
        assertEquals(vormerkerListe, karte.getAlleVormerker());
        assertTrue(karte.istErsterVormerker(vormerker));
        assertTrue(karte.istVorgemerktVon(vormerker));
    }

    @Test
    public void merkeVorUndEntferneVormerker()
    {
        Medium medium = new CD("titel", "kommentar", "interpret", 10);

        Kunde vormerker = new Kunde(new Kundennummer(123456), "vorname",
                "nachname");
        Vormerkkarte karte = new Vormerkkarte(medium, vormerker);

        assertTrue(karte.istVorgemerktVon(vormerker));
        assertFalse(karte.akzeptiert(vormerker));

        Kunde kunde2 = new Kunde(new Kundennummer(123457), "vorname",
                "nachname");
        Kunde kunde3 = new Kunde(new Kundennummer(123458), "vorname",
                "nachname");

        karte.merkeVor(kunde2);
        assertFalse(karte.akzeptiert(kunde2));

        karte.merkeVor(kunde3);
        assertFalse(karte.akzeptiert(kunde3));

        karte.entferneVormerker(kunde2);

        assertTrue(karte.akzeptiert(kunde2));

        karte.entferneVormerker(vormerker);

        assertTrue(karte.akzeptiert(vormerker));

        karte.entferneVormerker(kunde3);

        assertTrue(karte.akzeptiert(kunde3));

        assertEquals(0, karte.getAlleVormerker().size());

    }

    @Test
    public void istVormerkenMoeglich()
    {

        Kunde vormerker1 = new Kunde(new Kundennummer(123456), "klaus",
                "kleber");
        Kunde vormerker2 = new Kunde(new Kundennummer(123457), "Karl", "kleber");
        Kunde vormerker3 = new Kunde(new Kundennummer(123458), "gunter",
                "kleber");

        Medium medium = new CD("titel", "kommentar", "interpret", 10);

        Vormerkkarte karte = new Vormerkkarte(medium, vormerker1);
        karte.merkeVor(vormerker2);
        karte.merkeVor(vormerker3);

        Kunde entleiherKunde = new Kunde(new Kundennummer(123459), "max",
                "mustermann");

        assertFalse(karte.akzeptiert(vormerker1));
        assertFalse(karte.akzeptiert(vormerker2));
        assertFalse(karte.akzeptiert(vormerker3));

        assertFalse(karte.akzeptiert(entleiherKunde));

        assertEquals(3, karte.getAlleVormerker().size());

        karte.entferneVormerker(vormerker1);

        assertTrue(karte.akzeptiert(entleiherKunde));

    }

    @Test
    public void testeEquals()
    {
        Kunde vormerker1 = new Kunde(new Kundennummer(123456), "klaus",
                "kleber");
        Kunde vormerker2 = new Kunde(new Kundennummer(123457), "Karl", "kleber");
        Kunde vormerker3 = new Kunde(new Kundennummer(123458), "gunter",
                "kleber");

        Medium medium1 = new CD("titel1", "kommentar", "interpret", 11);
        Medium medium2 = new CD("titel2", "kommentar", "interpret", 12);
        Medium medium3 = new CD("titel3", "kommentar", "interpret", 13);

        Vormerkkarte karte1 = new Vormerkkarte(medium1, vormerker1);
        Vormerkkarte karte2 = new Vormerkkarte(medium1, vormerker2);
        Vormerkkarte karte3 = new Vormerkkarte(medium2, vormerker1);
        Vormerkkarte karte4 = new Vormerkkarte(medium1, vormerker1);

        Vormerkkarte karte5 = new Vormerkkarte(medium3, vormerker3);

        assertEquals(karte1, karte1); // Equals sich selbst

        assertFalse(karte1.equals(karte4));
        assertFalse(karte1.equals(karte2));
        assertFalse(karte1.equals(karte3));
        assertFalse(karte1.equals(karte5));

        assertFalse(karte2.equals(karte3));

    }
}
