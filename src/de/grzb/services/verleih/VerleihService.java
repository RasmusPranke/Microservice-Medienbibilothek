package de.grzb.services.verleih;

import java.util.List;

import de.grzb.fachwerte.Datum;
import de.grzb.materialien.Kunde;
import de.grzb.materialien.Verleihkarte;
import de.grzb.materialien.Vormerkkarte;
import de.grzb.materialien.medien.Medium;
import de.grzb.services.ObservableService;

/**
 * TODO für Blatt 5: ProtokollierException löschen
 * 
 * Der VerleihService erlaubt es, Medien auszuleihen und zurückzugeben.
 * 
 * Für jedes ausgeliehene Medium wird eine neue Verleihkarte angelegt. Wird das
 * Medium wieder zurückgegeben, so wird diese Verleihkarte wieder gelöscht.
 * 
 * VerleihService ist ein ObservableService, als solcher bietet er die
 * Möglichkeit über Verleihvorgänge zu informieren. Beobachter müssen das
 * Interface ServiceObserver implementieren.
 * 
 * @author SE2-Team
 * @version SoSe 2014
 */
public interface VerleihService extends ObservableService
{
    /**
     * Verleiht Medien an einen Kunden. Dabei wird für jedes Medium eine neue
     * Verleihkarte angelegt.
     * 
     * @param kunde Ein Kunde, an den ein Medium verliehen werden soll
     * @param medien Die Medien, die verliehen werden sollen
     * @param ausleihDatum Der erste Ausleihtag
     * 
     * @throws ProtokollierException Wenn beim Protokollieren des
     *             Verleihvorgangs ein Fehler auftritt.
     * 
     * @require kundeImBestand(kunde)
     * @require sindAlleNichtVerliehen(medien)
     * @require ausleihDatum != null
     * 
     * @ensure sindAlleVerliehenAn(kunde, medien)
     */
    void verleiheAn(Kunde kunde, List<Medium> medien, Datum ausleihDatum)
            throws ProtokollierException;

    /**
     * Prüft ob die ausgewählten Medium für den Kunde ausleihbar sind
     * 
     * @param kunde Der Kunde für den geprüft werden soll
     * @param medien Die medien
     * 
     * 
     * @return true, wenn das Entleihen für diesen Kunden möglich ist, sonst
     *         false
     * 
     * @require kundeImBestand(kunde)
     * @require medienImBestand(medien)
     */
    boolean istVerleihenMoeglich(Kunde kunde, List<Medium> medien);

    /**
     * Liefert den Entleiher des angegebenen Mediums.
     * 
     * @param medium Das Medium.
     * 
     * @return Den Entleiher des Mediums.
     * 
     * @require istVerliehen(medium)
     * 
     * @ensure result != null
     */
    Kunde getEntleiherFuer(Medium medium);

    /**
     * Liefert alle Medien, die von dem gegebenen Kunden ausgeliehen sind.
     * 
     * @param kunde Der Kunde.
     * @return Alle Medien, die von dem gegebenen Kunden ausgeliehen sind.
     *         Liefert eine leere Liste, wenn der Kunde aktuell nichts
     *         ausgeliehen hat.
     * 
     * @require kundeImBestand(kunde)
     * 
     * @ensure result != null
     */
    List<Medium> getAusgelieheneMedienFuer(Kunde kunde);

    /**
     * @return Eine Listenkopie aller Verleihkarten. Für jedes ausgeliehene
     *         Medium existiert eine Verleihkarte. Ist kein Medium verliehen,
     *         wird eine leere Liste zurückgegeben.
     * 
     * @ensure result != null
     */
    List<Verleihkarte> getVerleihkarten();

    /**
     * Nimmt zuvor ausgeliehene Medien zurück. Die entsprechenden Verleihkarten
     * werden gelöscht.
     * 
     * 
     * @param medien Die Medien.
     * @param rueckgabeDatum Das Rückgabedatum.
     * 
     * @require sindAlleVerliehen(medien)
     * @require rueckgabeDatum != null
     * 
     * @ensure sindAlleNichtVerliehen(medien)
     */
    void nimmZurueck(List<Medium> medien, Datum rueckgabeDatum)
            throws ProtokollierException;

