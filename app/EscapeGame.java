package app;

import model.Hero;
import model.HostileAlien;
import model.Alien;
import model.FriendlyAlien;
import model.HTWRoom;
import model.Lecturer;

import java.util.Random;
import java.io.Serializable;

/**
 * Die Klasse EscapeGame stellt die Spiellogik des Spiels dar.
 * 
 * @author Anas
 * @author Emilio
 */

public class EscapeGame implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * Stellt den Spielcharakter des Spielers dar.
     */
    private Hero hero;
    /**
     * Die Raeume des Spiels. Es gibt im Spiel genau fuenf Raeume.
     */
    private final HTWRoom[] rooms = new HTWRoom[5];
    /**
     * Gibt an, ob das Spiel momentan laeuft.
     */
    private boolean gameRunning = true;
    /**
     * Gibt an, ob das Spiel beendet wurde.
     */
    private boolean gameFinished = false;
    /**
     * aktuelle Runde im Spielgeschehen.
     */
    private int currentRound = 0;
    /**
     * maximale Anzahl an Runden.
     */
    private static final int MAXROUNDS = 24;
    /**
     * Index des aktuellen Raums (0..rooms.length-1). Start ist bei -1, aufgrund des Zugriffs
     * auf Indizes (z.B. room[-1+1=0]).
     */
    private int currentRoomIndex = -1;
    /**
     * alle Übungsleiterinstanzen
     */
    private Lecturer[] allLecturers;
    /**
        * Markiert, ob die kleine Verschnaufpause bereits genutzt wurde:
        * 0 bedeutet nein, 1 bedeutet ja.
     */
    private int smallRestUsed;

    /**
     * Konstruktor der Spielumgebung.
     * Initialisiert das Spiel und erstellt einen neuen Spielcharakter.
     */
    public EscapeGame() {
        this.hero = null;
    }

    /**
     * Prueft, ob das Spiel im Moment laeuft.
     * 
     * @return wahr, wenn das Spiel laeuft, sonst falsch
     */
    public boolean isGameRunning() {
        return gameRunning;
    }

    /**
     * Setzt den Spielstatus (laeuft oder pausiert).
     * 
     * @param gameRunning wahr, um das Spiel laufen zu lassen, sonst falsch
     */
    public void setGameRunning(boolean gameRunning) {
        this.gameRunning = gameRunning;
    }

    /**
     * Prueft, ob das Spiel beendet wurde.
     * 
     * @return wahr, wenn das Spiel beendet ist, sonst falsch
     */
    public boolean isGameFinished() {
        return gameFinished;
    }

    /**
     * Setzt den Status, ob das Spiel beendet ist.
     * 
     * @param gameFinished wahr, wenn das Spiel beendet ist, sonst falsch
     */
    public void setGameFinished(boolean gameFinished) {
        this.gameFinished = gameFinished;
    }

    /**
     * Start der Hauptlogik des Spiels.
     * @param heroName Der Name des Helden
     */
    public void run(String heroName) { // hier wird der Held erstellt, wenn noch keiner existiert
        if (hero == null) {
            // wenn kein Name angegeben wurde wird "Hero" als Standardname verwendet
            if (heroName == null || heroName.trim().isEmpty()) { 
                heroName = "Hero";
            }
            // neuen Helden erstellen.
            this.hero = new Hero(heroName.trim());
            // Ausgabe zur Bestätigung der Heldenerstellung
            System.out.println("Hero " + hero.getName() + " created.\n");
        }
        initializeRooms();
    }

    /**
     * Gibt den aktuellen Spielcharakter zurueck.
     * 
     * @return den Spielcharakter des Spiels
     */
    public Hero getHero() {
        return hero;
    }

    /**
     * Zeigt das Spielmenue an.
     */
    public void printMenu() {
        System.out.println("What do you want to do?");
        System.out.println("(1) Explore the university");
        System.out.println("(2) Show hero status");
        System.out.println("(3) Show signed slip");
        System.out.println("(4) Take a rest");
        System.out.println("(5) Exit game");
    }

    /**
     * Gibt Statuswerte des Helden aus.
     */
    public void showHeroStatus() {
        if (hero == null) {
            System.out.println("No Hero available.");
            return;
        }
        System.out.println("Hero: " + hero.getName());
        System.out.println("Health: " + hero.getHealthPoints());
        System.out.println("Experience: " + hero.getExperiencePoints());
    }

    /**
     * Zeigt den Laufzettel mit unterschriebenen Leitungen.
     */
    public void showSignedSlip() {
        if (hero == null) {
            System.out.println("No Hero available.");
            return;
        }

        /** Ein Array mit den unterschriebenen Übungsleitungen des Helden abrufen
         * getSignedExerciseLecturers liefert eine Kopie des Arrays zurück, die 
         * nur die unterschriebenen Übungsleitungen enthält 
         */
        Lecturer[] signedLecturers = hero.getSignedExerciseLecturers();

        // Alle Übungsleitungen durchgehen und anzeigen, ob sie unterschrieben wurden
        for (Lecturer lecturer : allLecturers) {
            // Standardmäßig davon ausgehen, dass die Übungsleitung nicht unterschrieben wurde
            boolean signed = false;
            // Prüfen, ob die aktuelle Übungsleitung im Array der unterschriebenen ist
            for (Lecturer signedLecturer : signedLecturers) {
                // Wenn die Übungsleitung unterschrieben wurde, markieren wir sie als "signed"
                if (signedLecturer != null && signedLecturer.equals(lecturer)) {
                    signed = true;
                    break;
                }
            }

            // Anzeige der Übungsleitung mit einem Häkchen, wenn sie unterschrieben wurde
            String checkbox = signed ? "[x]" : "[ ]";
            System.out.println(checkbox + " " + lecturer.getName());
        }
    }

    /**
     * Führt eine Verschnaufpause durch (lange oder kurze Pause) und passt
     * Lebenspunkte und Rundenstand entsprechend an.
     * 
     * @param choice "1" für lange Verschnaufpause, "2" für kurze Verschnaufpause
     */
    public void takeRest(String choice) {
        if (hero == null) {
            System.out.println("No Hero available.");
            return;
        }

        switch (choice) {
            // Fall: lange Verschnaufpause
            case "1":
                hero.regenerate(true);
                currentRound ++;
                if (checkIfGameOver()) {
                    return;
                }
                System.out.println("You took a long rest. Health is now: " + hero.getHealthPoints());
                System.out.println("Current round is now: " + currentRound);
                break;
            // Fall: kurze Verschnaufpause
            case "2":
                if (smallRestUsed == 1) {
                    System.out.println("Small rest already used. Choose long rest instead.");
                    return;
                }
                 hero.regenerate(false);
                 smallRestUsed = 1;
                 System.out.println("You took a small rest. Health is now: " + hero.getHealthPoints());
                 break;
            // Fall: ungültige Eingabe
            default:
                System.out.println("Invalid rest choice.");
                break;
        }
    }

    /**
     * Erkundet den Campus und behandelt zufällige Ereignisse:
     * nichts, Begegnung mit Alien oder Treffen einer Übungsleitung.
     */
    public void exploreCampus() {
        currentRound++;
        System.out.println("You explore the campus. Round " + currentRound + " of 24.");

        /**
         * Wechselt zum nächsten Raum im Kreis und
         * gibt die Raumbeschreibung aus.
         */
        currentRoomIndex = (currentRoomIndex + 1) % rooms.length;
        HTWRoom current = rooms[currentRoomIndex];
        System.out.println("You enter room " + current.getIdentifier() + ": " + current.getDescription());

        /**
         * Bestimmt ein zufälliges Ereignis:
         * die Zufallszahl zwischen 0.0 und 1.0 entscheidet über das Ereignis.
         */
        Random random = new Random();
        double r = random.nextDouble();

        // wenn r < 0.20: nichts passiert
        if (r < 0.20) {
            System.out.println("Nothing unusual happens. You continue your exploration.");
            return;
            // wenn 0.20 <= r < 0.72: Begegnung mit Alien
        } else if (r < 0.72) {
            handleAlienEncounter();
            return;
            // wenn r >= 0.72: Treffen einer Übungsleitung
        } else {
            handleLecturerEncounter(current);
            return;
        }
    }

    /**
     * Initialisiert fünf Räume mit Übungsleitungs-Instanzen, falls noch nicht vorhanden.
     */
    private void initializeRooms() {
        if (rooms[0] != null && rooms[0].getIdentifier() != null) {
            return;
        }

        // Erstellen der Übungsleitungs-Instanzen
        Lecturer l1 = new Lecturer("Frau Gärtner");
        Lecturer l2 = new Lecturer("Herr Gnaoui");
        Lecturer l3 = new Lecturer("Herr Poeser");
        Lecturer l4 = new Lecturer("Frau Safitri");
        Lecturer l5 = new Lecturer("Frau Vaseva");

        // Speichern aller Übungsleitungs-Instanzen im Spiel
        this.allLecturers = new Lecturer[] {l1, l2, l3, l4, l5};

        // Initialisieren der Räume mit den Übungsleitungs-Instanzen
        rooms[0] = new HTWRoom("A214", "Medienunterrichtsraum", l1);
        rooms[1] = new HTWRoom("A143", "Medienunterrichtsraum", l2);
        rooms[2] = new HTWRoom("A142", "Medienunterrichtsraum", l3);
        rooms[3] = new HTWRoom("A143", "Medienunterrichtsraum", l4);
        rooms[4] = new HTWRoom("A236", "Medienunterrichtsraum", l5);
    }

    /**
     * Behandelt eine Begegnung mit einer Übungsleitungs-Person im übergebenen Raum.
     * 
     * @param room aktueller Raum der Begegnung
     */
    private void handleLecturerEncounter(HTWRoom room) {
        // Übungsleitungs-Instanz aus dem Raum abrufen
        Lecturer lecturer = room.getLecturer();
        if (lecturer == null) {
            System.out.println("No one is here to sign your slip.");
            return;
        }

        System.out.println("You meet " + lecturer.getName() + ".");
        if (lecturer.isReadyToSign()) {
            // Übungsleitung unterschreibt den Laufzettel
            hero.signExerciseLeader(lecturer);
            // Übungsleitung als unterschrieben markieren
            lecturer.sign();
            System.out.println(lecturer.getName() + " signs your slip. Well done!");
            // Erfahrungspunkte vergeben
            hero.addExperiencePoints(2);
            System.out.println("You gain 2 experience points.");
        } else {
            System.out.println(lecturer.getName() + " already signed earlier.");
        }
    }

    /**
     * Behandelt eine Begegnung mit einem Alien (freundlich oder feindlich)
     * und vergibt ggf. Erfahrungspunkte.
     */
    private void handleAlienEncounter() {
        Alien alien;
        Random random = new Random();
        if (random.nextBoolean()) {
            alien = new FriendlyAlien();
        } else {
            alien = new HostileAlien();
        }
        alien.setGreetingText("Hello");
        System.out.println("You encounter an alien: " + alien.getName() + ". " + alien.greeting(hero.getName()));

        //wenn das Alien freundlich ist, passiert nichts feindliches
        if (alien.isFriendly()) {
            System.out.println("The alien seems friendly. Nothing hostile happens.");
            // Erfahrungspunkte vergeben
            hero.addExperiencePoints(3);
            System.out.println("You feel inspired and gain 3 experience points.");
            return;
        }
    }

    /**
     * Prüft, ob das Spiel vorbei ist (Held besiegt oder Rundenlimit erreicht).
     * 
     * @return wahr, wenn das Spiel verloren wurde, sonst falsch
     */
    public boolean checkIfGameOver() {
        if (hero.getHealthPoints() <= 0) {
            System.out.println("Your hero has been defeated. Game over.");
            setGameFinished(true);
            setGameRunning(false);
            return true;
        }
        else if (currentRound >= MAXROUNDS) {
            System.out.println("You have run out of time. Game over.");
            setGameFinished(true);
            setGameRunning(false);
            return true;
        }
        return false;
    }
}
