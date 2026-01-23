package model;

import java.io.Serializable;

/**
 * Repraesentiert einen Raum innerhalb der HTW.
 * 
 * @author Anas
 * @author Emilio
 */
public class HTWRoom implements Serializable {

    // Bitte serialVersionUID beibehalten, damit die Klasse bei der
    // Speicherung als Datei (Serialisierung) und beim Laden (Deserialisierung)
    // konsistent bleibt und Versionierungsprobleme vermieden werden.
    private static final long serialVersionUID = 9065680017147292999L;

    /**
     * Eindeutiger Bezeichner des Raums (z. B. "A210").
     */
    private final String identifier;

    /**
     * Beschreibung des Raums.
     */
    private final String description;

    /**
     * Übungsgruppenleiter*in, die bzw. der sich in diesem Raum befindet (kann null sein).
     */
    private Lecturer lecturer;

    /**
     * Erstellt einen neuen Raum mit Bezeichner, Beschreibung und optionaler Leitung.
     *
     * @param identifier  eindeutiger Bezeichner
     * @param description Beschreibung des Raums
     * @param lecturer    Übungsgruppenleiter*in im Raum oder null
     */
    public HTWRoom(String identifier, String description, Lecturer lecturer) {
        this.identifier = identifier;
        this.description = description;
        this.lecturer = lecturer;
    }

    /**
     * Liefert den eindeutigen Bezeichner des Raums.
     *
     * @return Bezeichner
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Liefert die Beschreibung des Raums.
     *
     * @return Beschreibung
     */
    public String getDescription() {
        return description;
    }

    /**
    * Liefert die im Raum befindliche Übungsgruppenleitung.
     *
     * @return aktuelle Übungsgruppenleitung oder null
     */
    public Lecturer getLecturer() {
        return lecturer;
    }

    /**
     * Setzt die Übungsgruppenleitung für diesen Raum (kann null sein, falls niemand anwesend).
     *
     * @param lecturer neue Übungsgruppenleitung oder null
     */
    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }
}
