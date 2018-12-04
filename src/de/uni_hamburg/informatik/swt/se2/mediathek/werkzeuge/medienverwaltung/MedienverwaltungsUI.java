package de.uni_hamburg.informatik.swt.se2.mediathek.werkzeuge.medienverwaltung;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import de.uni_hamburg.informatik.swt.se2.mediathek.werkzeuge.UIConstants;

/**
 * MedienverwaltungsUI beschreibt die grafische Benutzungsoberfläche für das
 * MedienverwaltungsWerkzeug. Sie beinhaltet einen Medienauflister, einen einen
 * Medienauflister, einen MedienDetailAnzeiger und Buttons zum Löschen, Ändern
 * und Anlegen von Medien.
 * 
 * @author SE2-Team
 * @version SoSe 2014
 */
class MedienverwaltungsUI
{
    // Konstante Strings, die an der Oberfläche erscheinen:
    private static final String LOESCHEN_BUTTON_BESCHRIFTUNG = "Löschen";
    private static final String AENDERN_BUTTON_BESCHRIFTUNG = "Ändern";
    private static final String EINFUEGEN_BUTTON_BESCHRIFTUNG = "Neu";

    // UI-Komponenten
    private JButton _hinzufuegenButton;
    private JButton _loeschenButton;
    private JButton _aendernButton;
    private JPanel _hauptPanel;

    private JPanel _medienAuflisterPanel;
    private JPanel _mediendetailAnzeigerPanel;
    private JPanel _detailansichtUndButtonsPanel;

    /**
     * Initialisiert die Elemente der Benutzungsoberfläche.
     * 
     * @param medienauflisterPanel Das UI-Panel des Medienauflisters.
     * @param mediendetailAnzeigerPanel Das UI-Panel des mediendetailAnzeigers.
     * 
     * @require (medienauflisterPanel != null)
     * @require (mediendetailAnzeigerPanel != null)
     */
    public MedienverwaltungsUI(JPanel medienauflisterPanel,
            JPanel mediendetailAnzeigerPanel)
    {
        assert medienauflisterPanel != null : "Vorbedingung verletzt: (medienauflisterPanel != null)";
        assert mediendetailAnzeigerPanel != null : "Vorbedingung verletzt: (mediendetailAnzeigerPanel != null)";

        _medienAuflisterPanel = medienauflisterPanel;
        _mediendetailAnzeigerPanel = mediendetailAnzeigerPanel;

        erzeugeHauptPanel();
        fuegeEinMedienauflisterPanel();
        erzeugeDetailansichtUndButtons();
    }

    /**
     * Erzeugt das Hauptpanel, in das der Auflister und der Materialanzeiger mit
     * den Buttons gepackt werden.
     */
    private void erzeugeHauptPanel()
    {
        _hauptPanel = new JPanel();
        _hauptPanel.setLayout(new BorderLayout());
        _hauptPanel.setPreferredSize(new java.awt.Dimension(-1, -1));
        _hauptPanel.setSize(-1, -1);
        _hauptPanel.setAutoscrolls(true);
        _hauptPanel.setBackground(UIConstants.BACKGROUND_COLOR);
    }

    /**
     * Fügt das medienAuflisterPanel in das hauptPanel ein.
     */
    private void fuegeEinMedienauflisterPanel()
    {
        _hauptPanel.add(_medienAuflisterPanel, BorderLayout.CENTER);
    }

    /**
     * Erzeugt ein Panel für die Detailsansicht mit den Buttons darunter, und
     * fügt es in das hauptPanel ein.
     */
    private void erzeugeDetailansichtUndButtons()
    {
        _detailansichtUndButtonsPanel = new JPanel();
        BorderLayout detailsPanelLayout = new BorderLayout();
        _hauptPanel.add(_detailansichtUndButtonsPanel, BorderLayout.EAST);
        _detailansichtUndButtonsPanel.setLayout(detailsPanelLayout);
        _detailansichtUndButtonsPanel.setPreferredSize(new java.awt.Dimension(
                240, -1));
        _detailansichtUndButtonsPanel.setSize(240, -1);
        _detailansichtUndButtonsPanel
                .setBackground(UIConstants.BACKGROUND_COLOR);
        erzeugeMedienDetailAnzeiger();
        erzeugeButtons();
    }

    /**
     * Erzeugt das Panel mit der Detailansicht.
     */
    private void erzeugeMedienDetailAnzeiger()
    {
        JPanel detailAnzeigerPanel = new JPanel();
        _detailansichtUndButtonsPanel.add(detailAnzeigerPanel,
                BorderLayout.CENTER);
        detailAnzeigerPanel.setLayout(new BorderLayout());
        detailAnzeigerPanel.setPreferredSize(new Dimension(-1, -1));
        detailAnzeigerPanel.setSize(-1, -1);
        detailAnzeigerPanel.setBackground(UIConstants.BACKGROUND_COLOR);
        detailAnzeigerPanel
                .add(_mediendetailAnzeigerPanel, BorderLayout.CENTER);
    }

    /**
     * Erzeugt das Panel mit den Buttons.
     */
    private void erzeugeButtons()
    {
        JPanel buttonPanel = new JPanel();
        _detailansichtUndButtonsPanel.add(buttonPanel, BorderLayout.SOUTH);
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setPreferredSize(new java.awt.Dimension(-1, 330));
        buttonPanel.setSize(-1, -1);
        buttonPanel.setBackground(UIConstants.BACKGROUND_COLOR);

        _hinzufuegenButton = new JButton(EINFUEGEN_BUTTON_BESCHRIFTUNG);
        _aendernButton = new JButton(AENDERN_BUTTON_BESCHRIFTUNG);
        _loeschenButton = new JButton(LOESCHEN_BUTTON_BESCHRIFTUNG);

        buttonPanel.add(_hinzufuegenButton);
        buttonPanel.add(_aendernButton);
        buttonPanel.add(_loeschenButton);

        _hinzufuegenButton.setPreferredSize(new java.awt.Dimension(140, 100));
        _hinzufuegenButton.setSize(-1, -1);
        _hinzufuegenButton.setEnabled(true);
        _hinzufuegenButton.setFont(UIConstants.BUTTON_FONT);

        _aendernButton.setPreferredSize(new java.awt.Dimension(140, 100));
        _aendernButton.setSize(-1, -1);
        _aendernButton.setEnabled(false);
        _aendernButton.setFont(UIConstants.BUTTON_FONT);

        _loeschenButton.setPreferredSize(new java.awt.Dimension(140, 100));
        _loeschenButton.setSize(-1, -1);
        _loeschenButton.setEnabled(false);
        _loeschenButton.setFont(UIConstants.BUTTON_FONT);
    }

    /**
     * Gibt den Hinzufügen-Button zurück.
     * 
     * @return Den Hinzufügen-Button.
     * 
     * @ensure result != null
     */
    public JButton getHinzufuegenButton()
    {
        return _hinzufuegenButton;
    }

    /**
     * Gibt den Ändern-Button zurück.
     * 
     * @return Der Ändern-Button.
     * 
     * @ensure result != null
     */
    public JButton getAendernButton()
    {
        return _aendernButton;
    }

    /**
     * Gibt den Löschen-Button zurück.
     * 
     * @return Den Löschen-Button.
     * 
     * @ensure result != null
     */
    public JButton getLoeschenButton()
    {
        return _loeschenButton;
    }

    /**
     * Gibt das Haupt-Panel der UI zurück.
     * 
     * @return Das Haupt-Panel der UI.
     * 
     * @ensure result != null
     */
    public JPanel getUIPanel()
    {
        return _hauptPanel;
    }
}
