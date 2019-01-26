package de.grzb.werkzeuge.subwerkzeuge.mediendetailanzeiger;

import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import de.grzb.materialien.medien.Medium;

/**
 * Ein MedienDetailAnzeigerWerkzeug ist ein Werkzeug um die Details von Medien
 * anzuzeigen.
 * 
 * @author SE2-Team
 * @version SoSe 2014
 */
public class MedienDetailAnzeigerWerkzeug
{
    private MedienDetailAnzeigerUI _ui;

    /**
     * Initialisiert ein neues MedienDetailAnzeigerWerkzeug.
     */
    public MedienDetailAnzeigerWerkzeug()
    {
        _ui = new MedienDetailAnzeigerUI();
    }

    /**
     * Setzt die Liste der Medien deren Details angezeigt werden sollen.
     * 
     * @param medien Eine Liste von Medien.
     * 
     * @require (medien != null)
     */
    public void setMedien(List<Medium> medien)
    {
        assert medien != null : "Vorbedingung verletzt: (medien != null)";
        JTextArea selectedMedienTextArea = _ui.getMedienAnzeigerTextArea();
        // TODO für Aufgabe 3.4.2 Die Mediendetails sollen angezeigt werden
        selectedMedienTextArea.setText("");
        // TODO für Blatt 3: Schleife löschen
        for (Medium medium : medien)
        {
            selectedMedienTextArea.append(medium.toFormatiertenString());
            selectedMedienTextArea.append("--------------- \n");
        }
    }

    /**
     * Gibt das Panel dieses Subwerkzeugs zurück.
     * 
     * @ensure result != null
     */
    public JPanel getUIPanel()
    {
        return _ui.getUIPanel();
    }
}
