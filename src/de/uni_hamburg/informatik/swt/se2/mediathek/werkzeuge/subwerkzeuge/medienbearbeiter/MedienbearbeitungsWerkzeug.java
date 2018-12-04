package de.uni_hamburg.informatik.swt.se2.mediathek.werkzeuge.subwerkzeuge.medienbearbeiter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.medien.DVD;
import de.uni_hamburg.informatik.swt.se2.mediathek.services.medienbestand.MedienbestandService;

/**
 * Ein MedienbearbeitungsWerkzeug stellt die Funktionalität bereit um ein Medium
 * neu zu erzeugen oder zu bearbeiten. Die UI wird durch die
 * MedienbearbeitungsUI gestaltet.
 * 
 * Zur Zeit kann das Werkzeuge nur mit DVDs umgehen.
 * 
 * @author SE2-Team
 * @version SoSe 2014
 */
public class MedienbearbeitungsWerkzeug
{
    /**
     * Die zu bearbeitende DVD
     */
    private DVD _dvd;

    /**
     * Der Medienbestand, wird benötigt um Änderungen einzupflegen
     */
    private MedienbestandService _medienbestand;

    // aendernModus ist true: eine bestehende DVD soll geandert werden.
    // aendernModus ist false: eine neue DVD soll angelegt werden
    private boolean _aendernModus;

    /**
     * Die UI der Medienbearbeitung.
     */
    private MedienbearbeitungsUI _medienbearbeitungsUI;

    /**
     * Initialisiert ein neues MedienbearbeitungsWerkzeug. Dieser Konstruktor
     * startet das Werkzeug im Änderungsmodus. Er erwartet als Parameter die zu
     * ändernde DVD.
     * 
     * @param medienbestand Der aktuelle Medienbestand.
     * @param dvd Die zu ändernde DVD
     * 
     * @require medienbestand != null
     * @require dvd != null
     */
    public MedienbearbeitungsWerkzeug(MedienbestandService medienbestand,
            DVD dvd)
    {
        this();

        assert medienbestand != null : "Vorbedingung verletzt: medienbestand != null";
        assert dvd != null : "Vorbedingung verletzt: dvd != null";

        _medienbestand = medienbestand;
        _dvd = dvd;

        _aendernModus = true; // Beim Speichern wird die gegebene DVD
                              // geändert.

        // Setze anzuzeigende Materialien an der UI.
        setzeAnzuzeigendesMaterial();
    }

    /**
     * Initialisiert ein neues MedienbearbeitungsWerkzeug. Dieser Konstruktor
     * startet das Werkzeug im Modus zur Neueingabe einer DVD.
     * 
     * @param medienbestand Der aktuelle Medienbestand.
     * 
     * @require medienbestand != null
     */
    public MedienbearbeitungsWerkzeug(MedienbestandService medienbestand)
    {
        this();

        assert medienbestand != null : "Vorbedingung verletzt: medienbestand != null";
        _medienbestand = medienbestand;

        _aendernModus = false; // Beim Speichern wird eine neue DVD
                               // erzeugt.
    }

    /**
     * Privater Konstruktor, wird von den anderen beiden Konstruktoren
     * aufgerufen.
     */
    private MedienbearbeitungsWerkzeug()
    {
        // Erzeuge UI
        _medienbearbeitungsUI = new MedienbearbeitungsUI();

        // Die Aktionen werden erzeugt und an der UI registriert.
        registriereUIAktionen();
    }

    /**
     * Setzt das anzuzeigende Material an der UI.
     */
    private void setzeAnzuzeigendesMaterial()
    {
        _medienbearbeitungsUI.getMedienTitleTextFeld().setText(_dvd.getTitel());
        _medienbearbeitungsUI.getMedienKommentarTextFeld().setText(
                _dvd.getKommentar());
        _medienbearbeitungsUI.getMedienRegisseurTextFeld().setText(
                _dvd.getRegisseur());
        _medienbearbeitungsUI.getLaufzeitSpinner().setValue(_dvd.getLaufzeit());
    }

    /**
     * Erzeugt die UI-Aktionen und registriert sie.
     */
    private void registriereUIAktionen()
    {
        registriereSpeichernAktion();
        registriereVerwerfenAktion();
    }

