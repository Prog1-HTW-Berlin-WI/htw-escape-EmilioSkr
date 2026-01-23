package model;

import java.io.Serializable;

/**
 * Abstrakte Basisklasse fuer alle Alien-Typen.
 * 
 * @author Anas
 * @author Emilio
 */
public abstract class Alien implements Serializable {

    // Bitte serialVersionUID beibehalten, damit die Klasse bei der
    // Speicherung als Datei (Serialisierung) und beim Laden (Deserialisierung)
    // konsistent bleibt und Versionierungsprobleme vermieden werden.
    private static final long serialVersionUID = 1729389822767173584L;

    /**
     * Eindeutiger Name des Aliens.
     */
    private final String name;

    /**
     * Aktuelle Lebenspunkte.
     */
    private int lifePoints;

    /**
     * Gibt an, ob das Alien freundlich ist.
     */
    private final boolean friendly;

    /**
     * Begruessungstext des Aliens.
     */
    private String greetingText;

    /**
     * Erstellt ein neues Alien.
     *
     * @param name         Name des Aliens
     * @param lifePoints   Lebenspunkte des Aliens
     * @param friendly     true, wenn freundlich
     * @param greetingText Begruessungstext bei Begegnung
     */
    protected Alien(String name, int lifePoints, boolean friendly, String greetingText) {
        this.name = name;
        this.lifePoints = lifePoints;
        this.friendly = friendly;
        this.greetingText = greetingText;
    }

    /**
     * Reduziert die Lebenspunkte um den angegebenen Schaden.
     * Wenn die Punkte unter 0 fallen, werden sie auf 0 gesetzt.
     * Gibt den verbleibenden Wert auf der Konsole aus.
     *
     * @param amount Schadenswert
     */
    public void takeDamage(int amount) {
        if (amount < 0) {
            return;
        }
        lifePoints -= amount;
        if (lifePoints < 0) {
            lifePoints = 0;
        }
        System.out.println(name + " takes " + amount + " damage and has " + lifePoints + " life points left.");
    }

    /**
     * Gibt an, ob das Alien besiegt ist.
     *
     * @return true, wenn Lebenspunkte 0 oder weniger sind
     */
    public boolean isDefeated() {
        return lifePoints <= 0;
    }

    /**
     * Gibt den Begruessungstext zurueck, optional mit Spielernamen.
     *
     * @param playerName Name des Spielcharakters
     * @return zusammengesetzter Begruessungstext
     */
    public String greeting(String playerName) {
        if (playerName == null || playerName.isEmpty()) {
            return greetingText;
        }
        return greetingText + " " + playerName + "!";
    }
    
    /**
     * Setzt den Begrüßungstext des Aliens.
     * 
     * @param greeting neuer Begrüßungstext
     */
    public void setGreetingText(String greeting) {
        greetingText = greeting;
    }

    /**
     * @return Name des Aliens
     */
    public String getName() {
        return name;
    }

    /**
     * @return aktuelle Lebenspunkte
     */
    public int getLifePoints() {
        return lifePoints;
    }

    /**
     * @return true, wenn freundlich
     */
    public boolean isFriendly() {
        return friendly;
    }
}