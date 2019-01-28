package de.grzb.services.verleih;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import de.grzb.fachwerte.Datum;
import de.grzb.materialien.Kunde;
import de.grzb.materialien.Verleihkarte;
import de.grzb.materialien.Vormerkkarte;
import de.grzb.materialien.medien.Medium;
import de.grzb.services.AbstractObservableService;
import de.grzb.services.Connector;
import de.grzb.services.kundenstamm.KundenstammServiceConnector;
import de.grzb.services.medienbestand.MedienbestandServiceConnector;

/**
 * TODO für Blatt 2: Vertragsbedingungen löschen
 * 
 * Diese Klasse implementiert das Interface VerleihServiceConnector. Siehe
 * dortiger Kommentar.
 * 
 * @author SE2-Team
 * @version SoSe 2014
 */

@Service
public class VerleihServiceConnectorImpl extends AbstractObservableService implements VerleihServiceConnector {

    private static final String REMOTE_SERVICE_NAME = "verleih-service";

    private final Connector _connector;

    /**
     * Der Medienbestand.
     */
    private final MedienbestandServiceConnector _medienbestand;

    /**
     * Der Kundenstamm.
     */
    private final KundenstammServiceConnector _kundenstamm;

    /**
     * Konstruktor. Erzeugt einen neuen VerleihServiceConnectorImpl.
     * 
     * @param kundenstamm
     *            Der KundenstammServiceConnector.
     * @param medienbestand
     *            Der MedienbestandServiceConnector.
     * @param initialBestand
     *            Der initiale Bestand.
     * 
     * @require kundenstamm != null
     * @require medienbestand != null
     * @require initialBestand != null
     */
    public VerleihServiceConnectorImpl(KundenstammServiceConnector kundenstamm,
            MedienbestandServiceConnector medienbestand, Connector connector) {
        _kundenstamm = kundenstamm;
        _medienbestand = medienbestand;
        _connector = connector;
    }

    private static class VerleihkartenList extends ArrayList<Verleihkarte> {
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Verleihkarte> getVerleihkarten() {
        return _connector.getResult(REMOTE_SERVICE_NAME, "/getVerleihkarten", VerleihkartenList.class);
    }

    @Override
    public boolean istVerliehen(Medium medium) {
        return _connector.getResult(REMOTE_SERVICE_NAME, "/getVerleihkarten", Boolean.class);
    }

    @Override
    public boolean istVerleihenMoeglich(Kunde kunde, List<Medium> medien) {
        assert kundeImBestand(kunde) : "Vorbedingung verletzt: kundeImBestand(kunde)";
        assert medienImBestand(medien) : "Vorbedingung verletzt: medienImBestand(medien)";

        boolean result = sindAlleNichtVerliehen(medien);

        // TODO für Blatt 6: die folgende For-Schleife komplett löschen
        for(Medium medium : medien) {
            if(istVorgemerkt(medium)) {
                Vormerkkarte karte = getVormerkkarteFuer(medium);
                if(!karte.istErsterVormerker(kunde)) {
                    result = false;
                }
            }
        }

        return result;
    }

    @Override
    public void nimmZurueck(List<Medium> medien, Datum rueckgabeDatum) throws ProtokollierException {
        assert sindAlleVerliehen(medien) : "Vorbedingung verletzt: sindAlleVerliehen(medien)";
        assert rueckgabeDatum != null : "Vorbedingung verletzt: rueckgabeDatum != null";

        for(Medium medium : medien) {
            Verleihkarte verleihkarte = _verleihkarten.get(medium);
            _verleihkarten.remove(medium);
            _protokollierer.protokolliere(VerleihProtokollierer.EREIGNIS_RUECKGABE, verleihkarte);
        }

        informiereUeberAenderung();
    }

    @Override
    public boolean sindAlleNichtVerliehen(List<Medium> medien) {
        assert medienImBestand(medien) : "Vorbedingung verletzt: medienImBestand(medien)";
        boolean result = true;
        for(Medium medium : medien) {
            if(istVerliehen(medium)) {
                result = false;
            }
        }
        return result;
    }

    @Override
    public boolean sindAlleVerliehenAn(Kunde kunde, List<Medium> medien) {
        assert kundeImBestand(kunde) : "Vorbedingung verletzt: kundeImBestand(kunde)";
        assert medienImBestand(medien) : "Vorbedingung verletzt: medienImBestand(medien)";

        boolean result = true;
        for(Medium medium : medien) {
            if(!istVerliehenAn(kunde, medium)) {
                result = false;
            }
        }
        return result;
    }

