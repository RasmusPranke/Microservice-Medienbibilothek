package de.uni_hamburg.informatik.swt.se2.mediathek.werkzeuge.subwerkzeuge.medienbearbeiter.musterloesung2010;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.medien.CD;
import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.medien.DVD;
import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.medien.Medium;
import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.medien.PCVideospiel;
import de.uni_hamburg.informatik.swt.se2.mediathek.services.medienbestand.MedienbestandService;
import de.uni_hamburg.informatik.swt.se2.mediathek.werkzeuge.UIConstants;

/**
 * Über die MedienbestandUI können neue Medien erfasst und bestehende Medien
 * gelöscht werden.
 * 
 * @author SE2-Team
 * @version April 2010
 * 
 * 
 *          TODO nicht nach WAM-Light gebaut
 */
public class MedienverwaltungUI_ML extends JFrame
{
    // Konstanten, die an der Oberfläche erscheinen:
    private static final String FENSTER_UEBERSCHRIFT = "Medienverwaltung";

    private static final long serialVersionUID = 1L;

    // Swing-Komponenten, auf die auch nach ihrer Erstellung noch zugegriffen
    // werden muss:
    private JLabel _meldungsText;
    private JTextField _titelTextfeld;
    private JTextField _cdLaufzeitTextfeld;
    private JTextField _cdInterpretTextfeld;
    private JTextField _kommentarTextfeld;

    private JTextField _dvdRegisseurTextfeld;
    private JTextField _dvdLaufzeitTextfeld;

    private JTextField _videospielPlattformTextfeld;
    private MedienbestandTableModel _medienTableModel;
    private JTable _medienTable;

    // Der Medienbestand enthällt alle Medien
    private final MedienbestandService _medienbestand;

    /**
     * MedienbestandUI Konstruktor
     * 
     * @param medienbestand Der Medienbestand, aus dem alle vorhanden Medien
     *            bezogen werden
     */
    public MedienverwaltungUI_ML(MedienbestandService medienbestand)
    {
        // Im Konstruktor wird der Fenster-Titel mitgegeben
        super(FENSTER_UEBERSCHRIFT);

        _medienbestand = medienbestand;

        // Gesamtes Layout setzen:
        getContentPane().setLayout(new BorderLayout());

        // Eingabebereich befüllen:
        JPanel nordPanel = new JPanel();
        befuelleEingabeBereich(nordPanel);
        getContentPane().add(BorderLayout.NORTH, nordPanel);

        // Listenbereich befüllen:
        JPanel centerPanel = new JPanel();
        befuelleTabellenBereich(centerPanel);
        getContentPane().add(BorderLayout.CENTER, centerPanel);

        // Statusleiste mit leerem Text initialisieren
        _meldungsText = new JLabel(" ");
        getContentPane().add(BorderLayout.SOUTH, _meldungsText);

        // Beim Schließen des Fensters soll die Anwendung beendet werden
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Fensterlayout berechnen lassen und Fenster sichtbar machen:
        pack();
    }

