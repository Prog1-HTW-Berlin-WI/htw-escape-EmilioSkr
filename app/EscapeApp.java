package app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

/**
 * @author anas
 * @author emilio
 */
public class EscapeApp {

    public static final String SAVE_FILE_NAME = "save";
    private EscapeGame game;
    private boolean gameRunning = true;

    /**
     * Startet das Spiel und initialisiert die Spielumgebung.
     */
    public static void main(String[] args) {
        System.out.println("Welcome to the HTW escape");
        System.out.println("========================================\n");

        EscapeApp app = new EscapeApp();

        while (true) {
            app.showMainMenu();
            String choice = app.readUserInput();
            app.handleUserInput(choice);
            System.out.println("====================");
        }
    }

    /**
     * Darstellung des Hauptmenues mit Auswahlmöglichkeiten.
     * Zeigt nur die verfügbaren Optionen basierend auf dem Spielzustand an.
     */
    private void showMainMenu() {
        System.out.println("You're in the main menu");
        System.out.println("What do you want to do next?");
        System.out.println("(1) Start new game");

        // Option 2: Nur anzeigen, wenn ein Spiel gestartet wurde
        if (isGameRunning()) {
            System.out.println("(2) Resume game");
        }

        // Option 3: Nur anzeigen, wenn ein gespeichertes Spiel vorhanden ist
        if (hasSavedGame()) {
            System.out.println("(3) Load game");
        }

        // Option 4: Nur anzeigen, wenn ein Spiel gestartet wurde
        if (isGameRunning()) {
            System.out.println("(4) Save game");
        }

        // Option 5: Nur anzeigen, wenn ein gespeichertes Spiel vorhanden ist
        if (hasSavedGame()) {
            System.out.println("(5) Delete game");
        }

        System.out.println("(6) Quit");
        System.out.println("");
        System.out.println("Please choose a number between 1 and 6: ");
    }

    /**
     * Liest die Eingabe und gibt sie wieder aus.
     */
    private String readUserInput() {
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();
        return userInput;
    }

    /**
     * Behandelt die Eingabe.
     */
    private void handleUserInput(String input) {
        switch (input) {
            case "1":
                this.startGame();
                break;
            case "2":
                // Prüfen, ob ein Spiel aktuell läuft
                if (isGameRunning()) {
                    this.resumeGame();
                } else {
                    // Falls kein Spiel läuft: Fehlermeldung anzeigen
                    System.out.println("Invalid input. Please choose a correct number between 1 and 6");
                }
                break;
            case "3":
                // Prüfen, ob ein gespeicherter Spielstand existiert
                if (hasSavedGame()) {
                    this.loadGame();
                } else {
                    // Falls kein Spielstand vorhanden ist: Fehlermeldung anzeigen
                    System.out.println("Invalid input. Please choose a correct number between 1 and 6");
                }
                break;
            case "4":
                // Prüfen, ob ein Spiel aktuell läuft
                if (isGameRunning()) {
                    this.saveGame();
                } else {
                    // Falls kein Spiel läuft: Fehlermeldung anzeigen
                    System.out.println("Invalid input. Please choose a correct number between 1 and 6");
                }
                break;
            case "5":
                // Prüfen, ob ein gespeicherter Spielstand existiert
                if (hasSavedGame()) {
                    this.deleteGame();
                } else {
                    // Falls kein Spielstand vorhanden ist: Fehlermeldung anzeigen
                    System.out.println("Invalid input. Please choose a correct number between 1 and 6");
                }
                break;
            case "6":
                System.out.println("Goodbye!");
                System.exit(0);
                break;
            default:
                // Falls eine ungültige Eingabe getätigt wurde
                System.out.println("Invalid input. Please choose a correct number between 1 and 6");
                break;
        }
    }

    /**
     * Initialisiert die Spielumgebung.
     */
    private void startGame() {
        this.game = new EscapeGame();
        System.out.println("Game started!");
        resumeGame();
    }

    /**
     * Beendet die Pause und fuehrt das Spiel weiter.
     */
    private void resumeGame() {
        if (this.game != null) {
            this.game.setGameRunning(true);
            this.game.run();
        }
    }

    /**
     * Loescht den Speicherstand.
     */
    private void deleteGame() {
        if (new File(SAVE_FILE_NAME).delete()) {
            System.out.println("Deleted save file");
        } else {
            System.out.println("No save file found to delete");
        }
    }

    /**
     * Speichert den aktuellen Stand des Spiels.
     */
    private void saveGame() {
        if (this.game == null) {
            System.out.println("No game to save!");
            return;
        }

        try (FileOutputStream fos = new FileOutputStream(SAVE_FILE_NAME);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(game);
            oos.flush();
            System.out.println("Save game file");
        } catch (Exception ex) {
            System.err.println("Something went wrong while saving the game: " + ex.getMessage());
        }
    }

    /**
     * Laedt das Spiel mithilfe des Speicherstands.
     */
    private void loadGame() {
        try (FileInputStream fis = new FileInputStream(SAVE_FILE_NAME);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            this.game = (EscapeGame) ois.readObject();
            System.out.println("Load save file");
        } catch (Exception ex) {
            System.err.println("Something went wrong while loading the game: " + ex.getMessage());
        }
    }

    /**
     * Ausgabe, ob das Spiel momentan laeuft.
     */
    private boolean isGameRunning() {
        return game != null;
    }

    /**
     * Ausgabe, ob ein Speicherstand vorhanden ist.
     */
    private boolean hasSavedGame() {
        return new File(SAVE_FILE_NAME).exists();
    }

}