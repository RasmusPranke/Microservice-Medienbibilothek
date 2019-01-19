package de.grzb.materialien;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.grzb.materialien.medien.Medium;

/**
 * TODO für Blatt 6: löschen
 * 
 * Mit Hilfe von Vormerkkarten werden beim Vormerken eines Mediums alle
 * relevanten Daten notiert.
 * 
 * Sie beantwortet die folgenden Fragen:
 * <ul>
 * <li>Welches Medium wurde vorgemerkt</li>
 * <li>Welcher Kunde wurde vorgemerkt</li>
 * </ul>
 * 
 * Wenn eine Vormerkkarte keine Kunden mehr enthält kann sie entsorgt werden. Um
 * das entsorgen kümmert sich der VerleihService
 * 
 * @author SE2-Team
 * @version SoSe 2014
 */
public class Vormerkkarte
{

    private List<Kunde> _vormerker;
    private Medium _medium;

    /**
     * Initialisiert eine Vormerkkarte für das Medium mit einem Vormerker
     * 
     * @param medium Das Medium für welches Kunden als Vormerker gespeichert
     *            werden sollen
     * @param vormerker Ein Kunde, welcher als erster Vormerker auf der Karte
     *            auftauchen soll
     * 
     * @require medium!=null
     * @require vormerker!=null
     */
    public Vormerkkarte(Medium medium, Kunde vormerker)
    {
        assert vormerker != null : "Vorbedingung verletzt: vormeker!=null";
        assert (medium != null) : "Vorbedingung verletzt: medium!=null";

        _medium = medium;
        _vormerker = new ArrayList<Kunde>();
        _vormerker.add(vormerker);
    }

    /**
     * Ein übergebener Kunde wird in die Liste der Vormerker eingetragen.
     * 
     * @require akzeptiert(kunde)
     * @ensure istVorgemerktVon(kunde)
     */
    public void merkeVor(Kunde kunde)
    {
        assert akzeptiert(kunde) : "Vorbedingung verletzt: akzeptiert(kunde)";
        _vormerker.add(kunde);
    }

    /**
     * Entfernt einen Kunden aus der Liste der Vormerker. Nachfolgende Vormerker
     * rücken auf.
     * 
     * @require istVorgemerktVon(kunde)
     * @ensure !istVorgemerktVon(kunde)
     */
    public void entferneVormerker(Kunde kunde)
    {
        assert istVorgemerktVon(kunde) : "Vorbedingung verletzt: istVorgemerktVon(kunde)";
        _vormerker.remove(kunde);
    }

    /**
     * Liefert true, wenn das Medium durch einen Kunden vorgemerkt werden kann.
     * Dies ist der Fall, wenn das Medium von nicht mehr als 2 unterschiedlichen
     * Kunden vorgemerkt wurde und der Kunde noch nicht vorgemerkt hat.
     * 
     * @param kunde Ein Kunde für den überprüft werden soll, ob er dieses Medium
     *            vormerken kann.
     * @require kunde!=null
     */
    public boolean akzeptiert(Kunde kunde)
    {
        assert (kunde != null) : "Vorbedingung verletzt: kunde!=null";
        return hatPlatzFuerWeiterenVormerker() && !istVorgemerktVon(kunde);
    }

    /**
     * Liefert true, wenn nicht mehr als 2 unterschiedliche Kunden das Medium
     * vorgemerkt haben, ansonsten false.
     */
    private boolean hatPlatzFuerWeiterenVormerker()
    {
        return _vormerker.size() < 3;
    }

    /**
     * Liefert true, wenn das Medium durch einen Kunden vorgemerkt ist,
     * ansonsten false.
     * 
     * @require kunde!=null
     */
    public boolean istVorgemerktVon(Kunde kunde)
    {
        assert (kunde != null) : "Vorbedingung verletzt: kunde!=null";
        return _vormerker.contains(kunde);
    }

    /**
     * Gibt alle Vormerker in einer nicht veränderbaren Liste zurück.
     * 
     * @return alle Vormerker in einer nicht veränderbaren Liste zurück.
     * 
     */
    public List<Kunde> getAlleVormerker()
    {
        return Collections.unmodifiableList(_vormerker);
    }

    /**
     * Gibt das Medium dieser Vormerkkarte
     * 
     * @return Medium
     */
    public Medium getMedium()
    {
        return _medium;
    }

    /**
     * Gibt eine String-Darstellung der Vormerkkarte (enhält Zeileumbrüche)
     * zurück.
     * 
     * @return Eine formatierte Stringrepäsentation der Vormerkkarte. Enthält
     *         Zeilenumbrüche.
     * 
     * @ensure result != null
     */
    public String getFormatiertenString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append(_medium.getFormatiertenString() + " vorgemerkt von:\n");
        for (Kunde k : _vormerker)
        {
            builder.append(k.getFormatiertenString());
        }
        return builder.toString();

    }

    /**
     * Prüft, ob ein übergebener Kunde der erste Vormerker ist.
     * 
     * @param kunde Ein Kunde
     * 
     * @return true, wenn ein Kunde der erste Vormerker ist, ansonsten false.
     * 
     * @require kunde != null
     */
    public boolean istErsterVormerker(Kunde kunde)
    {
        assert kunde != null : "Vorbedingung verletzt: kunde != null";
        boolean result = false;
        if (_vormerker.size() > 0)
        {
            result = kunde.equals(_vormerker.get(0));
        }
        return result;
    }

}