    /*
     * Füllt das gegebene Panel mit zwei Eingabe-Feldern und einem Button.
     * 
     * Dieser Eingabebereich wird im oberen Teil des Fensters angezeigt.
     * 
     * @param nordPanel das zu befüllende Panel
     */
    private void befuelleEingabeBereich(JPanel eingabePanel)
    {
        /*
         * Hinweis: diese Methode benutzt an einigen Stellen geschachtelte
         * Panels, um ein schöneres Layout hinzukriegen.
         */

        eingabePanel
                .setLayout(new BoxLayout(eingabePanel, BoxLayout.PAGE_AXIS));
        String[] medientyp = { "CD", "DVD", "PCVideospiel",
                "KonsolenVideospiel" };
        // Diese lokalen Variablen müssen final deklariert werden, damit
        // sie aus den anonymen Klassen für die Listener zugreifbar sind.
        final JComboBox medienAuswahlbox = new JComboBox(medientyp);
        final CardLayout eingabeMultiLayout = new CardLayout();
        final JPanel eingabeMultiPanel = new JPanel(eingabeMultiLayout);
        JPanel medienAuswahlPanel = new JPanel();
        medienAuswahlPanel.add(medienAuswahlbox);
        eingabePanel.add(medienAuswahlPanel);

        /*
         * Dies ist eine anonyme innere Klasse. Ihre einzige Methode wird
         * aufgerufen, wenn der Ausleihe-Ansicht-Button gedrückt wird. Daraufhin
         * wird das Ausleihepanel angezeigt.
         */
        medienAuswahlbox.addItemListener(new ItemListener()
        {
            public void itemStateChanged(ItemEvent event)
            {
                eingabeMultiLayout.show(eingabeMultiPanel,
                        (String) event.getItem());
            }
        });

        // Titel einfügen
        befuelleTitel(eingabePanel);

        // Multipannel einfügen
        eingabePanel.add(eingabeMultiPanel);

        JPanel cdPanel = new JPanel();
        befuelleCDPanel(cdPanel);
        eingabeMultiPanel.add(cdPanel, medientyp[0]);

        JPanel dvdPanel = new JPanel();
        befuelleDVDPanel(dvdPanel);
        eingabeMultiPanel.add(dvdPanel, medientyp[1]);

        JPanel videospielPanel = new JPanel();
        befuelleVideospielPanel(videospielPanel);
        eingabeMultiPanel.add(videospielPanel, medientyp[2]);

        befuelleKommentar(eingabePanel);

        erfassenButtonHinzufuegen(eingabePanel, medienAuswahlbox);

        CompoundBorder eingabeBorder = new CompoundBorder(new LineBorder(
                Color.BLACK), new EmptyBorder(10, 10, 10, 10));
        eingabePanel.setBorder(eingabeBorder);
    }

    /*
     * Fügt den Erfassen-Button in das gegebene Panel ein.
     * 
     * @param nordPanel das zu befüllende Panel
     */
    private void erfassenButtonHinzufuegen(JPanel nordPanel,
            final JComboBox medienAuswahlbox)
    {
        JButton erfassenButton = new JButton("Erfassen");
        nordPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        erfassenButton.setBackground(UIConstants.BUTTON_SELECTED_COLOR);
        erfassenButton.setPreferredSize(new java.awt.Dimension(180, 50));
        erfassenButton.setFont(UIConstants.BUTTON_FONT);

        erfassenButton.setAlignmentX(0);

        actionListenerErstellen(medienAuswahlbox, erfassenButton);
        nordPanel.add(erfassenButton);
    }