    /**
     * Prüft ob das angegebene Medium verliehen ist.
     * 
     * @param medium Ein Medium, für das geprüft werden soll ob es verliehen
     *            ist.
     * @return true, wenn das gegebene medium verliehen ist, sonst false.
     * 
     * @require mediumImBestand(medium)
     */
    boolean istVerliehen(Medium medium);

    /**
     * Prüft ob alle angegebenen Medien nicht verliehen sind.
     * 
     * @param medien Eine Liste von Medien.
     * @return true, wenn alle gegebenen Medien nicht verliehen sind, sonst
     *         false.
     * 
     * @require medienImBestand(medien)
     */
    boolean sindAlleNichtVerliehen(List<Medium> medien);

    /**
     * Prüft ob alle angegebenen Medien verliehen sind.
     * 
     * @param medien Eine Liste von Medien.
     * 
     * @return true, wenn alle gegebenen Medien verliehen sind, sonst false.
     * 
     * @require medienImBestand(medien)
     */
    boolean sindAlleVerliehen(List<Medium> medien);

    /**
     * Prüft, ob alle angegebenen Medien an einen bestimmten Kunden verliehen
     * sind.
     * 
     * @param kunde Ein Kunde
     * @param medien Eine Liste von Medien
     * @return true, wenn alle Medien an den Kunden verliehen sind, sonst false.
     * 
     * @require kundeImBestand(kunde)
     * @require medienImBestand(medien)
     */
    boolean sindAlleVerliehenAn(Kunde kunde, List<Medium> medien);

    /**
     * Prüft, ob ein Medium an einen bestimmten Kunden verliehen ist.
     * 
     * @param kunde Ein Kunde
     * @param medium Ein Medium
     * @return true, wenn das Medium an den Kunden verliehen ist, sonst false.
     * 
     * @require kundeImBestand(kunde)
     * @require mediumImBestand(medium)
     */
    boolean istVerliehenAn(Kunde kunde, Medium medium);

    /**
     * Prüft ob der angebene Kunde existiert. Ein Kunde existiert, wenn er im
     * Kundenstamm enthalten ist.
     * 
     * @param kunde Ein Kunde.
     * @return true wenn der Kunde existiert, sonst false.
     * 
     * @require kunde != null
     */
    boolean kundeImBestand(Kunde kunde);

    /**
     * Prüft ob das angebene Medium existiert. Ein Medium existiert, wenn es im
     * Medienbestand enthalten ist.
     * 
     * @param medium Ein Medium.
     * @return true wenn das Medium existiert, sonst false.
     * 
     * @require medium != null
     */
    boolean mediumImBestand(Medium medium);

    /**
     * Prüft ob die angebenen Medien existierien. Ein Medium existiert, wenn es
     * im Medienbestand enthalten ist.
     * 
     * @param medien Eine Liste von Medien.
     * @return true wenn die Medien existieren, sonst false.
     * 
     * @require medien != null
     * @require !medien.isEmpty()
     */
    boolean medienImBestand(List<Medium> medien);

    /**
     * Gibt alle Verleihkarten für den angegebenen Kunden zurück.
     * 
     * @param kunde Ein Kunde.
     * @return Alle Verleihkarten des angebenen Kunden. Eine leere Liste, wenn
     *         der Kunde nichts entliehen hat.
     * 
     * @require kundeImBestand(kunde)
     * 
     * @ensure result != null
     */
    List<Verleihkarte> getVerleihkartenFuer(Kunde kunde);

    /**
     * Gibt die Verleihkarte für das angegebene Medium zurück, oder null wenn
     * das Medium nicht verliehen ist.
     * 
     * @param medium Ein Medium.
     * @return Die Verleihkarte für das angegebene Medium.
     * 
     * @require istVerliehen(medium)
     * 
     * @ensure (result != null)
     */
    Verleihkarte getVerleihkarteFuer(Medium medium);