    @Override
    public boolean istVerliehenAn(Kunde kunde, Medium medium) {
        assert kundeImBestand(kunde) : "Vorbedingung verletzt: kundeImBestand(kunde)";
        assert mediumImBestand(medium) : "Vorbedingung verletzt: mediumImBestand(medium)";

        return istVerliehen(medium) && getEntleiherFuer(medium).equals(kunde);
    }

    @Override
    public boolean sindAlleVerliehen(List<Medium> medien) {
        assert medienImBestand(medien) : "Vorbedingung verletzt: medienImBestand(medien)";

        boolean result = true;
        for(Medium medium : medien) {
            if(!istVerliehen(medium)) {
                result = false;
            }
        }
        return result;
    }

    @Override
    public void verleiheAn(Kunde kunde, List<Medium> medien, Datum ausleihDatum) throws ProtokollierException {
        assert kundeImBestand(kunde) : "Vorbedingung verletzt: kundeImBestand(kunde)";
        assert sindAlleNichtVerliehen(medien) : "Vorbedingung verletzt: sindAlleNichtVerliehen(medien) ";
        assert ausleihDatum != null : "Vorbedingung verletzt: ausleihDatum != null";
        assert istVerleihenMoeglich(kunde, medien) : "Vorbedingung verletzt:  istVerleihenMoeglich(kunde, medien)";

        for(Medium medium : medien) {
            Verleihkarte verleihkarte = new Verleihkarte(kunde, medium, ausleihDatum);

            if(istVorgemerkt(medium)) {
                List<Medium> mediumAsList = new ArrayList<Medium>();
                mediumAsList.add(medium);
                entferneVormerker(kunde, mediumAsList);
            }
            /*
             * TODO für Blatt 1 und 2: Verleihkarte wird nicht der Map
             * hinzugefügt
             * 
             * Ups, hier ist der Fehler. Es wird zwar eine Verleihkarte erzeugt,
             * aber dann ...
             */
            _verleihkarten.put(medium, verleihkarte);
            _protokollierer.protokolliere(VerleihProtokollierer.EREIGNIS_AUSLEIHE, verleihkarte);
        }
        // XXX Was passiert wenn das Protokollieren mitten in der Schleife
        // schief geht? informiereUeberAenderung ineinen finally Block?
        informiereUeberAenderung();
    }

    @Override
    public boolean kundeImBestand(Kunde kunde) {
        return _kundenstamm.enthaeltKunden(kunde);
    }

    @Override
    public boolean mediumImBestand(Medium medium) {
        return _medienbestand.enthaeltMedium(medium);
    }

    @Override
    public boolean medienImBestand(List<Medium> medien) {
        assert medien != null : "Vorbedingung verletzt: medien != null";
        assert !medien.isEmpty() : "Vorbedingung verletzt: !medien.isEmpty()";

        boolean result = true;
        for(Medium medium : medien) {
            if(!mediumImBestand(medium)) {
                result = false;
                break;
            }
        }
        return result;
    }

    @Override
    public List<Medium> getAusgelieheneMedienFuer(Kunde kunde) {
        assert kundeImBestand(kunde) : "Vorbedingung verletzt: kundeImBestand(kunde)";
        List<Medium> result = new ArrayList<Medium>();
        for(Verleihkarte verleihkarte : _verleihkarten.values()) {
            if(verleihkarte.getEntleiher().equals(kunde)) {
                result.add(verleihkarte.getMedium());
            }
        }
        return result;
    }

    @Override
    public Kunde getEntleiherFuer(Medium medium) {
        assert istVerliehen(medium) : "Vorbedingung verletzt: istVerliehen(medium)";
        Verleihkarte verleihkarte = _verleihkarten.get(medium);
        return verleihkarte.getEntleiher();
    }

    @Override
    public Verleihkarte getVerleihkarteFuer(Medium medium) {
        assert istVerliehen(medium) : "Vorbedingung verletzt: istVerliehen(medium)";
        return _verleihkarten.get(medium);
    }

    @Override
    public List<Verleihkarte> getVerleihkartenFuer(Kunde kunde) {
        assert kundeImBestand(kunde) : "Vorbedingung verletzt: kundeImBestand(kunde)";
        List<Verleihkarte> result = new ArrayList<Verleihkarte>();
        for(Verleihkarte verleihkarte : _verleihkarten.values()) {
            if(verleihkarte.getEntleiher().equals(kunde)) {
                result.add(verleihkarte);
            }
        }
        return result;
    }