    /*
     * Fügt die Funktionalität zum gegebenen Eingabe-Button hinzu.
     * 
     * @param medienAuswahlbox Die Auswahl, anhand derer die auszulösende Aktion
     * bestimmt wird
     * 
     * @param erfassenButton der JButton, durch den eine Medienerfassung
     * ausgelöst werden soll.
     */
    private void actionListenerErstellen(final JComboBox medienAuswahlbox,
            JButton erfassenButton)
    {
        erfassenButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent arg0)
            {
                switch (medienAuswahlbox.getSelectedIndex())
                {
                case 0:
                    cdErfassen();
                    break;
                case 1:
                    dvdErfassen();
                    break;
                case 2:
                    videospielErfassen();
                    break;
                }
            }

        });
    }

    /*
     * Erstellt das Panel für den Kommentar
     * 
     * @param eingabePanel Das Panel, dem die Kommentarfelder hinzugefügt werden
     * sollen
     */
    private void befuelleKommentar(JPanel eingabePanel)
    {
        JPanel kommentarPanel = new JPanel(new BorderLayout());
        JLabel kommentarLabel = new JLabel("Kommentar:  ");
        kommentarLabel.setPreferredSize(new Dimension(100, 20));

        kommentarPanel.add(BorderLayout.WEST, kommentarLabel);
        _kommentarTextfeld = new JTextField(20);
        kommentarPanel.add(BorderLayout.CENTER, _kommentarTextfeld);
        eingabePanel.add(kommentarPanel);
    }

    /*
     * Erstellt das Panel für den Titel
     * 
     * @param eingabePanel Das Panel, dem die Titelfelder hinzugefügt werden
     * sollen
     */
    private void befuelleTitel(JPanel eingabePanel)
    {
        JPanel titelPanel = new JPanel(new BorderLayout());
        JLabel titelLabel = new JLabel("Titel:");
        titelLabel.setPreferredSize(new Dimension(100, 20));
        titelPanel.add(BorderLayout.WEST, titelLabel);
        _titelTextfeld = new JTextField(20);
        titelPanel.add(BorderLayout.CENTER, _titelTextfeld);
        eingabePanel.add(titelPanel);
    }

    /*
     * Erstllt das Panel für die CD-Medienerfassung
     * 
     * @param cdPanel Das Panel, dem die CDfelder hinzugefügt werden sollen
     */
    private void befuelleCDPanel(JPanel cdPanel)
    {
        cdPanel.setLayout(new BoxLayout(cdPanel, BoxLayout.PAGE_AXIS));

        JPanel interpretPanel = new JPanel(new BorderLayout());
        JLabel interpretLabel = new JLabel("Interpret:");
        interpretLabel.setPreferredSize(new Dimension(100, 20));
        interpretPanel.add(BorderLayout.WEST, interpretLabel);

        _cdInterpretTextfeld = new JTextField(20);
        interpretPanel.add(BorderLayout.CENTER, _cdInterpretTextfeld);
        cdPanel.add(interpretPanel);

        JPanel laufzeitPanel = new JPanel(new BorderLayout());
        JLabel laufzeitLabel = new JLabel("Laufzeit:");
        laufzeitLabel.setPreferredSize(new Dimension(100, 20));
        laufzeitPanel.add(BorderLayout.WEST, laufzeitLabel);
        _cdLaufzeitTextfeld = new JTextField(20);
        laufzeitPanel.add(BorderLayout.CENTER, _cdLaufzeitTextfeld);
        cdPanel.add(laufzeitPanel);

    }

    /*
     * Erstllt das Panel für die DVD-Medienerfassung
     * 
     * @param dvdPanel Das Panel, dem die DVDfelder hinzugefügt werden sollen
     */
    private void befuelleDVDPanel(JPanel dvdPanel)
    {
        dvdPanel.setLayout(new BoxLayout(dvdPanel, BoxLayout.PAGE_AXIS));

        JPanel regisseurPanel = new JPanel(new BorderLayout());
        JLabel regisseurLabel = new JLabel("Regisseur:");
        regisseurLabel.setPreferredSize(new Dimension(100, 20));
        regisseurPanel.add(BorderLayout.WEST, regisseurLabel);
        _dvdRegisseurTextfeld = new JTextField(20);
        regisseurPanel.add(BorderLayout.CENTER, _dvdRegisseurTextfeld);
        dvdPanel.add(regisseurPanel);

        JPanel laufzeitPanel = new JPanel(new BorderLayout());
        JLabel laufzeitLabel = new JLabel("Laufzeit:");
        laufzeitLabel.setPreferredSize(new Dimension(100, 20));
        laufzeitPanel.add(BorderLayout.WEST, laufzeitLabel);
        _dvdLaufzeitTextfeld = new JTextField(20);
        laufzeitPanel.add(BorderLayout.CENTER, _dvdLaufzeitTextfeld);
        dvdPanel.add(laufzeitPanel);

    }

    /*
     * Erstllt das Panel für die PC/Konsole-Medienerfassung
     * 
     * @param videospielPanel Das Panel, dem die Videospiel-Felder hinzugefügt
     * werden sollen
     */
    private void befuelleVideospielPanel(JPanel videospielPanel)
    {
        videospielPanel.setLayout(new BoxLayout(videospielPanel,
                BoxLayout.PAGE_AXIS));

        JPanel platformPanel = new JPanel(new BorderLayout());
        JLabel platformLabel = new JLabel("Plattform:");
        platformLabel.setPreferredSize(new Dimension(100, 20));
        platformPanel.add(BorderLayout.WEST, platformLabel);
        _videospielPlattformTextfeld = new JTextField(20);
        platformPanel.add(BorderLayout.CENTER, _videospielPlattformTextfeld);
        videospielPanel.add(platformPanel);

        videospielPanel.add(new JLabel(" "));
    }

    /**
     * Füllt das gegebene Panel mit einer Liste inklusive Überschrift.
     * 
     * Dieser Listenbereich wird im unteren Teil des Fenster angezeigt.
     * 
     * @param centerPanel Das Panel, dem die Tabelle hinzugefügt werden soll
     */
    private void befuelleTabellenBereich(JPanel centerPanel)
    {
        centerPanel.setLayout(new BorderLayout());
        _medienTableModel = new MedienbestandTableModel(
                _medienbestand.getMedien());
        _medienTable = new JTable();
        _medienTable.setModel(_medienTableModel);

        CompoundBorder medienBorder = new CompoundBorder(new BevelBorder(
                BevelBorder.LOWERED), new TitledBorder("Erfasste Medien"));
        centerPanel.setBorder(medienBorder);
        JScrollPane scrollPane = new JScrollPane(_medienTable);
        centerPanel.add(BorderLayout.CENTER, scrollPane);
        JButton loeschenButton = new JButton("Letzte löschen");
        loeschenButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent arg0)
            {
                List<Medium> medien = _medienbestand.getMedien();
                if (medien.size() > 0)
                {
                    _medienbestand.entferneMedium(medien.get(medien.size() - 1));
                    aktualisiereGUI();
                }
            }
        });
        centerPanel.add(BorderLayout.SOUTH, loeschenButton);
    }

    // Der Erfassen-Button wurde geklickt: Einsammeln der Textfeldinhalte
    // und erstellen einer CompactDisc, falls möglich.
    private void cdErfassen()
    {
        String interpret = _cdInterpretTextfeld.getText();
        String titel = _titelTextfeld.getText();
        String laufzeitText = _cdLaufzeitTextfeld.getText();
        String kommentar = _kommentarTextfeld.getText();
        try
        {
            int laufzeit = Integer.parseInt(laufzeitText);
            if (laufzeit >= 0)
            {
                CD neueCD = new CD(titel, kommentar, interpret, laufzeit);
                _medienbestand.fuegeMediumEin(neueCD);
                aktualisiereGUI();
            }
            else
            {
                _meldungsText.setText("Bitte nur Laufzeiten größer "
                        + "gleich Null eingeben!");
            }
        }
        catch (NumberFormatException e)
        {
            _meldungsText.setText("Bitte nur ganze Zahlen als Laufzeit "
                    + "eingeben!");
            // Meldung an den Benutzer
        }
    }

    // Der Erfassen-Button wurde geklickt: Einsammeln der Textfeldinhalte
    // und erstellen einer DVD, falls möglich.
    private void dvdErfassen()
    {
        String regisseur = _dvdRegisseurTextfeld.getText();
        String titel = _titelTextfeld.getText();
        String laufzeitText = _dvdLaufzeitTextfeld.getText();
        String kommentar = _kommentarTextfeld.getText();
        try
        {
            int laufzeit = Integer.parseInt(laufzeitText);
            if (laufzeit >= 0)
            {
                DVD neueDVD = new DVD(titel, kommentar, regisseur, laufzeit);
                _medienbestand.fuegeMediumEin(neueDVD);
                aktualisiereGUI();
            }
            else
            {
                _meldungsText.setText("Bitte nur Laufzeiten größer "
                        + "gleich Null eingeben!");
            }
        }
        catch (NumberFormatException e)
        {
            _meldungsText
                    .setText("Bitte nur ganze Zahlen als Laufzeit eingeben!");
            // Meldung an den Benutzer
        }
    }

    // Der Erfassen-Button wurde geklickt: Einsammeln der Textfeldinhalte
    // und erstellen eines Videospiels, falls möglich.
    private void videospielErfassen()
    {
        String titel = _titelTextfeld.getText();
        String plattform = _videospielPlattformTextfeld.getText();
        String kommentar = _kommentarTextfeld.getText();
        PCVideospiel neuesVideospiel = new PCVideospiel(titel, kommentar,
                plattform);
        _medienbestand.fuegeMediumEin(neuesVideospiel);
        aktualisiereGUI();
    }

    /*
     * Aktualisiert die GUI nach Änderungen im Medienbestand
     */
    private void aktualisiereGUI()
    {
        _medienTableModel.setMedien(_medienbestand.getMedien());
        _meldungsText.setText("");
    }
}
