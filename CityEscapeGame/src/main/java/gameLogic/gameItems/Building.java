/**
 * Author: Sanika Goyal
 * 2nd year student at SFU
 * Software Systems major
 */

package gameLogic.gameItems;

import java.awt.*;
/**
 * This class extends Game Item class to be a component of MapSite
 * Building is not a path for player or Enemy.
 */
public class Building extends GameItem {
    /**
     * This constructor can be used to instantiate the building class.
     * It sets building to be not a path for player/enemy.
     * It sets the location of garden on the MapSite.
     * @param location: Point - x is row index, y is column index
     */
    public Building(Point location) {
        super(location);
        path = false;
    }

    @Override
    public String toString() {
        return "Building" + super.toString();
    }
}
