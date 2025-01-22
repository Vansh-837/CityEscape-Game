package gameLogic.character;

import java.awt.Point;

/**
 * Cop class representing a character that pursues the player (Thief).
 * The Cop can move in four directions (up, down, left, right) by one unit at a time.
 * Inherits basic location properties from the Character superclass.
 */
public class Cop extends Character {
    /**
     * Constructs a Cop with a specified initial location.
     * @param location The starting location of the Cop.
     */
    public Cop(Point location) {
        super(location);
    }
}
