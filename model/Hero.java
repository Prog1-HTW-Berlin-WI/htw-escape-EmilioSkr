package model;

import java.io.Serializable;
import java.util.Random;

/**
 * Spielcharakter (Held) des Spiels.
 * 
 * Verwaltet Lebenspunkte, Erfahrungspunkte, Namen, sowie den Laufzettel mit
 * unterschreibenden Übungsleitungen. Bietet Aktionen wie Regeneration,
 * Angriff, Fluchtversuch und das Eintragen von Unterschriften.
 * 
 */
public class Hero implements Serializable {

    // Bitte serialVersionUID beibehalten, damit die Klasse bei der
    // Speicherung als Datei (Serialisierung) und beim Laden (Deserialisierung)
    // konsistent bleibt und Versionierungsprobleme vermieden werden.
    private static final long serialVersionUID = 3578735620108186013L;
    /**
     * maximale Anzahl an Lebenspunkte
     */
    private static final int MAXHEALTH = 50;
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
        this.healthPoints = MAXHEALTH;
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
     * Ermöglicht dem Helden eine Verschnaufpause.
     * Wenn {@code longRest} wahr ist, regeneriert der Held 10 Lebenspunkte (große Pause),
     * andernfalls 3 Lebenspunkte (kleine Pause, nur einmal möglich).
     * 
     * @param longRest {@code true} für lange Pause, sonst kurze Pause
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

        if (healthPoints > MAXHEALTH) {
            healthPoints = MAXHEALTH;
        }
    }

    /**
     * Versucht aus einer Begegnung mit einem Gegner zu fliehen.
     * 
     * @return {@code true}, wenn die Flucht gelingt; sonst {@code false}
     */
    public boolean flee() {
        // zufallzahl zwischen 0.0 und 1.0
        Random random = new Random();
        // wenn zahl kleiner als 0.42 ist, flucht erfolgreich, sonst nicht
        if (random.nextDouble() < 0.42) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Führt einen Angriff aus und berechnet den Schaden.
     * 13% Fehlschlag (0 Schaden), weitere 12% kritischer Treffer (doppelter Schaden).
     * 
     * @return ganzzahliger berechneter Schaden
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
     * Trägt die angegebene Übungsleitung in den nächsten freien Platz des Laufzettels ein.
     * Jede Person darf nur einmal unterschreiben.
     *
     * @param lecturer Übungsgruppenleitung
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
     * Erhöht die Erfahrungspunkte des Helden um den angegebenen Wert.
     *
     * @param experiencePoints Anzahl der hinzuzufügenden Erfahrungspunkte (positiv)
     */
    public void addExperiencePoints(int experiencePoints) {
        if (experiencePoints > 0) {
            this.experiencePoints += experiencePoints;
        }
    }
    /**
     * Gibt an, ob der Held noch handlungsfähig ist.
     *
     * @return wahr, wenn die Lebenspunkte größer als 0 sind
     */
    public boolean isOperational() {
        return healthPoints > 0;
    }

    /**
     * Liefert den Namen des Helden.
     *
     * @return Name des Helden
     */
    public String getName() {
        return name;
    }

    /**
     * Liefert die aktuellen Lebenspunkte des Helden.
     *
     * @return Lebenspunkte
     */
    public int getHealthPoints() {
        return healthPoints;
    }

    /**
     * Gibt eine Kopie der unterschriebenen Übungsleitungen zurück.
     *
     * @return Array-Kopie der unterschreibenden Leitungen
     */
    public Lecturer[] getSignedExerciseLecturers() {
        return signedExerciseLecturers.clone();
    }
}