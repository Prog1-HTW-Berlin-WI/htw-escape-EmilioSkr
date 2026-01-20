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
 * @author anas
 * @author emilio
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
     * Gibt an, ob die kleine Verschnaufpause genutzt wurde: falsch bedeutet nein, wahr bedeutet ja
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
    public void run(String heroName) {
        if (hero == null) {
            if (heroName == null || heroName.trim().isEmpty()) {
                heroName = "Hero";
            }
            this.hero = new Hero(heroName.trim());
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
            System.out.println("No hero available.");
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
            System.out.println("No hero available.");
            return;
        }

        Lecturer[] signedLecturers = hero.getSignedExerciseLecturers();

        for (Lecturer lecturer : allLecturers) {
            boolean signed = false;
            for (Lecturer signedLecturer : signedLecturers) {
                if (signedLecturer != null && signedLecturer.equals(lecturer)) {
                    signed = true;
                    break;
                }
            }

            String checkbox = signed ? "[x]" : "[ ]";
            System.out.println(checkbox + " " + lecturer.getName());
        }
    }

    /**
     * Fuehrt eine Verschnaufpause durch.
     */
    public void takeRest(String choice) {
        if (hero == null) {
            System.out.println("No hero available.");
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
     * Erkundet den Campus und behandelt zufaellige Ereignisse (Alien treffen, nichts passiert oder Übungsgruppenleiter treffen).
     */
    public void exploreCampus() {
        currentRound++;
        System.out.println("You explore the campus. Round " + currentRound + " of 24.");

        currentRoomIndex = (currentRoomIndex + 1) % rooms.length;
        HTWRoom current = rooms[currentRoomIndex];
        System.out.println("You enter room " + current.getIdentifier() + ": " + current.getDescription());

        Random random = new Random();
        double r = random.nextDouble();

        if (r < 0.20) {
            System.out.println("Nothing unusual happens. You continue your exploration.");
            return;
        } else if (r < 0.72) {
            handleAlienEncounter();
            return;
        } else {
            handleLecturerEncounter(current);
            return;
        }
    }

    /**
     * Initialisiert drei Beispielraeume mit Lecturer-Instanzen, falls noch nicht vorhanden.
     */
    private void initializeRooms() {
        if (rooms[0] != null && rooms[0].getIdentifier() != null) {
            return;
        }

        Lecturer l1 = new Lecturer("Frau Gärtner");
        Lecturer l2 = new Lecturer("Herr Gnaoui");
        Lecturer l3 = new Lecturer("Herr Poeser");
        Lecturer l4 = new Lecturer("Frau Safitri");
        Lecturer l5 = new Lecturer("Frau Vaseva");

        this.allLecturers = new Lecturer[] {l1, l2, l3, l4, l5};

        rooms[0] = new HTWRoom("A214", "Medienunterrichtsraum", l1);
        rooms[1] = new HTWRoom("A143", "Medienunterrichtsraum", l2);
        rooms[2] = new HTWRoom("A142", "Medienunterrichtsraum", l3);
        rooms[3] = new HTWRoom("A143", "Medienunterrichtsraum", l4);
        rooms[4] = new HTWRoom("A236", "Medienunterrichtsraum", l5);
    }

    /**
     * Behandelt eine Begegnung mit einem Uebungsgruppenleiter im aktuellen Raum.
     */
    private void handleLecturerEncounter(HTWRoom room) {
        Lecturer lecturer = room.getLecturer();
        if (lecturer == null) {
            System.out.println("No one is here to sign your slip.");
            return;
        }

        System.out.println("You meet " + lecturer.getName() + ".");
        if (lecturer.isReadyToSign()) {
            hero.signExerciseLeader(lecturer);
            lecturer.sign();
            System.out.println(lecturer.getName() + " signs your slip. Well done!");
            hero.addExperiencePoints(2);
            System.out.println("You gain 2 experience points.");
        } else {
            System.out.println(lecturer.getName() + " already signed earlier.");
        }
    }

    /**
     * Behandelt eine Begegnung mit einem Alien.
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

        if (alien.isFriendly()) {
            System.out.println("The alien seems friendly. Nothing hostile happens.");
            hero.addExperiencePoints(3);
            System.out.println("You feel inspired and gain 3 experience points.");
            return;
        }
    }

    /**
     * Prueft, ob das Spiel vorbei ist (Held besiegt oder Rundenlimit erreicht).
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
