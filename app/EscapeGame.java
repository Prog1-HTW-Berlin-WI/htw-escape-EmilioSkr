package app;

import model.Hero;
import model.HostileAlien;
import model.Alien;
import model.FriendlyAlien;
import model.HTWRoom;
import model.Lecturer;

import java.util.Scanner;

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

        while (gameRunning && !gameFinished) {
            printMenu();
            String choice = readUserInput();
            handleMenuChoice(choice);
            System.out.println();
        }
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
     * Liest eine Nutzereingabe ein.
     *
     * @return Eingabezeile
     */
    public void handleMenuChoice(String choice) {
        switch (choice) {
            case "1":
                exploreCampus();
                break;
            case "2":
                showHeroStatus();
                break;
            case "3":
                showSignedSlip();
                break;
            case "4":
                takeRest();
                break;
            case "5":
                System.out.println("Exiting game.");
                // gameFinished = true;
                gameRunning = false;
                break;
            default:
                System.out.println("Invalid input. Please choose between 1 and 5.");
                break;
        }
    }

    /**
     * Platzhalter fuer Erkundung.
     */
    private void exploreCampus() {
        System.out.println("You explore the university. (Content to be implemented.)");
    }

    /**
     * Gibt Statuswerte des Helden aus.
     */
    private void showHeroStatus() {
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
    private void showSignedSlip() {
        if (hero == null) {
            System.out.println("No hero available.");
            return;
        }
        Lecturer[] lecturers = hero.getSignedExerciseLecturers();
        
        String[] names = new String[5];

        names[0] = "Prof1";
        names[1] = "Prof2";
        names[2] = "Prof3";
        names[3] = "Prof4";
        names[4] = "Prof5";


        for (int i = 0; i < lecturers.length; i++) {

            Lecturer lecturer = lecturers[i];
            boolean signed = lecturer != null;
            String checkbox = signed ? "[x]" : "[ ]";
            String label = names[i] ;

            if (signed) {
                System.out.println(checkbox + " " + label + " - " + lecturer.getName());
            } else {
                System.out.println(checkbox + " " + label);
            }
        }
    }

    /**
     * Fuehrt eine Verschnaufpause durch.
     */
    private void takeRest() {
        if (hero == null) {
            System.out.println("No hero available.");
            return;
        }
        int before = hero.getHealthPoints();
        hero.regenerate(false);
        int after = hero.getHealthPoints();
        if (after == before) {
            System.out.println("No small rest available anymore.");
        } else {
            System.out.println("You take a rest. Health is now: " + after);
        }
    }
}
