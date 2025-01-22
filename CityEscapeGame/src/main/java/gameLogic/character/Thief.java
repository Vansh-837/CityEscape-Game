package gameLogic.character;

import java.awt.Point;

/**
 * Thief class representing a character controlled by the player.
 * The Thief can move in four directions (up, down, left, right) by one unit at a time.
 * Inherits basic location properties from the Character superclass.
 */
public class Thief extends Character {
    /**
     * Constructs a Thief with a specified initial location.
     * @param location The starting location of the Thief.
     */
    public Thief(Point location) {
        super(location);
    }

}