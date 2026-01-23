package model;

import java.io.Serializable;

/**
 * Repraesentiert eine Übungsgruppenleiterin bzw. einen Übungsgruppenleiter,
 * die bzw. der den Laufzettel unterschreiben kann.
 * 
 * @author Anas
 * @author Emilio
 */
public class Lecturer implements Serializable {

    // Bitte serialVersionUID beibehalten, damit die Klasse bei der
    // Speicherung als Datei (Serialisierung) und beim Laden (Deserialisierung)
    // konsistent bleibt und Versionierungsprobleme vermieden werden.
    private static final long serialVersionUID = 540082607047283589L;

    /**
     * Name der Übungsgruppenleiterin bzw. des Übungsgruppenleiters.
     */
    private final String name;

    /**
     * Kennzeichnet, ob der Laufzettel bereits unterschrieben wurde.
     */
    private boolean hasSigned;

    /**
     * Erstellt eine neue Instanz mit dem angegebenen Namen.
     *
     * @param name Name der Übungsgruppenleiterin bzw. des Übungsgruppenleiters
     */
    public Lecturer(String name) {
        this.name = name;
    }

    /**
     * Gibt an, ob die Uebungsgruppenleiterin bzw. der Uebungsgruppenleiter bereit ist,
     * den Laufzettel zu unterschreiben. Hier gilt: noch nicht unterschrieben bedeutet bereit.
     *
     * @return wahr, wenn noch nicht unterschrieben wurde
     */
    public boolean isReadyToSign() {
        return !hasSigned;
    }

    /**
     * Markiert den Laufzettel als unterschrieben.
     */
    public void sign() {
        hasSigned = true;
    }

    /**
     * Liefert den Namen der Übungsgruppenleiterin bzw. des Übungsgruppenleiters.
     *
     * @return Name
     */
    public String getName() {
        return name;
    }

    /**
     * Prueft, ob bereits unterschrieben wurde.
     *
     * @return wahr, wenn der Laufzettel unterschrieben wurde
     */
    public boolean hasSigned() {
        return hasSigned;
    }
}