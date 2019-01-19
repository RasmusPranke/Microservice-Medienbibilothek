package de.grzb.werkzeuge.subwerkzeuge.medienbearbeiter;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * Diese Klasse enthält das GUI-Fenster für die Medienverwaltung.
 * 
 * @author SE2-Team
 * @version SoSe 2014
 */
class MedienbearbeitungsUI
{
    // Konstante Strings, die an der Oberfläche erscheinen:
    private static final String FENSTER_UEBERSCHRIFT = "DVD erstellen / bearbeiten";
    private static final String SPEICHERN_BUTTON_BESCHRIFTUNG = "Speichern";
    private static final String VERWERFEN_BUTTON_BESCHRIFTUNG = "Verwerfen";

    // Swing-Komponenten, auf die auch nach ihrer
    // Erstellung noch zugegriffen werden muss:
    private JPanel _hauptPanel;
    private JFrame _frame;

    private JTextField _titelTextFeld;
    private JTextField _kommentarTextFeld;
    private JTextField _regisseurTextFeld;
    private JSpinner _laufzeitSpinner;

    private JButton _speichernButton;
    private JButton _verwerfenButton;

    /**
     * Initialisiert eine neue MedienbearbeitungsUI.
     */
    public MedienbearbeitungsUI()
    {
        // Im Konstruktor wird der Fenster-Titel mitgegeben
        _frame = new JFrame(FENSTER_UEBERSCHRIFT);

        // Benötigte Panel erzeugen
        erzeugeHauptPanel();
        erzeugeEingabebereichPanel();
        erzeugeButtonPanel();

        // Beim Schließen des Fensters soll das Werkzeug beendet werden.
        _frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Mit setPreferredSize kann man die startgröße von Swing-Komponenten
        // setzen: Hier wird die Startgröße des Fensters gesetzt:
        _frame.setPreferredSize(new Dimension(400, 180));
        _frame.pack();
        _frame.setLocationRelativeTo(null);
    }

    /**
     * In das hier erzeugte Panel werden alle anderen UI-Components eingefügt.
     */
    private void erzeugeHauptPanel()
    {
        _hauptPanel = new JPanel();
        _hauptPanel.setLayout(new BorderLayout());
        _frame.add(_hauptPanel);
    }

    /**
     * Erzeugt das Panel mit den Labels und Eingabefeldern und fügt es in das
     * Haupt-Panel ein.
     */
    private void erzeugeEingabebereichPanel()
    {
        // Das Panel für die Labels auf der linken Seite
        JPanel labelsPanel = new JPanel();
        labelsPanel.setLayout(new GridLayout(4, 1));

        // Das Panel für die Eingabefelder auf der rechten Seite
        JPanel eingabeFelderPanel = new JPanel();
        eingabeFelderPanel.setLayout(new GridLayout(4, 1));

        _titelTextFeld = new JTextField();
        _kommentarTextFeld = new JTextField();
        _regisseurTextFeld = new JTextField();
        SpinnerModel model = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1);
        _laufzeitSpinner = new JSpinner(model);

        labelsPanel.add(erzeugeRechtsbuendigesLabel("Titel"));
        labelsPanel.add(erzeugeRechtsbuendigesLabel("Regisseur"));
        labelsPanel.add(erzeugeRechtsbuendigesLabel("Laufzeit"));
        labelsPanel.add(erzeugeRechtsbuendigesLabel("Kommentar"));

        eingabeFelderPanel.add(_titelTextFeld);
        eingabeFelderPanel.add(_regisseurTextFeld);
        eingabeFelderPanel.add(_laufzeitSpinner);
        eingabeFelderPanel.add(_kommentarTextFeld);

        JPanel eingabeGesamtPanel = new JPanel();
        eingabeGesamtPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        eingabeGesamtPanel.setLayout(new BorderLayout(10, 0));
        eingabeGesamtPanel.add(labelsPanel, BorderLayout.WEST);
        eingabeGesamtPanel.add(eingabeFelderPanel, BorderLayout.CENTER);

        _hauptPanel.setLayout(new BorderLayout());
        _hauptPanel.add(eingabeGesamtPanel, BorderLayout.NORTH);
    }

    /**
     * Erzeugt das Panel mit dem Speichern- und dem Verwerfen-Button und fügt es
     * in das Haupt-Panel ein.
     */
    private void erzeugeButtonPanel()
    {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        _speichernButton = new JButton(SPEICHERN_BUTTON_BESCHRIFTUNG);
        _verwerfenButton = new JButton(VERWERFEN_BUTTON_BESCHRIFTUNG);

        buttonPanel.add(_speichernButton);
        buttonPanel.add(_verwerfenButton);

        _hauptPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Erzeugt ein rechtsbündiges Label mit der gegebenen Beschriftung
     * 
     * @param beschriftung gewünschte Beschriftung für das Label
     * @return JLabel das erzeugte Label
     */
    private JLabel erzeugeRechtsbuendigesLabel(String beschriftung)
    {
        JLabel label = new JLabel(beschriftung);
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        return label;
    }

    /**
     * Gibt das Textfeld zur Eingabe des Medientitels zurück.
     * 
     * @return Das Textfeld zur Eingabe des Medientitels.
     * 
     * @ensure result != null
     */
    public JTextField getMedienTitleTextFeld()
    {
        return _titelTextFeld;
    }

    /**
     * Gibt das Textfeld zur Eingabe des Medienkommentars zurück.
     * 
     * @return Das Textfeld zur Eingabe des Medienkommentars.
     * 
     * @ensure result != null
     */
    public JTextField getMedienKommentarTextFeld()
    {
        return _kommentarTextFeld;
    }

    /**
     * Gibt das Textfeld zur Eingabe des Regisseurs des Mediums zurück.
     * 
     * @return Das Textfeld zur Eingabe des Regisseurs des Mediums.
     * 
     * @ensure result != null
     */
    public JTextField getMedienRegisseurTextFeld()
    {
        return _regisseurTextFeld;
    }

    /**
     * Gibt den Regler zur Eingabe des Laufzeit zurück.
     * 
     * @return Den Regler zur Eingabe des Laufzeit.
     * 
     * @ensure result != null
     */
    public JSpinner getLaufzeitSpinner()
    {
        return _laufzeitSpinner;
    }

    /**
     * Gibt den Speichern-Button zurück.
     * 
     * @return Den Speichern-Button.
     * 
     * @ensure result != null
     */
    public JButton getSpeichernButton()
    {
        return _speichernButton;
    }

    /**
     * Gibt den Verwerfen-Button zurück.
     * 
     * @return Den Verwerfen-Button.
     * 
     * @ensure result != null
     */
    public JButton getVerwerfenButton()
    {
        return _verwerfenButton;
    }

    /**
     * Gibt das JFrame der UI zurück.
     * 
     * @return Das JFrame der UI.
     * 
     * @ensure result != null
     */
    public JFrame getUIFrame()
    {
        return _frame;
    }

    /**
     * Zeigt das Medienbearbeitungsfenster an.
     */
    public void zeigeFenster()
    {
        _frame.setLocationRelativeTo(null);
        _frame.setVisible(true);
    }

}
