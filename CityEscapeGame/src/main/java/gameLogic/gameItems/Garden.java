/**
 * Author: Sanika Goyal
 * 2nd year student at SFU
 * Software Systems major
 */

package gameLogic.gameItems;

import java.awt.*;

/**
 * This class extends Game Item class to be a component of Mapsite
 * Garden is not a path for player or Enemy.
 */
public class Garden extends GameItem {

    /**
     * This constructor can be used to instantiate the garden class.
     * It sets garden to be not a path for player/enemy.
     * It sets the location of garden on the MapSite.
     * @param location: Point - x is row index, y is column index
     */
    public Garden(Point location) {
        super(location);
        path = false;
    }

    /**
     * This function returns the string form of Garden
     * @return String
     */
    @Override
    public String toString() {
        return "Garden" + super.toString();
    }

}
