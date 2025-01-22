/**
 * Author: Sanika Goyal
 * 2nd year student at SFU
 * Software Systems major
 */

package gameLogic.gameItems;

import java.awt.*;

/**
 * This class provides an abstraction for all the game items of the map site.
 * Any class can extend this class to be a component of map site.
 * subclass can serve as a path for the player/enemy by setting path = true.
 */
public abstract class GameItem {

    Point location;         //x  is row index, y is column index
    boolean path;           //

    /**
     * Subclass can call this constructor to set their location.
     * @param location : x is row index and y is column index
     */
    public GameItem(Point location) {
        this.location = location;
    }

    /**
     * This method returns the location of game item
     * @return location: Point - x is row index and y is column index
     */
    public Point getLocation() {
        return location;
    }

    /**
     * This method sets the location of game item.
     * @param location: Point - x is row index and y is the column index.
     */
    public void setLocation(Point location) {
        this.location = location;
    }

    /**
     * This function returns true if game item can be a path for the player/enemy otherwise returns false.
     * @return path: boolean value - true if it can be a path otherwise false
     */
    public boolean isPath() {
        return path;
    }

    /**
     * This function returns the string form of GameItem
     * @return String
     */
    @Override
    public String toString() {
        return location.toString();
    }
}
