/**
 * Author: Sanika Goyal
 * 2nd year student at SFU
 * Software Systems major
 */

package gameLogic.hurdles;
import java.awt.*;

/**
 * This is an abstraction class for hurdles.
 * Class extending this class can override getPenalty method to impose
 * different penalty on player when it hits that hurdle.
 */
public abstract class Hurdle {
    Point location;         //x is row index and y is column index
    boolean active;         //represent the active status of hurdle
    int penalty;            //represent the penalty of hitting a hurdle

    /**
     * This constructor can be used by subclasses to set their location.
     * @param location: Point - x is row index and y is column index.
     */
    public Hurdle(Point location) {
        this.location = location;
        this.active = true;
    }

    /**
     * This function provides the location of the hurdle
     * @return location: Point - x is row index and y is column index
     */
    public Point getLocation() {
        return location;
    }

    /**
     * This function sets the location of the reward.
     * @param location: Point - x is row index and y is column index.
     */
    public void setLocation(Point location) {
        this.location = location;
    }

    /**
     * This function provides the active status of the hurdle.
     * @return boolean: true if hurdle is active, false otherwise
     */
    public boolean isActive(){
        return active;
    }

    /**
     * This function makes the hurdle inactive
     */
    public void setInactive(){
        this.active = false;
    }

    /**
     * This function returns the penalty value of the hurdle.
     * @return penalty : int.
     */
    public int getPenalty(){
        return penalty;
    }
}
