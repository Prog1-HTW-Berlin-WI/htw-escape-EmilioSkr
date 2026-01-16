package app;

import model.Hero;
import model.HTWRoom;
import model.Lecturer;

import java.util.Scanner;

/**
 * Die Klasse EscapeGame stellt die Spiellogik des Spiels dar.
 * 
 * @author anas
 * @author emilio
 */

public class EscapeGame {
    /**
     * Stellt den Spielcharakter des Spielers dar.
     */
    private Hero hero;
    /**
     * Die Raeume des Spiels. Es gibt im Spiel genau drei Raeume.
     */
    private final HTWRoom[] rooms = new HTWRoom[3];
    /**
     * Gibt an, ob das Spiel momentan laeuft.
     */
    private boolean gameRunning = true;
    /**
     * Gibt an, ob das Spiel beendet wurde.
     */
    private boolean gameFinished = false;

    /**
     * Scanner fuer Nutzereingaben.
     */
    private final Scanner scanner = new Scanner(System.in);

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
     */
    public void run() {
        if (hero == null) {
            System.out.println("Choose a name for your hero:");
            String name = readUserInput();
            if (name == null || name.trim().isEmpty()) {
                name = "Hero";
            }
            this.hero = new Hero(name.trim());
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
    private String readUserInput() {
        return scanner.nextLine();
    }

    /**
     * Zeigt das Spielmenue an.
     */
    private void printMenu() {
        System.out.println("What do you want to do?");
        System.out.println("(1) Explore the university");
        System.out.println("(2) Show hero status");
        System.out.println("(3) Show signed slip");
        System.out.println("(4) Take a rest");
        System.out.println("(5) Exit game");
    }

    /**
     * Reagiert auf die Nutzerauswahl aus dem Menue.
     *
     * @param choice Auswahlstring
     */
    private void handleMenuChoice(String choice) {
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
                gameFinished = true;
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
