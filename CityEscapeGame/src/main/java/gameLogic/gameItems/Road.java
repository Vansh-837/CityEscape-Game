/**
 * Author: Sanika Goyal
 * 2nd year student at SFU
 * Software Systems major
 */

package gameLogic.gameItems;

import java.awt.*;
/**
 * This class extends Game Item class to be a component of MapSite
 * Road is a path for player or Enemy.
 * Road can also have rewards on it for players to collect.
 * Road can also have hurdles to obstruct player from winning the game.
 */
public class Road extends GameItem {
    /**
     * This constructor can be used to instantiate the garden class.
     * It sets road to be a path for player/enemy.
     * It sets the location of road on the MapSite.
     * @param location: Point - x is row index, y is column index
     */
    public Road(Point location) {
        super(location);
        path = true;
    }

    /**
     * This function returns the string form of Road
     * @return String
     */
    @Override
    public String toString() {
        return "Road" + super.toString();
    }
}
