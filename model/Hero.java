package model;

import java.io.Serializable;
import java.util.Random;

public class Hero implements Serializable {

    // Bitte serialVersionUID beibehalten, damit die Klasse bei der
    // Speicherung als Datei (Serialisierung) und beim Laden (Deserialisierung)
    // konsistent bleibt und Versionierungsprobleme vermieden werden.
    private static final long serialVersionUID = 3578735620108186013L;
    /**
     * maximale Anzahl an Lebenspunkte
     */
    private static final int maxHealth = 50;
    /**
     * Name des Spielcharakters
     */
    private String name;
    /**
     * Aktuelle Lebenspunkte des Spielcharakters.
     */
    private int healthPoints;
    /**
     * Aktuelle Anzahl an Erfahrungspunkte des Spielcharakters.
     */
    private int experiencePoints;
    /**
    * Laufzettel mit den Übungsgruppenleiter*innen, die den Besuch
     * des Spielcharakters bestaetigt haben.
     */
    private Lecturer[] signedExerciseLecturers;
    /**
     * Gibt an, ob die kleine Verschnaufpause genutzt wurde: wahr bedeutet ja, falsch bedeutet nein
     */
    private boolean smallRestUsed;

    /**
     * Konstruktor des Helden.
     * Erstellt einen neuen Helden mit dem angegebenen Namen.
     * Die Lebenspunkte starten bei 50 und die Erfahrungspunkte bei 0.
     * 
     * @param name Name des Spielcharakters
     */
    public Hero(String name) {
        this.name = name;
        this.healthPoints = maxHealth;
        this.experiencePoints = 0;
        this.signedExerciseLecturers = new Lecturer[5];
    }

    /**
     * Reduziert die Lebenspunkte um den angegebenen Wert.
     * 
     * @param amount Schadenshoehe
     */
    public void takeDamage(int amount) {
        if (amount < 0) {
            return;
        }
        healthPoints -= amount;
        if (healthPoints < 0) {
            healthPoints = 0;
        }
    }
    /**
     * Ermoeglicht dem Helden eine Verschnaufpause.
     * Falls longRest wahr ist, dann hat der Held eine große Verschnaufpause und regeneriert 10 Lebenspunkte.
     * Falls longRest falsch ist, dann hat der Held eine kleine Verschnaufpause und regeneriert 3 Lebenspunkte.
     * 
     * @param longRest
     */
    public void regenerate(boolean longRest) {
        if (longRest) {
            healthPoints += 10;
        } else {
            if (smallRestUsed) {
                return;
            }
            healthPoints += 3;
            smallRestUsed = true;
        }

        if (healthPoints > maxHealth) {
            healthPoints = maxHealth;
        }
    }

    /**
     * Versucht aus einer Begegnung mit dem Gegner zu fliehen.
     * Falls es einen Erfolg gibt, dann wird wahr ausgegeben, sonst falsch.
     * 
     * @return boolean, ob der Fluchtversuch erfolgreich war, wahr bedeutet ja, falsch bedeutet nein
     */
    public boolean flee() {
        Random random = new Random();
        if (random.nextDouble() < 0.42) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Fuehrt einen Angriff auf ein feindliches Wesen aus.
     * 13% Fehlschlag: Schaden 0.
     * 12% kritischer Treffer: Doppelter Schaden
     * 
     * @return Ganzzahliger berechneter Schaden
     */
     public int attack() {
        double baseDamage = experiencePoints * 2.3 + 1;
        Random random = new Random();
        double chance = random.nextDouble();

        if (chance < 0.13) {
            return 0;
        } else if (chance < 0.25) {
            baseDamage *= 2;
        }

        return (int) baseDamage;
    }
    /**
     * Traegt den angegebenen Übungsgruppenleiter in den naechsten freien
     * Platz des Laufzettels ein. Jeder Leiter darf nur einmal unterschreiben.
     *
    * @param lecturer Übungsgruppenleiter
     */
    public void signExerciseLeader(Lecturer lecturer) {
        if (lecturer == null) {
            return;
        }

        for (Lecturer l : signedExerciseLecturers) {
            if (lecturer.equals(l)) {
                return;
            }
        }

        for (int i = 0; i < signedExerciseLecturers.length; i++) {
            if (signedExerciseLecturers[i] == null) {
                signedExerciseLecturers[i] = lecturer;
                return;
            }
        }
    }

    /**
     * Gibt die aktuellen Erfahrungspunkte des Helden zurück.
     *
     * @return Erfahrungspunkte
     */
    public int getExperiencePoints() {
        return experiencePoints;
    }

    /**
     * Erhoeht die Erfahrungspunkte des Helden um den angegebenen Wert.
     *
     * @param experiencePoints Anzahl der hinzuzufuegenden Erfahrungspunkte
     */
    public void addExperiencePoints(int experiencePoints) {
        if (experiencePoints > 0) {
            this.experiencePoints += experiencePoints;
        }
    }
    /**
     * Gibt an, ob der Held noch handlungsfaehig ist.
     *
     * @return wahr, wenn die Lebenspunkte groeßer als 0 sind
     */
    public boolean isOperational() {
        return healthPoints > 0;
    }
}