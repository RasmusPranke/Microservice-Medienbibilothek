package de.grzb.werkzeuge.subwerkzeuge.medienbearbeiter.musterloesung2010;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

/**
 * Diese Klasse enthält das GUI-Fenster für die Medienverwaltung.
 * 
 * Allerdings ist diese Version noch lückenhaft und die Bennennung von
 * Variablen, Labels und Überschriften ist noch nicht korrekt.
 * 
 * @author SE2-Team
 * @version April 2010
 * 
 * 
 *          TODO nicht nach WAM-Light gebaut
 */
class MedienverwaltungUI_VL extends JFrame
{
    // Konstanten, die an der Oberfläche erscheinen:
    private static final String FENSTER_UEBERSCHRIFT = "Medienverwaltung";
    private static final String EINGABE_UEBERSCHRIFT = "Eingabebereich";
    private static final String LABEL_EINGABEFELD2 = "Neuer Wert 2:  ";
    private static final String LABEL_EINGABEFELD1 = "Neuer Wert 1:  ";
    private static final String ERFASSENBUTTON_BESCHRIFTUNG = "Werte erfassen";
    private static final String LISTEN_UEBERSCHRIFT = "Erfasste Werte";

    private static final long serialVersionUID = 1L;

    // Swing-Komponenten, auf die auch nach ihrer Erstellung noch zugegriffen
    // werden muss:
    private JTextField _textFeld1;
    private JTextField _textFeld2;
    private JButton _erfassenButton;
    private JList _medienListe;

    public MedienverwaltungUI_VL()
    {
        // Im Konstruktor wird der Fenster-Titel mitgegeben
        super(FENSTER_UEBERSCHRIFT);

        // Gesamtes Layout setzen:
        getContentPane().setLayout(new BorderLayout());

        // Eingabebereich befüllen:
        JPanel nordPanel = new JPanel();
        befuelleEingabebereich(nordPanel);
        getContentPane().add(BorderLayout.NORTH, nordPanel);

        // Listenbereich befüllen:
        JPanel centerPanel = new JPanel();
        befuelleListenbereich(centerPanel);
        getContentPane().add(BorderLayout.CENTER, centerPanel);

        // Beim Schließen des Fensters soll die Anwendung beendet werden
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Mit setPreferredSize kann man die Startgröße von Swing-Komponenten
        // setzen:
        setPreferredSize(new Dimension(400, 350));

        // Fensterlayout berechnen lassen und Fenster sichtbar machen:
        pack();
        setVisible(true);
    }

    /**
     * Füllt das gegebene Panel mit zwei Eingabe-Feldern und einem Button.
     * 
     * Dieser Eingabebereich wird im oberen Teil des Fensters angezeigt.
     * 
     * @param nordPanel das zu befüllende Panel
     */
    private void befuelleEingabebereich(JPanel nordPanel)
    {
        /*
         * Hinweis: diese Methode benutzt an einigen Stellen geschachtelte
         * Panels, um ein schöneres Layout hinzukriegen.
         */

        // Schöner Rand mit Titel und Extra-Abstand:
        CompoundBorder rand = new CompoundBorder(new TitledBorder(
                EINGABE_UEBERSCHRIFT), new EmptyBorder(3, 3, 3, 2));
        nordPanel.setBorder(rand);

        // Ein BoxLayout für das gesamte Panel setzen:
        nordPanel.setLayout(new BoxLayout(nordPanel, BoxLayout.Y_AXIS));

        eingabeFelderHinzufuegen(nordPanel);

        erfassenButtonHinzufuegen(nordPanel);
    }

    /**
     * Diese Methode fügt zwei Eingabefelder mit Labels in das gegebene Panel
     * ein.
     * 
     * @param nordPanel das zu befüllende Panel
     */
    private void eingabeFelderHinzufuegen(JPanel nordPanel)
    {
        // Neues Panel mit Eingabefeld 1 inklusive Label erstellen und in
        // nordPanel einfügen:
        JPanel eingabePanel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(LABEL_EINGABEFELD1);
        eingabePanel.add(BorderLayout.WEST, label);
        _textFeld1 = new JTextField();
        eingabePanel.add(BorderLayout.CENTER, _textFeld1);
        nordPanel.add(eingabePanel);

        // Neues Panel mit Eingabefeld 2 inklusive Label erstellen und in
        // nordPanel einfügen:
        JPanel eingabePanel2 = new JPanel(new BorderLayout());
        JLabel label2 = new JLabel(LABEL_EINGABEFELD2);
        eingabePanel2.add(BorderLayout.WEST, label2);
        _textFeld2 = new JTextField();
        eingabePanel2.add(BorderLayout.CENTER, _textFeld2);
        nordPanel.add(eingabePanel2);
    }

    /**
     * Fügt den Erfassen-Button in das gegebene Panel ein.
     * 
     * @param nordPanel das zu befüllende Panel
     */
    private void erfassenButtonHinzufuegen(JPanel nordPanel)
    {
        // Neues Panel mit Button erstellen und in nordPanel einfügen:
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBorder(new EmptyBorder(10, 0, 1, 1));
        _erfassenButton = new JButton(ERFASSENBUTTON_BESCHRIFTUNG);
        buttonPanel.add(BorderLayout.EAST, _erfassenButton);
        nordPanel.add(buttonPanel);

        // Funktionalitaet zum Button hinzufügen:
        actionListenerErstellen(_erfassenButton);
    }

    /**
     * Füllt das gegebene Panel mit einer Liste inklusive Überschrift.
     * 
     * Dieser Listenbereich wird im unteren Teil des Fenster angezeigt.
     * 
     * @param centerPanel das zu befüllende Panel
     */
    private void befuelleListenbereich(JPanel centerPanel)
    {
        // Layout für das gegebene centerPanel setzen
        centerPanel.setLayout(new BorderLayout());

        // Schöner Rand mit Titel fuer die Liste:
        CompoundBorder listenBorder = new CompoundBorder(new BevelBorder(
                BevelBorder.LOWERED), new TitledBorder(LISTEN_UEBERSCHRIFT));
        centerPanel.setBorder(listenBorder);

        // Liste mit ScrollPane erstellen und in das Panel einfügen:
        _medienListe = new JList();
        _medienListe.setMinimumSize(new Dimension(200, 200));
        JScrollPane scrollPane = new JScrollPane(_medienListe);
        centerPanel.add(BorderLayout.CENTER, scrollPane);
    }

    /**
     * Fügt die Funktionalität zum gegebenen Eingabe-Button hinzu.
     * 
     * @param erfassenButton der JButton, durch den eine Aktion ausgelöst werden
     *            soll.
     */
    private void actionListenerErstellen(JButton erfassenButton)
    {
        erfassenButton.addActionListener(

        /**
         * Dies ist eine anonyme innere Klasse. Ihre einzige Methode wird
         * aufgerufen, wenn der Button gedrueckt wird.
         */
        new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                /*
                 * Hinweis: Mit setListData() lassen sich alle Einträge einer
                 * JList durch neue ersetzen.
                 */
            }
        });

    }

}