    @Override
    public void entferneVormerker(Kunde kunde, List<Medium> medien) throws ProtokollierException {
        assert kundeImBestand(kunde) : "Vorbedingung verletzt: kundeImBestand(kunde)";
        assert sindAlleVorgemerktVon(kunde, medien) : "Vorbedingung verletzt: sindAlleVorgemerktVon(kunde, medien)";

        for(Medium medium : medien) {
            Vormerkkarte vormerkkarte = _vormerkkarten.get(medium);
            vormerkkarte.entferneVormerker(kunde);
            if(vormerkkarte.getAlleVormerker().size() == 0) {
                _vormerkkarten.remove(medium);
            }
        }

    }

    @Override
    public boolean istVorgemerktVon(Kunde kunde, Medium medium) {
        assert kundeImBestand(kunde) : "Vorbedingung verletzt: kundeImBestand(kunde)";
        assert mediumImBestand(medium) : "Vorbedingung verletzt: mediumImBestand(medium)";

        if(_vormerkkarten.get(medium) != null) {
            return _vormerkkarten.get(medium).istVorgemerktVon(kunde);
        }
        return false;
    }

    @Override
    public boolean kannVorgemerktWerdenVon(Kunde kunde, Medium medium) {
        assert kundeImBestand(kunde) : "Vorbedingung verletzt: kundeImBestand(kunde)";
        assert mediumImBestand(medium) : "Vorbedingung verletzt: mediumImBestand(medium)";

        boolean vormerkenMoeglich = true;
        boolean bereitsVonKundeEntliehen = false;
        Vormerkkarte vormerkkarte = _vormerkkarten.get(medium);
        Verleihkarte verleihkarte = _verleihkarten.get(medium);

        if(vormerkkarte != null) {
            vormerkenMoeglich = vormerkkarte.akzeptiert(kunde);
        }

        if(verleihkarte != null) {
            bereitsVonKundeEntliehen = verleihkarte.getEntleiher().equals(kunde);
        }
        return vormerkenMoeglich && !bereitsVonKundeEntliehen;
    }

    @Override
    public boolean koennenAlleVorgemerktWerdenVon(Kunde kunde, List<Medium> medien) {
        assert kundeImBestand(kunde) : "Vorbedingung verletzt: kundeImBestand(kunde)";
        assert medienImBestand(medien) : "Vorbedingung verletzt: medienImBestand(medien)";

        for(Medium medium : medien) {
            if(!kannVorgemerktWerdenVon(kunde, medium)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void merkeVor(Kunde kunde, List<Medium> medien) throws ProtokollierException {
        assert kundeImBestand(kunde) : "Vorbedingung verletzt: kundeImBestand(kunde)";
        assert medienImBestand(medien) : "Vorbedingung verletzt: medienImBestand(medien)";
        assert koennenAlleVorgemerktWerdenVon(kunde,
                medien) : "Vorbedingung verletzt: koennenAlleVorgemerktWerdenVon(kunde, medien)";

        for(Medium medium : medien) {
            if(!istVorgemerkt(medium)) {
                Vormerkkarte neueVormerkkarte = new Vormerkkarte(medium, kunde);
                _vormerkkarten.put(medium, neueVormerkkarte);
            }
            else {
                getVormerkkarteFuer(medium).merkeVor(kunde);
            }
        }
        informiereUeberAenderung();
    }

    @Override
    public boolean sindAlleVorgemerktVon(Kunde kunde, List<Medium> medien) {
        assert kundeImBestand(kunde) : "Vorbedingung verletzt: kundeImBestand(kunde)";
        assert medienImBestand(medien) : "Vorbedingung verletzt: medienImBestand(medien)";
        for(Medium medium : medien) {
            if(!istVorgemerktVon(kunde, medium)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean istVorgemerkt(Medium medium) {
        assert mediumImBestand(medium) : "Vorbedingung verletzt: mediumImBestand(medium)";
        return _vormerkkarten.get(medium) != null;
    }

    @Override
    public Vormerkkarte getVormerkkarteFuer(Medium medium) {
        assert mediumImBestand(medium) : "Vorbedingung verletzt: mediumImBestand(medium)";
        assert istVorgemerkt(medium) : "Vorbedingung verletzt: istVorgemerkt(medium)";
        return _vormerkkarten.get(medium);
    }

}
