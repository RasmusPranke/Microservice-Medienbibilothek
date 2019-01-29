package de.grzb.verleihservice.materialien;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
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
 * @author SE2-Team, Nikolai Elich
 * @version WiSe 2018/19
 */
@Entity
public class Vormerkkarte
{

    @NotNull
    private List<Long> vormerkerIds;

    @Id
    @NotNull
    private Long mediumId;

    protected Vormerkkarte() { }

    /**
     * Initialisiert eine Vormerkkarte für das Medium mit einem Vormerker
     *
     * @param mediumId Die ID des Mediums für welches Kunden als Vormerker gespeichert
     *            werden sollen
     * @param vormerkerId Die ID des Kunden, welcher als erster Vormerker auf der Karte
     *            auftauchen soll
     *
     * @require mediumId!=null
     * @require vormerkerId!=null
     */
    public Vormerkkarte(Long mediumId, Long vormerkerId)
    {
        assert vormerkerId != null : "Vorbedingung verletzt: vormeker!=null";
        assert (mediumId != null) : "Vorbedingung verletzt: mediumId!=null";

        this.mediumId = mediumId;
        this.vormerkerIds = new ArrayList<>();
        this.vormerkerIds.add(vormerkerId);
    }

    /**
     * Die ID eines übergebenen Kunden wird in die Liste der Vormerker eingetragen.
     *
     * @require akzeptiert(kundenId)
     * @ensure istVorgemerktVon(kundenId)
     */
    public void merkeVor(Long kundenId)
    {
        assert akzeptiert(kundenId) : "Vorbedingung verletzt: akzeptiert(kundenId)";
        vormerkerIds.add(kundenId);
    }

    /**
     * Entfernt einen Kunden aus der Liste der Vormerker. Nachfolgende Vormerker
     * rücken auf.
     *
     * @require istVorgemerktVon(kundenId)
     * @ensure !istVorgemerktVon(kundenId)
     */
    public void entferneVormerker(Long kundenId)
    {
        assert istVorgemerktVon(kundenId) : "Vorbedingung verletzt: istVorgemerktVon(kundenId)";
        vormerkerIds.remove(kundenId);
    }

    /**
     * Liefert true, wenn das Medium durch einen Kunden vorgemerkt werden kann.
     * Dies ist der Fall, wenn das Medium von nicht mehr als 2 unterschiedlichen
     * Kunden vorgemerkt wurde und der Kunde noch nicht vorgemerkt hat.
     *
     * @param kundenId Die ID eines Kunden für den überprüft werden soll, ob er dieses Medium
     *            vormerken kann.
     * @require kundenId!=null
     */
    public boolean akzeptiert(Long kundenId)
    {
        assert (kundenId != null) : "Vorbedingung verletzt: kundenId!=null";
        return hatPlatzFuerWeiterenVormerker() && !istVorgemerktVon(kundenId);
    }

    /**
     * Liefert true, wenn nicht mehr als 2 unterschiedliche Kunden das Medium
     * vorgemerkt haben, ansonsten false.
     */
    private boolean hatPlatzFuerWeiterenVormerker()
    {
        return vormerkerIds.size() < 3;
    }

    /**
     * Liefert true, wenn das Medium durch einen Kunden vorgemerkt ist,
     * ansonsten false.
     *
     * @require kundenId!=null
     */
    public boolean istVorgemerktVon(Long kundenId)
    {
        assert (kundenId != null) : "Vorbedingung verletzt: kundenId!=null";
        return vormerkerIds.contains(kundenId);
    }

    /**
     * Gibt die IDs alle Vormerker in einer nicht veränderbaren Liste zurück.
     *
     * @return die IDs alle Vormerker in einer nicht veränderbaren Liste.
     *
     */
    public List<Long> getVormerkerIds() {
        return vormerkerIds;
    }

    public void setVormerkerIds(List<Long> vormerkerIds) {
        this.vormerkerIds = vormerkerIds;
    }

    /**
     * Gibt die ID des Mediums dieser Vormerkkarte
     *
     * @return ID des Mediums
     */
    public Long getMediumId()
    {
        return mediumId;
    }

    public void setMediumId(Long mediumId) {
        this.mediumId = mediumId;
    }

    /**
     * Prüft, ob ein übergebener Kunde der erste Vormerker ist.
     *
     * @param kundenId Die ID eines Kunden
     *
     * @return true, wenn ein Kunde der erste Vormerker ist, ansonsten false.
     *
     * @require kundenId != null
     */
    public boolean istErsterVormerker(Long kundenId)
    {
        assert kundenId != null : "Vorbedingung verletzt: kundenId != null";
        boolean result = false;
        if (vormerkerIds.size() > 0)
        {
            result = kundenId.equals(vormerkerIds.get(0));
        }
        return result;
    }

}
