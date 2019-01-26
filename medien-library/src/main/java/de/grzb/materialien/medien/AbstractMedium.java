package de.grzb.materialien.medien;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import de.grzb.fachwerte.Geldbetrag;

/**
 * TODO für Blatt 4: löschen und Funktionalität in CD einbauen
 * 
 * Ein AbstractMedium bietet eine Standardimplementation für ein Medium an.
 * 
 * @author SE2-Team
 * @version SoSe 2014
 */
@MappedSuperclass
public class AbstractMedium implements Medium {

    // 4 Leerzeichen für formatierte Strings
    protected static final String SPACE = "    ";

    /**
     * Gebühr für einen Tag
     */
    private final int _tagesmietgebuehr;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long _id;

    /**
     * Ein Kommentar zum Medium
     */
    private String _kommentar;

    /**
     * Der Titel des Mediums
     * 
     */
    @NotNull
    private String _titel;

    /**
     * Initialisiert ein neues Exemplar.
     * 
     * @param titel
     *            Der Titel des Mediums
     * @param kommentar
     *            Ein Kommentar zum Medium
     * 
     * @require titel != null
     * @require kommentar != null
     * 
     * @ensure getTitel() == titel
     * @ensure getKommentar() == kommentar
     */
    public AbstractMedium(String titel, String kommentar) {
        assert titel != null : "Vorbedingung verletzt: titel != null";
        assert kommentar != null : "Vorbedingung verletzt: kommentar != null";
        _titel = titel;
        _kommentar = kommentar;
        _tagesmietgebuehr = 300;
    }

    protected AbstractMedium() {
        _tagesmietgebuehr = 0;
    }

    @Override
    public Geldbetrag berechneMietgebuehr(int mietTage) {
        assert mietTage > 0 : "Vorbedingung verletzt: mietTage > 0";

        return new Geldbetrag(_tagesmietgebuehr * mietTage);
    }

    @Override
    public String toFormatiertenString() {
        return getMedienBezeichnung() + ":\n" + SPACE + "ID: " + _id + "\n" + SPACE + "Titel: " + _titel + "\n" + SPACE
                + "Kommentar: " + _kommentar + "\n";
    }

    @Override
    public String getKommentar() {
        return _kommentar;
    }

    /**
     * Ändert den Kommentar
     * 
     * @param kommentar
     *            Ein Kommentar zum Medium
     * 
     * @require kommentar != null
     * @ensure getKommentar() == kommentar
     */
    @Override
    public void setKommentar(String kommentar) {
        assert kommentar != null : "Vorbedingung verletzt: kommentar != null";
        _kommentar = kommentar;
    }

    @Override
    public String getTitel() {
        return _titel;
    }

    /**
     * Ändert den Titel
     * 
     * @param titel
     *            Der Titel des Mediums
     * 
     * @require titel != null
     * @ensure getTitel() == titel
     */
    @Override
    public void setTitel(String titel) {
        assert titel != null : "Vorbedingung verletzt: titel != null";
        _titel = titel;
    }

    @Override
    public String getMedienBezeichnung() {
        return "AbstractMedium";
    }

    @Override
    public long getId() {
        return _id;
    }

    @Override
    public void setId(long id) {
        _id = id;
    }
}
