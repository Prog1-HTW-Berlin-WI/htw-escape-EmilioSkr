package app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

/**
 * Start und UI-Klasse der Anwendung.
 * 
 * Verantwortlich für die Anzeige der Menüs, das Einlesen der Nutzereingaben
 * und die Delegation an die Spiel-Logik in {@link EscapeGame} (Starten,
 * Fortsetzen, Speichern, Laden und Löschen von Spielständen).
 * 
 * @author Anas
 * @author Emilio
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
     * Einstiegspunkt der Anwendung. Zeigt das Hauptmenü in einer Schleife an und
     * verarbeitet Nutzereingaben.
     * 
     * @param args nicht verwendet
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
     * Darstellung des Hauptmenüs mit Auswahlmöglichkeiten.
     * Zeigt nur die verfügbaren Optionen basierend auf dem aktuellen Spielzustand an.
     */
    private void showMainMenu() {
        System.out.println("You're in the main menu");
        System.out.println("What do you want to do next?");
        System.out.println("(1) Start new game");

        // Option 2: Nur anzeigen, wenn ein Spiel gestartet wurde UND nicht beendet ist
        if (isGameRunning() && !isGameFinished()) {
            System.out.println("(2) Resume game");
        }

        // Option 3: Nur anzeigen, wenn ein gespeichertes Spiel vorhanden ist
        if (hasSavedGame() && !isGameFinished()) {
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
     * Liest eine komplette Zeile von der Standard-Eingabe.
     * 
     * @return Eingabe, das der Nutzer in die Konsole eingetippt hat
     */
    private String readUserInput() {
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();
        return userInput;
    }

    /**
     * Verarbeitet die Eingabe aus dem Hauptmenü und führt die entsprechende Aktion aus.
     * 
     * Gültige Optionen hängen vom aktuellen Spielzustand ab (z. B. nur speichern,
     * wenn bereits ein Spiel läuft).
     * 
     * @param input die ausgewählte Menüoption ("1" bis "6")
     */
    private void handleUserInput(String input) {
        switch (input) {
            case "1":
                this.startGame();
                break;
            case "2":
                if (isGameRunning() && !isGameFinished()) {
                    this.resumeGame();
                } else {
                    System.out.println("Invalid input. Please choose a correct number between 1 and 6");
                }
                break;
            case "3":
                if (hasSavedGame() && !isGameFinished()) {
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
     * Verarbeitet eine Auswahl innerhalb des Spielmenüs (während ein Spiel läuft).
     * 
     * @param choice die ausgewählte Option ("1" bis "5")
     */
    public void handleMenuChoice(String choice) {
        switch (choice) {
            case "1":
                this.game.exploreCampus();
                break;
            case "2":
                this.game.showHeroStatus();
                break;
            case "3":
                this.game.showSignedSlip();
                break;
            case "4":
                handleRest();
                break;
            case "5":
                System.out.println("Exiting game.");
                // gameFinished = true;
                this.game.setGameRunning(false);
                break;
            default:
                System.out.println("Invalid input. Please choose between 1 and 5.");
                break;
        }
    }

    /**
     * Startet ein neues Spiel: erstellt die Spielinstanz, fragt den Heldennamen ab
     * und übergibt diesen an die Spiel-Logik.
     */
    private void startGame() {
        this.game = new EscapeGame();
        System.out.println("Game started!");
        System.out.println("Choose a name for your hero:");
        String heroName = readUserInput();
        this.game.run(heroName);
        resumeGame();
    }

    /**
     * Setzt das Spiel fort, zeigt wiederholt das Spielmenü an und verarbeitet die
     * Spielauswahl, solange das Spiel läuft.
     */
    private void resumeGame() {
        if (this.game != null) {
            this.game.setGameRunning(true);
            while (this.game.isGameRunning()) {
                this.game.checkIfGameOver();
                this.game.printMenu();
                String choice = readUserInput();
                this.handleMenuChoice(choice);
                System.out.println();
            }
        }
    }

    /**
     * Löscht den vorhandenen Speicherstand ({@link #SAVE_FILE_NAME}).
     * Gibt das Ergebnis der Operation auf der Konsole aus.
     */
    private void deleteGame() {
        if (new File(SAVE_FILE_NAME).delete()) {
            System.out.println("Deleted save file");
        } else {
            System.out.println("No save file found to delete");
        }
    }

    /**
     * Speichert den aktuellen Stand des Spiels in eine Datei ({@link #SAVE_FILE_NAME})
     * mittels Java-Serialisierung.
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
     * Lädt den Spielstand aus der Datei ({@link #SAVE_FILE_NAME}) mittels
     * Java-Deserialisierung und stellt die Spielinstanz wieder her.
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
    }

    /**
     * Behandelt die Eingabe zur Verschnaufpause im laufenden Spiel.
     * ("1" bedeutet lange Verschnaufpause, "2" bedeutet kurze Verschnaufpause).
     */
    private void handleRest() {
        System.out.println("Choose rest type:");
        System.out.println("(1) Long rest");
        System.out.println("(2) Short rest");
        System.out.println();
        String restChoice = readUserInput();
        this.game.takeRest(restChoice);
    }

    /**
     * Prüft, ob es eine aktive Spielinstanz gibt.
     * 
     * @return wahr, wenn eine Spielinstanz existiert, sonst falsch
     */
    private boolean isGameRunning() {
        return game != null;
    }

    /**
     * Prüft, ob das aktuelle Spiel bereits beendet wurde.
     * 
     * @return wahr, wenn Spielinstanz existiert und Spiel beendet wurde
     */
    private boolean isGameFinished() {
        return game != null && game.isGameFinished();
    }


    /**
     * Prüft, ob ein Speicherstand vorhanden ist.
     * 
     * @return wahr, wenn ein Speicherstand vorhanden ist, sonst falsch
     */
    private boolean hasSavedGame() {
        return new File(SAVE_FILE_NAME).exists();
    }

}