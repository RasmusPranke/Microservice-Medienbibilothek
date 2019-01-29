package de.grzb.verleihservice.materialien;

import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * Mit Hilfe von Verleihkarten werden beim Verleih eines Mediums alle relevanten Daten notiert.
 * <p>
 * Sie beantwortet die folgenden Fragen: Welches Medium wurde ausgeliehen? Wer hat das Medium
 * ausgeliehen? Wann wurde das Medium ausgeliehen?
 * <p>
 * Wenn Medien zurück gegeben werden, kann die zugehörige Verleihkarte entsorgt werden. Um die
 * Verwaltung der Karten kümmert sich der VerleihService
 *
 * @author SE2-Team, Nikolai Elich
 * @version WiSe 2018/19
 */
@Entity
public class Verleihkarte {

    // Eigenschaften einer Verleihkarte
    @NotNull
    private int[] ausleihdatum; // formatiert als [tag, monat, jahr]

    @NotNull
    private Long entleiherId;

    @Id
    @NotNull
    private Long mediumId;

    protected Verleihkarte() { }

    /**
     * Initialisert eine neue Verleihkarte mit den gegebenen Daten.
     *
     * @param entleiherId Die ID eines Kunden, der das Medium ausgeliehen hat.
     * @param mediumId Die ID eines verliehenen Mediums.
     * @param ausleihdatum Das int[], an dem der Kunde das Medium ausgeliehen hat.
     *
     * @require entleiherId != null
     * @require mediumId != null
     * @require ausleihdatum != null
     * @ensure #getEntleiherId() == entleiherId
     * @ensure #getMediumId() == mediumId
     * @ensure #getAusleihdatum() == ausleihdatum
     */
    public Verleihkarte(Long entleiherId, Long mediumId, int[] ausleihdatum) {
        assert entleiherId != null : "Vorbedingung verletzt: entleiherId != null";
        assert mediumId != null : "Vorbedingung verletzt: mediumId != null";
        assert ausleihdatum != null : "Vorbedingung verletzt: ausleihdatum != null";

        this.entleiherId = entleiherId;
        this.mediumId = mediumId;
        this.ausleihdatum = ausleihdatum;
    }

    /**
     * Gibt das Ausleihdatum zurück.
     *
     * @return Das Ausleihdatum.
     *
     * @ensure result != null
     */
    public int[] getAusleihdatum() {
        return ausleihdatum;
    }

    public void setAusleihdatum(int[] ausleihdatum) {
        this.ausleihdatum = ausleihdatum;
    }

    /**
     * Gibt die ID des Entleihers zurück.
     *
     * @return die ID des Kunden, der das Medium entliehen hat.
     *
     * @ensure result != null
     */
    public Long getEntleiherId() {
        return entleiherId;
    }

    public void setEntleiherId(Long entleiherId) {
        this.entleiherId = entleiherId;
    }

    /**
     * Gibt die ID des Mediums, dessen Ausleihe auf der Karte vermerkt ist, zurück.
     *
     * @return Die ID des Mediums, dessen Ausleihe auf dieser Karte vermerkt ist.
     *
     * @ensure result != null
     */
    public Long getMediumId() {
        return mediumId;
    }

    public void setMediumId(Long mediumId) {
        this.mediumId = mediumId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                 + ((ausleihdatum == null) ? 0 : ausleihdatum.hashCode());
        result = prime * result
                 + ((entleiherId == null) ? 0 : entleiherId.hashCode());
        result = prime * result + ((mediumId == null) ? 0 : mediumId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Verleihkarte) {
            Verleihkarte other = (Verleihkarte) obj;

            if (other.getAusleihdatum().equals(ausleihdatum)
                && other.getEntleiherId().equals(entleiherId)
                && other.getMediumId().equals(mediumId)) {

                return true;
            }
        }
        return false;
    }
}
