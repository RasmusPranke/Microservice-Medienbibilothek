package de.grzb.werkzeuge.medienverwaltung;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import de.grzb.materialien.medien.DVD;
import de.grzb.materialien.medien.Medium;
import de.grzb.services.medienbestand.MedienbestandService;
import de.grzb.services.verleih.VerleihService;
import de.grzb.werkzeuge.SubWerkzeugObserver;
import de.grzb.werkzeuge.subwerkzeuge.medienbearbeiter.MedienbearbeitungsWerkzeug;
import de.grzb.werkzeuge.subwerkzeuge.mediendetailanzeiger.MedienDetailAnzeigerWerkzeug;
import de.grzb.werkzeuge.subwerkzeuge.medienverwaltungmedienauflister.MedienverwaltungMedienauflisterWerkzeug;

/**
 * Ein MedienverwaltungsWerkzeug stellt die Funktionalität für die
 * Medienverwaltung bereit. Mit ihm können Medien erzeugt, geändert und gelöscht
 * werden. Die UI wird durch die MedienverwaltungsUI gestaltet.
 * 
 * Zur Zeit können alle Medienarten gelöscht werden, aber nur DVDs lassen sich
 * anlegen und ändern.
 * 
 * @author SE2-Team
 * @version SoSe 2014
 */
public class MedienverwaltungsWerkzeug
{
    /**
     * Der akteulle Medienbestand.
     */
    private MedienbestandService _medienbestand;

    /**
     * Die UI der Medienverwaltung.
     */
    private MedienverwaltungsUI _medienverwaltungUI;

    /**
     * Das Werkzeug zum auflisten des aktuellen Medienbestands.
     */
    private MedienverwaltungMedienauflisterWerkzeug _medienAuflisterWerkzeug;

    /**
     * Das Werkzeug zum anzeigen der Details des selektierten Mediums.
     */
    private MedienDetailAnzeigerWerkzeug _medienDetailAnzeigerWerkzeug;

    /**
     * Der Verleih-Service.
     */
    private final VerleihService _verleihService;

    /**
     * Initialisiert ein neues MedienverwaltungsWerkzeug.
     * 
     * @param medienbestand Der aktuelle Medienbestand.
     * 
     * @require medienbestand != null
     * @require verleihService != null
     */
    public MedienverwaltungsWerkzeug(MedienbestandService medienbestand,
            VerleihService verleihService)
    {
        assert medienbestand != null : "Vorbedingung verletzt: medienbestand != null";
        assert verleihService != null : "Vorbedingung verletzt: verleihService != null";
        _medienbestand = medienbestand;
        _verleihService = verleihService;

        // Subwerkzeuge erstellen
        _medienAuflisterWerkzeug = new MedienverwaltungMedienauflisterWerkzeug(
                medienbestand);
        _medienDetailAnzeigerWerkzeug = new MedienDetailAnzeigerWerkzeug();

        // Erzeuge UI
        _medienverwaltungUI = new MedienverwaltungsUI(
                _medienAuflisterWerkzeug.getUIPanel(),
                _medienDetailAnzeigerWerkzeug.getUIPanel());

        // Beobachter erzeugen und an den Subwerkzeugen registrieren
        registriereSubWerkzeugBeobachter();

        // Die Verwaltungsaktionen werden erzeugt und an der UI registriert.
        registriereUIAktionen();
    }

    /**
     * Registriert die Aktionen, die bei bestimmten Änderungen in Subwerkzeugen
     * ausgeführt werden.
     */
    private void registriereSubWerkzeugBeobachter()
    {
        registriereMedienSelektionsAktion();
    }

    /**
     * Registiert die Aktion, die ausgeführt wird, wenn ein Medium ausgewählt
     * wird.
     */
    private void registriereMedienSelektionsAktion()
    {
        _medienAuflisterWerkzeug
                .registriereBeobachter(new SubWerkzeugObserver()
                {

                    @Override
                    public void reagiereAufAenderung()
                    {
                        zeigeAusgewaehlteMedien();
                        aktualisiereButtons();
                    }
                });
    }

    /**
     * Erzeugt die UI-Aktionen und registriert diese.
     */
    private void registriereUIAktionen()
    {
        registriereHinzufuegenAktion();
        registriereAendernAktion();
        registriereLoeschenAktion();
    }

    /**
     * Zeigt die Details der ausgewählten Medien.
     */
    private void zeigeAusgewaehlteMedien()
    {
        Medium medium = _medienAuflisterWerkzeug.getSelectedMedium();
        List<Medium> selectedMedien = new ArrayList<Medium>();
        if (medium != null)
        {
            selectedMedien.add(medium);
        }
        _medienDetailAnzeigerWerkzeug.setMedien(selectedMedien);
    }

    /**
     * Registiert die Aktion, die beim Klicken auf Neu ausgeführt wird.
     */
    private void registriereHinzufuegenAktion()
    {
        _medienverwaltungUI.getHinzufuegenButton().addActionListener(
                new ActionListener()
                {

                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        new MedienbearbeitungsWerkzeug(_medienbestand)
                                .zeigeFenster();
                    }
                });

    }

    /**
     * Registiert die Aktion, die beim Klicken auf Ändern ausgeführt wird.
     */
    private void registriereAendernAktion()
    {
        _medienverwaltungUI.getAendernButton().addActionListener(
                new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        Medium medium = _medienAuflisterWerkzeug
                                .getSelectedMedium();
                        if (medium != null && medium instanceof DVD)
                        {
                            new MedienbearbeitungsWerkzeug(_medienbestand,
                                    (DVD) medium).zeigeFenster();
                        }

                    }
                });
    }

    /**
     * Registriert die Aktion, die ausgeführt wird, wenn der Löschen-Button
     * gedrückt wird.
     */
    private void registriereLoeschenAktion()
    {
        _medienverwaltungUI.getLoeschenButton().addActionListener(
                new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        Medium medium = _medienAuflisterWerkzeug
                                .getSelectedMedium();
                        if (medium != null)
                        {
                            _medienbestand.entferneMedium(medium);
                        }
                    }
                });
    }

    /**
     * Graut die Button ein/aus je nach Selektion
     */
    private void aktualisiereButtons()
    {
        Medium selectedMedium = _medienAuflisterWerkzeug.getSelectedMedium();
        boolean verliehenOderVorgemerkt = (selectedMedium != null)
                && (_verleihService.istVerliehen(selectedMedium) || _verleihService
                        .istVorgemerkt(selectedMedium));
        _medienverwaltungUI.getLoeschenButton().setEnabled(
                (selectedMedium != null) && !verliehenOderVorgemerkt);

        _medienverwaltungUI.getAendernButton().setEnabled(
                (selectedMedium != null) && (selectedMedium instanceof DVD));
    }

    /**
     * Gibt das Panel, dass die UI-Koponente darstellt zurück.
     * 
     * @return Das Panel, dass die UI-Koponente darstellt.
     * 
     * @ensure result != null
     */
    public JPanel getUIPanel()
    {
        return _medienverwaltungUI.getUIPanel();
    }
}
