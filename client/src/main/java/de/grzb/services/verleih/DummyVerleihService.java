package de.grzb.services.verleih;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.grzb.fachwerte.Datum;
import de.grzb.fachwerte.Kundennummer;
import de.grzb.materialien.Kunde;
import de.grzb.materialien.Verleihkarte;
import de.grzb.materialien.Vormerkkarte;
import de.grzb.materialien.medien.CD;
import de.grzb.materialien.medien.Medium;
import de.grzb.services.AbstractObservableService;
import de.grzb.services.kundenstamm.KundenstammServiceConnector;
import de.grzb.services.medienbestand.MedienbestandServiceConnector;

/**
 * Diese Klasse implementiert das Interface VerleihServiceConnector. Es handelt sich
 * lediglich um eine Dummy-Implementation, um die GUI zu testen.
 * 
 * @author GUI-Team
 * @version SoSe 2014
 */
public class DummyVerleihService extends AbstractObservableService implements
        VerleihServiceConnector
{
    // Generator für Zufallszahlen und zufällige boolsche Werte
    private static final Random RANDOM = new Random();

    private static final CD MEDIUM = new CD("Titel", "Kommentar", "Interpret",
            70);
    private static final Kundennummer KUNDENNUMMER = new Kundennummer(123456);
    private static final Kunde ENTLEIHER = new Kunde(KUNDENNUMMER, "Vorname",
            "Nachname");
    private static final Verleihkarte VERLEIHKARTE = new Verleihkarte(
            ENTLEIHER, MEDIUM, Datum.heute());

    public DummyVerleihService(KundenstammServiceConnector kundenstamm,
            MedienbestandServiceConnector medienbestand,
            List<Verleihkarte> initialBestand)
    {
    }

    /**
     * Beispiel: Gibt immer dasselbe Kundenobjekt als Entleiher zurück, ohne das
     * als Eingabeparameter übergebene Medium zu beachten.
     */
    @Override
    public Kunde getEntleiherFuer(Medium medium)
    {
        return ENTLEIHER;
    }

    @Override
    public List<Medium> getAusgelieheneMedienFuer(Kunde kunde)
    {
        List<Medium> ergebnisListe = new ArrayList<Medium>();
        ergebnisListe.add(MEDIUM);
        return ergebnisListe;
    }

    @Override
    public Verleihkarte getVerleihkarteFuer(Medium medium)
    {
        return VERLEIHKARTE;
    }

    @Override
    public List<Verleihkarte> getVerleihkarten()
    {
        List<Verleihkarte> ergebnisListe = new ArrayList<Verleihkarte>();
        ergebnisListe.add(VERLEIHKARTE);
        return ergebnisListe;
    }

    @Override
    public void verleiheAn(Kunde kunde, List<Medium> medien, Datum ausleihDatum)
    {
    }

    @Override
    public void nimmZurueck(List<Medium> medien, Datum rueckgabeDatum)
    {
    }

    @Override
    public boolean istVerliehen(Medium medium)
    {
        return RANDOM.nextBoolean();
    }

    @Override
    public boolean sindAlleNichtVerliehen(List<Medium> medien)
    {
        return RANDOM.nextBoolean();
    }

    @Override
    public boolean sindAlleVerliehen(List<Medium> medien)
    {
        return RANDOM.nextBoolean();
    }

    @Override
    public boolean istVerliehenAn(Kunde kunde, Medium medium)
    {
        return RANDOM.nextBoolean();
    }

    @Override
    public boolean sindAlleVerliehenAn(Kunde kunde, List<Medium> medien)
    {
        return RANDOM.nextBoolean();
    }

    @Override
    public boolean kundeImBestand(Kunde kunde)
    {
        return ENTLEIHER.equals(kunde);
    }

    @Override
    public boolean mediumImBestand(Medium medium)
    {
        return MEDIUM.equals(medium);
    }

    @Override
    public boolean medienImBestand(List<Medium> medien)
    {
        boolean result = true;
        for (Medium medium : medien)
        {
            if (!mediumImBestand(medium))
            {
                result = false;
                break;
            }
        }
        return result;
    }

    @Override
    public List<Verleihkarte> getVerleihkartenFuer(Kunde kunde)
    {
        List<Verleihkarte> result = new ArrayList<Verleihkarte>();
        result.add(VERLEIHKARTE);
        return result;
    }

    @Override
    public void merkeVor(Kunde kunde, List<Medium> medien)
            throws ProtokollierException
    {

    }

    @Override
    public void entferneVormerker(Kunde kunde, List<Medium> medien)
            throws ProtokollierException
    {

    }

    @Override
    public boolean istVorgemerktVon(Kunde kunde, Medium medium)
    {
        return RANDOM.nextBoolean();
    }

    @Override
    public boolean kannVorgemerktWerdenVon(Kunde kunde, Medium medium)
    {
        return RANDOM.nextBoolean();
    }

    @Override
    public boolean koennenAlleVorgemerktWerdenVon(Kunde kunde,
            List<Medium> medien)
    {
        return RANDOM.nextBoolean();
    }

    @Override
    public boolean sindAlleVorgemerktVon(Kunde kunde, List<Medium> medien)
    {
        return RANDOM.nextBoolean();
    }

    @Override
    public Vormerkkarte getVormerkkarteFuer(Medium medium)
    {
        return null;
    }

    @Override
    public boolean istVorgemerkt(Medium medium)
    {
        return false;
    }

    @Override
    public boolean istVerleihenMoeglich(Kunde kunde, List<Medium> medien)
    {
        return false;
    }
}
