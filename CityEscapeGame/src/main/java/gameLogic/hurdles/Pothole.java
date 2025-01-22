/**
 * Author: Sanika Goyal
 * 2nd year student at SFU
 * Software Systems major
 */

package gameLogic.hurdles;

import java.awt.*;

/**
 * Pothole is a type of hurdle.
 * It ends the game.
 */
public class Pothole extends Hurdle {

    /**
     * This constructor instantiates the pothole object and set its location.
     * @param location: Point - x is row index and y is column index.
     */
    public Pothole(Point location) {
        super(location);
        penalty = -1;
    }

}
