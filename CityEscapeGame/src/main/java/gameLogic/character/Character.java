package gameLogic.character;

import java.awt.Point;

/**
 * Abstract Character class that defines the basic properties and movement methods for a game character.
 * Each character has a location on the map and must implement specific movement behavior.
 * This class provides getters and setters for location and defines abstract movement methods.
 */
public abstract class Character {
    protected Point location;

    /**
     * Constructs a Character with a specified location.
     * @param location The initial location of the character.
     */
    public Character(Point location) {
        this.location = location;
    }
    /**
     * Gets the current location of the character.
     * @return The current location as a Point.
     */
    public Point getLocation() {
        return location;
    }
    /**
     * Sets a new location for the character.
     * @param location The new location as a Point.
     */
    public void setLocation(Point location) {
        this.location = location;
    }

    /**
     * This function checks if the given location overlaps the character's location.
     * @param location: Point the location that needs to be checked for overlapping
     * @return true if the given location overlaps character otherwise false.
     */
    public boolean overLap(Point location) {
        return this.location.equals(location);
    }

}
