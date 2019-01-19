package de.grzb.materialien.medien;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

/**
 * TODO für Blatt 1: CDBlatt1 verwenden
 * 
 * 
 * Eine CD ist ein Medium. Zusätzlich zu den Eigenschaften eines Mediums erfasst
 * sie Informationen zu Spiellänge und Interpret.
 * 
 * @author SE2-Team
 * @version SoSe 2014
 */
@Entity
@IdClass(value = CD.CDId.class)
public class CD extends AbstractMedium implements Medium {
    public static class CDId implements Serializable {
        /**
         * 
         */
        private static final long serialVersionUID = 5160712423311301732L;
        private String _interpret;
        private String _titel;

        public CDId(String _titel, String _interpret) {
            super();
            this._interpret = _interpret;
            this._titel = _titel;
        }

        public CDId() {
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((_interpret == null) ? 0 : _interpret.hashCode());
            result = prime * result + ((_titel == null) ? 0 : _titel.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if(this == obj) return true;
            if(obj == null) return false;
            if(getClass() != obj.getClass()) return false;
            CDId other = (CDId) obj;
            if(_interpret == null) {
                if(other._interpret != null) return false;
            }
            else if(!_interpret.equals(other._interpret)) return false;
            if(_titel == null) {
                if(other._titel != null) return false;
            }
            else if(!_titel.equals(other._titel)) return false;
            return true;
        }
    }

    /**
     * Der Interpret der CD
     */
    @Id
    private String _interpret;

    /**
     * Die Spiellänge der CD in Minuten
     */
    private int _spiellaenge;

    /**
     * Initialisiert ein neues Exemplar.
     * 
     * @param titel
     *            Der Titel der CD
     * @param kommentar
     *            Ein Kommentar zu der CD
     * @param interpret
     *            Der Interpret der CD
     * @param spiellaenge
     *            Die Spiellaenge der CD in Minuten
     * 
     * @require titel != null
     * @require kommentar != null
     * @require interpret != null
     * @require spiellaenge > 0
     * 
     * @ensure getTitel() == titel
     * @ensure getKommentar() == kommentar
     * @ensure getInterpret() == interpret
     * @ensure getSpiellaenge() == spiellaenge
     */
    public CD(String titel, String kommentar, String interpret, int spiellaenge) {
        super(titel, kommentar);
        assert spiellaenge > 0 : "Vorbedingung verletzt: spiellaenge > 0";
        assert interpret != null : "Vorbedingung verletzt: interpret != null";
        _spiellaenge = spiellaenge;
        _interpret = interpret;
    }

    protected CD() {
    }

    @Override
    public String getFormatiertenString() {
        return super.getFormatiertenString() + SPACE + "Interpret: " + _interpret + "\n" + SPACE + "Spiellänge: "
                + _spiellaenge + "\n";
    }

    /**
     * Gibt den Interpreten der CD zurück.
     * 
     * @return Den Interpreten der CD.
     * 
     * @ensure result != null
     */
    public String getInterpret() {
        return _interpret;
    }

    /**
     * Ändert den Interpreten
     * 
     * @param interpret
     *            Der Interpret des Mediums
     * 
     * @require interpret != null
     * @ensure getInterpret() == interpret
     */
    public void setInterpret(String interpret) {
        assert interpret != null : "Vorbedingung verletzt: interpret != null";
        _interpret = interpret;
    }

    @Override
    public String getMedienBezeichnung() {
        return "CD";
    }

    /**
     * Gibt die Spiellänge (in Minuten) der CD zurück.
     * 
     * @return Die Spiellänge der CD.
     * 
     * @ensure result > 0
     */
    public int getSpiellaenge() {
        return _spiellaenge;
    }

    /**
     * Ändert die Spiellänge
     * 
     * @param spiellaenge
     *            Spiellänge des Medium
     * 
     * @require spiellaenge > 0
     * @ensure getSpielaenge() == spiellaenge
     */
    public void setSpiellaenge(int spiellaenge) {
        assert spiellaenge > 0 : "Vorbedingung verletzt: spiellaenge > 0";
        _spiellaenge = spiellaenge;
    }

    @Override
    public String toString() {
        return getFormatiertenString();
    }
}
