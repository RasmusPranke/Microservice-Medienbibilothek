package de.grzb.werkzeuge.rueckgabe;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import de.grzb.fachwerte.Datum;
import de.grzb.fachwerte.Kundennummer;
import de.grzb.materialien.Kunde;
import de.grzb.materialien.Verleihkarte;
import de.grzb.materialien.medien.CD;
import de.grzb.materialien.medien.DVD;
import de.grzb.materialien.medien.Medium;
import de.grzb.services.kundenstamm.KundenstammServiceConnector;
import de.grzb.services.kundenstamm.KundenstammServiceConnectorImpl;
import de.grzb.services.medienbestand.MedienbestandServiceConnector;
import de.grzb.services.medienbestand.MedienbestandServiceConnectorImpl;
import de.grzb.services.verleih.VerleihServiceConnector;
import de.grzb.services.verleih.VerleihServiceConnectorImpl;

public class VerleihkartenTableModelTest {
    private Verleihkarte _karte1;
    private Verleihkarte _karte2;
    private VerleihServiceConnector _verleihService;
    private VerleihkartenTableModel _model;

    public VerleihkartenTableModelTest() {
        Kunde kundeSchmitz = new Kunde(new Kundennummer(123456), "Susi", "Schmitz");
        Kunde kundeSchmidt = new Kunde(new Kundennummer(456789), "Klaus", "Schmidt");
        KundenstammServiceConnector kundenstamm = new KundenstammServiceConnectorImpl(new ArrayList<Kunde>());
        kundenstamm.fuegeKundenEin(kundeSchmitz);
        kundenstamm.fuegeKundenEin(kundeSchmidt);
        Medium cd = new CD("CD-Titel", "CD-Kommentar", "CD-Interpret", 42);
        Medium dvd = new DVD("DVD-Titel", "DVD-Kommentar", "DVD-Regisseur", 120);
        MedienbestandServiceConnector medienbestand = new MedienbestandServiceConnectorImpl(new ArrayList<Medium>());
        medienbestand.fuegeMediumEin(cd);
        medienbestand.fuegeMediumEin(dvd);
        Datum datum = new Datum(14, 4, 2009);
        _karte1 = new Verleihkarte(kundeSchmitz, dvd, datum);
        _karte2 = new Verleihkarte(kundeSchmidt, cd, datum);
        List<Verleihkarte> verleihkarten = new ArrayList<Verleihkarte>();
        verleihkarten.add(_karte1);
        verleihkarten.add(_karte2);
        _verleihService = new VerleihServiceConnectorImpl(kundenstamm, medienbestand, verleihkarten);
        _model = new VerleihkartenTableModel();
        _model.setVerleihkarten(_verleihService.getVerleihkarten());
    }

    @Test
    public void testeLeereVeleihkartenListe() throws Exception {
        VerleihkartenTableModel leer = new VerleihkartenTableModel();
        // erwartet: es gibt trotzdem alle Spalten, aber keine Zeilen
        assertEquals(6, leer.getColumnCount());
        assertEquals(0, leer.getRowCount());
    }

    @Test
    public void testeSpaltenDefinition() throws Exception {
        assertEquals(6, _model.getColumnCount());
        assertEquals("Kunde", _model.getColumnName(0));
        assertEquals("Mediumtyp", _model.getColumnName(1));
        assertEquals("Titel", _model.getColumnName(2));
        assertEquals("Ausleihdatum", _model.getColumnName(3));
        assertEquals("Ausleihdauer (Tage)", _model.getColumnName(4));
        assertEquals("Mietgebühr (€)", _model.getColumnName(5));
    }

    @Test
    public void testeVerleihkartenMitZeilenVerknuepft() throws Exception {
        // Verleihkarten werden nach Kunde sortiert, deshalb muss die Karte von
        // Schmidt vor der von Schmitz auftauchen
        assertEquals(_karte2, _model.getVerleihkartenFuerZeile(0));
        assertEquals(_karte1, _model.getVerleihkartenFuerZeile(1));
    }

    @Test
    public void testeWerte() throws Exception {
        // Dieser Test prüft nicht, ob die Ausleihdauer richtig angegeben wird,
        // weil für die Ausgabe der Ausleihdauer auf das heutige Datum
        // zugegriffen wird, das aus dem Test heraus nicht gesetzt werden kann.

        assertEquals("Klaus Schmidt", _model.getValueAt(0, 0));
        assertEquals("CD", _model.getValueAt(0, 1));
        assertEquals("CD-Titel", _model.getValueAt(0, 2));
        assertEquals(new Datum(14, 4, 2009).toString(), _model.getValueAt(0, 3));
        assertEquals("Susi Schmitz", _model.getValueAt(1, 0));
        assertEquals("DVD", _model.getValueAt(1, 1));
        assertEquals("DVD-Titel", _model.getValueAt(1, 2));
        assertEquals(new Datum(14, 4, 2009).toString(), _model.getValueAt(1, 3));
    }

    @Test
    public void testeZeilenAnzahl() throws Exception {
        assertEquals(2, _model.getRowCount());
    }
}