    /**
     * TODO für Blatt 6: löschen
     * 
     * Merkt Medien für einen Kunden vor. Dabei wird für jedes Medium der Kunde
     * als Vormerker auf der jeweiligen Verleihkarte eingetragen.
     * 
     * @param kunde Ein Kunde, für den ein Medium vorgemerkt werden soll
     * @param medien Die Medien, die vorgemerkt werden sollen
     * 
     * @require medienImBestand(medien)
     * @require kundeImBestand(kunde)
     * @require koennenAlleVorgemerktWerdenVon(kunde, medien)
     * 
     * @ensure sindAlleVorgemerktVon(kunde, medien)
     */
    void merkeVor(Kunde kunde, List<Medium> medien)
            throws ProtokollierException;

    /**
     * TODO für Blatt 6: löschen
     * 
     * Entfernt einen Kunden von Vormerklisten. Dabei wird für jedes Medium der
     * Kunde als Vormerker von der jeweiligen Verleihkarte entfernt.
     * 
     * @param kunde Ein Kunde, der von einer Vormerkliste entfernt werden soll
     * @param medien Die Medien
     * 
     * @require medienImBestand(medien)
     * @require kundeImBestand(kunde)
     * @require sindAlleVorgemerktVon(kunde, medien)
     * 
     * @ensure koennenAlleVorgemerktWerdenVon(kunde, medien)
     */
    void entferneVormerker(Kunde kunde, List<Medium> medien)
            throws ProtokollierException;

    /**
     * TODO für Blatt 6: löschen
     * 
     * Überprüft für jedes Medium ob es für den Kunde vorgemerkt werden kann
     * 
     * @param kunde Eine Kunde, für den das Vormerken überprüft werden soll
     * @param medien Die Medien
     * @return true, wenn alle Medien für Kunde vorgemerkt werden können, sonst
     *         false
     * 
     * @require kundeImBestand(kunde)
     * @require medienImBestand(medien)
     */
    boolean koennenAlleVorgemerktWerdenVon(Kunde kunde, List<Medium> medien);

    /**
     * TODO für Blatt 6: löschen
     * 
     * Überprüft für das Medium ob es für den Kunde vorgemerkt werden kann
     * 
     * @param kunde Eine Kunde, für den das Vormerken überprüft werden soll
     * @param medium Das Medium
     * @return true, wenn das Medien für Kunde vorgemerkt werden kann, sonst
     *         false
     * 
     * @require kundeImBestand(kunde)
     * @require medienImBestand(medien)
     */
    boolean kannVorgemerktWerdenVon(Kunde kunde, Medium medium);

    /**
     * TODO für Blatt 6: löschen
     * 
     * Überprüft für den Kunde jedes Medium vorgemerkt ist
     * 
     * @param kunde Eine Kunde, für den das Vormerken überprüft werden soll
     * @param medien Die Medien
     * 
     * @return true, wenn alle Medien von diesem Kunden vorgemerkt sind, sonst
     *         false
     * 
     * @require kundeImBestand(kunde)
     * @require medienImBestand(medien)
     */
    boolean sindAlleVorgemerktVon(Kunde kunde, List<Medium> medien);

    /**
     * TODO für Blatt 6: löschen
     * 
     * Überprüft für das Medium ob es für den Kunde vorgemerkt ist
     * 
     * @param kunde Eine Kunde, für den das Vormerken überprüft werden soll
     * @param medium Das Medium
     * @return true, wenn das Medien für Kunde vorgemerkt ist, sonst false
     * 
     * @require kundeImBestand(kunde)
     * @require mediumImBestand(medium)
     */
    boolean istVorgemerktVon(Kunde kunde, Medium medium);

    /**
     * TODO für Blatt 6: löschen
     * 
     * Überprüft für das Medium bereits vorgemerkt ist (Ob eine Vormerkkarte
     * existiert)
     * 
     * @param medium Das Medium
     * @return true, wenn eine Vormerkkarte existiert, sonst false
     * 
     * @require medienImBestand(medien)
     */
    boolean istVorgemerkt(Medium medium);

    /**
     * TODO für Blatt 6: löschen
     * 
     * Gibt die Vormerkkarte für das übergebene Medium
     * 
     * @param medium Das Medium für das die Vormerkkarte gesucht wird
     * @return Die gesuchte Vormerkkarte
     * 
     * @require medienImBestand(medien)
     * @require istVorgemerkt(medium)
     * 
     * @ensure result != null
     */
    Vormerkkarte getVormerkkarteFuer(Medium medium);

}