    /**
     * Registriert die Aktion, die ausgeführt wird, wenn der Hinzufügen-Button
     * gedruck wird.
     */
    private void registriereSpeichernAktion()
    {
        _medienbearbeitungsUI.getSpeichernButton().addActionListener(
                new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent arg0)
                    {
                        String titel = _medienbearbeitungsUI
                                .getMedienTitleTextFeld().getText();
                        String regisseur = _medienbearbeitungsUI
                                .getMedienRegisseurTextFeld().getText();
                        int laufzeit = (Integer) _medienbearbeitungsUI
                                .getLaufzeitSpinner().getValue();
                        String kommentar = _medienbearbeitungsUI
                                .getMedienKommentarTextFeld().getText();

                        boolean eingabenGueltig = sindAlleEingabenGueltig(
                                titel, regisseur, laufzeit, kommentar);
                        if (eingabenGueltig)
                        {
                            if (_aendernModus)
                            {
                                aendereDVD(titel, regisseur, laufzeit,
                                        kommentar);
                            }
                            else
                            {
                                erzeugeNeueDVD(titel, regisseur, laufzeit,
                                        kommentar);
                            }
                        }
                        else
                        {
                            gibEingabeFehlermeldungAus(titel, regisseur,
                                    laufzeit, kommentar);
                        }
                    }
                });
    }

    /**
     * Registriert die Aktion, die ausgeführt wird, wenn der Verwerfen-Button
     * gedrückt wird.
     */
    private void registriereVerwerfenAktion()
    {
        _medienbearbeitungsUI.getVerwerfenButton().addActionListener(
                new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        _medienbearbeitungsUI.getUIFrame().dispose();
                    }
                });
    }

    /**
     * Zeigt das Medienverwaltungsfenster an.
     */
    public void zeigeFenster()
    {
        _medienbearbeitungsUI.zeigeFenster();
    }

    /**
     * Erzeugt eine neue DVD mit den gegebenen Werten und fügt sie in den
     * Medienbestand ein.
     * 
     * @require titel != null
     * @require regisseur != null
     * @require laufzeit > 0
     * @require kommentar != null
     */
    private void erzeugeNeueDVD(String titel, String regisseur, int laufzeit,
            String kommentar)
    {
        assert titel != null : "Vorbedingung verletzt: titel != null";
        assert regisseur != null : "Vorbedingung verletzt: regisseur != null";
        assert laufzeit > 0 : "Vorbedingung verletzt: laufzeit > 0";
        assert kommentar != null : "Vorbedingung verletzt: kommentar != null";

        _medienbestand.fuegeMediumEin(new DVD(titel, kommentar, regisseur,
                laufzeit));
        _medienbearbeitungsUI.getUIFrame().dispose();
    }

    /**
     * Ändert die bearbeitete DVD anhand der gegebenen Werte und informiert den
     * Medienbestand, dass sich Medien geändert haben.
     * 
     * @require titel != null
     * @require regisseur != null
     * @require laufzeit > 0
     * @require kommentar != null
     */
    private void aendereDVD(String titel, String regisseur, int laufzeit,
            String kommentar)
    {
        assert titel != null : "Vorbedingung verletzt: titel != null";
        assert regisseur != null : "Vorbedingung verletzt: regisseur != null";
        assert laufzeit > 0 : "Vorbedingung verletzt: laufzeit > 0";
        assert kommentar != null : "Vorbedingung verletzt: kommentar != null";

        _dvd.setTitel(titel);
        _dvd.setKommentar(kommentar);
        _dvd.setRegisseur(regisseur);
        _dvd.setLaufzeit(laufzeit);
        _medienbestand.medienWurdenGeaendert();
        _medienbearbeitungsUI.getUIFrame().dispose();
    }

    /**
     * Prüft, ob die gegebenen Eingaben gültig sind.
     * 
     * @return false, wenn mindestens eine Eingabe ungültig ist, sonst true
     */
    private boolean sindAlleEingabenGueltig(String titel, String regisseur,
            int laufzeit, String kommentar)
    {
        return istEingabeGueltig(titel) && istEingabeGueltig(regisseur)
                && istEingabeGueltig(laufzeit) && istEingabeGueltig(kommentar);
    }

    /**
     * Prüft, welche der gegebenen Eingabewerte ungültig sind, und gibt für den
     * ersten gefundenen Fehler eine Fehlermeldung aus.
     */
    private void gibEingabeFehlermeldungAus(String titel, String regisseur,
            int laufzeit, String kommentar)
    {
        if (!istEingabeGueltig(titel))
        {
            gibEingabeFehlermeldungAus("den Titel");
        }
        else if (!istEingabeGueltig(regisseur))
        {
            gibEingabeFehlermeldungAus("den Regisseur");
        }
        else if (!istEingabeGueltig(laufzeit))
        {
            gibEingabeFehlermeldungAus("die Laufzeit");
        }
        else if (!istEingabeGueltig(kommentar))
        {
            gibEingabeFehlermeldungAus("den Kommentar");
        }
    }

    /**
     * Prüft ob der gegebene String eine gültige Eingabe ist.
     * 
     * @param text Der zu prüfende Text
     * @return true, wenn eine Eingabe erfolgte, sonst false.
     */
    private boolean istEingabeGueltig(String text)
    {
        return (text != null && !text.matches("[ ]*"));
    }

    /**
     * Prüft ob die gegebene Zahl eine gültige Eingabe ist.
     * 
     * @param value Die zu prüfende Zahl
     * @return true, wenn die Zahl > 0 ist, sonst false.
     */
    protected boolean istEingabeGueltig(int value)
    {
        return value > 0;
    }

    /**
     * zeigt einen Warnungs-Dialog an, für ein fehlerhaft ausgefülltes Textfeld.
     * 
     * @param textfeldBezeichnung Eine Bezeichnung des fehlerhaften Textfeldes.
     * 
     * @require textfeldBezeichnung != null
     */
    private void gibEingabeFehlermeldungAus(String textfeldBezeichnung)
    {
        assert textfeldBezeichnung != null : "Vorbedingung verletzt: textfeldBezeichnung != null";

        JOptionPane.showMessageDialog(null, "Bitte geben Sie "
                + textfeldBezeichnung + " ein!", "Eingabefehler",
                JOptionPane.WARNING_MESSAGE);
    }
}
