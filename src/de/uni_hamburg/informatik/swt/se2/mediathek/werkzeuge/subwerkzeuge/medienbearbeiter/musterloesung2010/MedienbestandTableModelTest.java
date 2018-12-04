package de.uni_hamburg.informatik.swt.se2.mediathek.werkzeuge.subwerkzeuge.medienbearbeiter.musterloesung2010;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.medien.CD;
import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.medien.Medium;

public class MedienbestandTableModelTest
{

    private Medium _cd1;
    private Medium _cd2;
    private MedienbestandTableModel _model;

    public MedienbestandTableModelTest()
    {
        _cd1 = new CD("CD1-Titel", "CD1-Kommentar", "CD1-Interpret", 42);
        _cd2 = new CD("CD2-Titel", "CD2-Kommentar", "CD2-Regisseur", 120);
        List<Medium> medien = new ArrayList<Medium>();
        medien.add(_cd1);
        medien.add(_cd2);
        _model = new MedienbestandTableModel(medien);
    }

    @Test
    public void testeLeereMedienListe() throws Exception
    {
        MedienbestandTableModel leer = new MedienbestandTableModel(
                new ArrayList<Medium>());
        // erwartet: es gibt trotzdem alle Spalten, aber keine Zeilen
        assertEquals(3, leer.getColumnCount());
        assertEquals(0, leer.getRowCount());
    }

    @Test
    public void testeMedienMitZeilenVerknuepft() throws Exception
    {
        // Die Medien werden nach Medienart sortiert, deshalb muss die CD vor
        // der DVD auftauchen.
        assertTrue(_model.zeileExistiert(0));
        assertEquals(_cd1, _model.getMediumFuerZeile(0));
        assertTrue(_model.zeileExistiert(1));
        assertEquals(_cd2, _model.getMediumFuerZeile(1));
    }

    @Test
    public void testeSpaltenDefinition() throws Exception
    {
        assertEquals(3, _model.getColumnCount());
        assertEquals("Medientyp", _model.getColumnName(0));
        assertEquals("Titel", _model.getColumnName(1));
        assertEquals("Kommentar", _model.getColumnName(2));
        assertEquals(String.class, _model.getColumnClass(0));
        assertEquals(String.class, _model.getColumnClass(1));
        assertEquals(String.class, _model.getColumnClass(2));
    }

    @Test
    public void testeWerte() throws Exception
    {
        // Die Icons in der dritten Spalte werden von diesem Test nicht
        // überprüft.
        assertEquals("CD", _model.getValueAt(0, 0));
        assertEquals("CD1-Titel", _model.getValueAt(0, 1));
        assertEquals("CD", _model.getValueAt(1, 0));
        assertEquals("CD2-Titel", _model.getValueAt(1, 1));
    }

    @Test
    public void testeZeilenAnzahl() throws Exception
    {
        assertEquals(2, _model.getRowCount());
    }

    @Test
    public void testeMedienSetzen() throws Exception
    {
        List<Medium> medien = new ArrayList<Medium>();
        medien.add(_cd1);
        medien.add(_cd2);
        MedienbestandTableModel model = new MedienbestandTableModel(
                new ArrayList<Medium>());
        model.setMedien(medien);
        assertEquals(2, model.getRowCount());
    }
}
