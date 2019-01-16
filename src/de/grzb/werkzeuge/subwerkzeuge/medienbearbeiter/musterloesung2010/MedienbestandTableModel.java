package de.grzb.werkzeuge.subwerkzeuge.medienbearbeiter.musterloesung2010;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import de.grzb.materialien.medien.Medium;
import de.grzb.werkzeuge.MedienComparator;

/**
 * Dieses TableModel hält die Medien, die an der Oberfläche angezeigt werden
 * sollen. Aufgabe eines Tablemodels ist es u.a., für die jeweiligen Spalten und
 * Zeilen einer GUI-Tabelle die anzuzeigenden Einträge zu liefern. Für jede
 * Zeile weiß dieses Modell, welches Medium dort dargestellt wird.
 * 
 * @author SE2-Team
 * @version SoSe 2014
 */
public class MedienbestandTableModel extends AbstractTableModel
{
    private static final String[] COLUMN_NAMES = new String[] { "Medientyp",
            "Titel", "Kommentar" };

    private static final long serialVersionUID = 1L;

    /**
     * Die Liste der angezeigten Medien. Hält die Medien in nach
     * VormerkMedienFormatiererComparator sortierter Reihenfolge.
     */
    private List<Medium> _medien;

    /**
     * Konstruktor. Initialisiert ein neues Tablemodel mit den gegebenen Medien.
     * 
     * @param medien Eine Liste der anzuzeigenden Medien.
     * 
     * @require medien != null
     */
    public MedienbestandTableModel(List<Medium> medien)
    {
        assert medien != null : "Vorbedingung verletzt: medien != null";
        _medien = new ArrayList<Medium>(medien);

        sortiereMedien();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
        Class<?> clazz = Object.class;
        switch (columnIndex)
        {
        case 0:
        case 1:
        case 2:
            clazz = String.class;
        }
        return clazz;
    }

    @Override
    public int getColumnCount()
    {
        return COLUMN_NAMES.length;
    }

    @Override
    public String getColumnName(int column)
    {
        return COLUMN_NAMES[column];
    }

    @Override
    public int getRowCount()
    {
        if (_medien != null)
        {
            return _medien.size();
        }
        else
        {
            return 0;
        }
    }

    @Override
    public Object getValueAt(int row, int column)
    {
        Medium medium = _medien.get(row);
        Object ergebnis = null;
        switch (column)
        {
        case 0:
            ergebnis = medium.getMedienBezeichnung();
            break;
        case 1:
            ergebnis = medium.getTitel();
            break;
        case 2:
            ergebnis = medium.getKommentar();
            break;
        }
        return ergebnis;
    }

    /**
     * Liefert das Medium, das in der Zeile mit der gegebenen Nummer dargestellt
     * wird.
     * 
     * @param zeile Die Nummer der Tabellenzeile
     * 
     * @require zeileExistiert(zeile)
     * @ensure result != null
     */
    public Medium getMediumFuerZeile(int zeile)
    {
        assert zeileExistiert(zeile) : "Vorbedingung verletzt: zeileExistiert(zeile)";
        return _medien.get(zeile);
    }

    /**
     * Prüft, ob für die gegebene Tabellen-Zeile ein Medium in dem TableModel
     * existiert.
     * 
     * @param zeile Die Nummer der Tabellenzeile
     */
    public boolean zeileExistiert(int zeile)
    {
        boolean result = false;
        if ((zeile < _medien.size()) && (zeile >= 0))
        {
            result = true;
        }
        return result;
    }

    /**
     * Setzt die anzuzeigenden Medien.
     * 
     * @param medien Eine Liste der zu setzenden Medien.
     * 
     * @require medien != null
     */
    public void setMedien(List<Medium> medien)
    {
        assert medien != null : "Vorbedingung verletzt: medien != null";
        _medien = new ArrayList<Medium>(medien);

        sortiereMedien();

        fireTableDataChanged();
    }

    /**
     * Sortiert Medien nach der im MedienComparator angegebenen
     * Sortierreihenfolge.
     */
    private void sortiereMedien()
    {
        Collections.sort(_medien, new MedienComparator());
    }

}
