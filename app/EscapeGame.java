package app;

import model.Hero;
import model.HTWRoom;

/**
 * @author anas
 * @author emilio
 */

public class EscapeGame {
    private final Hero hero;
    private final HTWRoom[] rooms = new HTWRoom[3];
    private boolean gameRunning = true;
    private boolean gameFinished = false;

    /**
     * Konstruktor der Spielumgebung.
     */
    public EscapeGame() {
        this.hero = new Hero();
    }

    /**
     * Ausgabe, ob das Spiel im Moment laeuft.
     */
    public boolean isGameRunning() {
        return gameRunning;
    }

    /**
     * Setzt den Spielstatus (laeuft oder pausiert).
     */
    public void setGameRunning(boolean gameRunning) {
        this.gameRunning = gameRunning;
    }

    /**
     * Ausgabe, ob das Spiel beendet wurde.
     */
    public boolean isGameFinished() {
        return gameFinished;
    }

    /**
     * Setzt den Status, ob das Spiel beendet ist.
     */
    public void setGameFinished(boolean gameFinished) {
        this.gameFinished = gameFinished;
    }

    /**
     * Start der Hauptlogik des Spiels.
     */
    public void run() {
        // System.out.println("The game has started. Or not?");
        System.out.println("Choose a name for your hero:");
        
    }

    /**
     * Gibt den aktuellen Spielcharakter zurueck.
     */
    public Hero getHero() {
        return hero;
    }
}
