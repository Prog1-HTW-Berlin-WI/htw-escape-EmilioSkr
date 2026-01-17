package app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

/**
 * Die Klasse EscapeApp dient zur Anzeige der Spiellogik und regelt die Interaktion zwischen Konsole und Spieler.
 * 
 * @author anas
 * @author emilio
 */
public class EscapeApp {
    /**
     * Dateiname für den Speicherstand.
     */
    public static final String SAVE_FILE_NAME = "save";
    /**
     * Aktuelle Spielinstanz.
     */
    private EscapeGame game;
    /**
     * Gibt an, ob das Spiel momentan läuft.
     */
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

        // Option 2: Nur anzeigen, wenn ein Spiel gestartet wurde UND nicht beendet ist
        if (isGameRunning() && hasSavedGame()) {
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
     * 
     * @return Eingabe, das der Nutzer in die Konsole eingetippt hat
     */
    private String readUserInput() {
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();
        return userInput;
    }

    /**
     * Behandelt die Eingabe.
     * 
     * @param input Eingabe, das der Nutzer in die Konsole eingetippt hat
     */
    private void handleUserInput(String input) {
        switch (input) {
            case "1":
                this.startGame();
                break;
            case "2":
                if (isGameRunning() && hasSavedGame()) {
                    this.resumeGame();
                } else {
                    System.out.println("Invalid input. Please choose a correct number between 1 and 6");
                }
                break;
            case "3":
                if (hasSavedGame()) {
                    this.loadGame();
                } else {
                    System.out.println("Invalid input. Please choose a correct number between 1 and 6");
                }
                break;
            case "4":
                if (isGameRunning()) {
                    this.saveGame();
                } else {
                    System.out.println("Invalid input. Please choose a correct number between 1 and 6");
                }
                break;
            case "5":
                if (hasSavedGame()) {
                    this.deleteGame();
                } else {
                    System.out.println("Invalid input. Please choose a correct number between 1 and 6");
                }
                break;
            case "6":
                System.out.println("Goodbye!");
                System.exit(0);
                break;
            default:
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
     * Setzt das Spiel fort.
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
     * Speichert den aktuellen Stand des Spiels in einer Datei.
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
            return;
        }
        System.out.println("Game saved!");
    }

    /**
     * Prueft, ob das Spiel momentan laeuft.
     * 
     * @return wahr, wenn eine Spielinstanz exisitert, sonst falsch
     */
    private boolean isGameRunning() {
        return game != null;
    }

    /**
     * Ausgabe, ob das Spiel beendet wurde.
     * 
     * @return wahr, wenn Spielinstanz existiert und Spiel beendet wurde
     */
    private boolean isGameFinished() {
        return game != null && game.isGameFinished();
    }


    /**
     * Prueft, ob ein Speicherstand vorhanden ist.
     * 
     * @return wahr, wenn ein Speicherstand vorhanden ist, sonst falsch
     */
    private boolean hasSavedGame() {
        return new File(SAVE_FILE_NAME).exists();
    }

}