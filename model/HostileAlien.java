package model;

/**
 * Feindliches Alien mit hoeheren Lebenspunkten.
 */
public class HostileAlien extends Alien {

    /**
     * Erstellt ein feindliches Alien mit Standardwerten.
     */
    public HostileAlien() {
        super("Hostile Alien", 35, false, "Prepare to fight");
    }
}
