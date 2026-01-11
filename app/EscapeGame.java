package app;

import model.Hero;
import model.HTWRoom;

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
    private final Hero hero;
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
     * Konstruktor der Spielumgebung.
     * Initialisiert das Spiel und erstellt einen neuen Spielcharakter.
     */
    public EscapeGame() {
        this.hero = new Hero();
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
        // System.out.println("The game has started. Or not?");
        System.out.println("Choose a name for your hero:");
        
    }

    /**
     * Gibt den aktuellen Spielcharakter zurueck.
     * 
     * @return den Spielcharakter des Spiels
     */
    public Hero getHero() {
        return hero;
    }
}
