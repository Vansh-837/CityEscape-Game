/**
 * Author: Sanika Goyal
 * 2nd year student at SFU
 * Software Systems major
 */

package gameLogic.hurdles;

import java.awt.*;
/**
 * Spike is a type of hurdle.
 * It decreases the time player has to complete the game.
 */
public class Spike extends Hurdle {
    /**
     * This constructor instantiates the spike object and set its location.
     * @param location: Point - x is row index and y is column index.
     */
    public Spike(Point location) {
        super(location);
        penalty = 60;
    }

}
