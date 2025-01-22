/**
 * Author: Sanika Goyal
 * 2nd year student at SFU
 * Software Systems major
 */

package gameLogic.rewards;

import java.awt.*;

/**
 * Nitro is special reward, it blinks and is available for only limited time.
 * It increases the time player has to complete the game.
 */
public class Nitro extends Reward {
    private static int count;
    /**
     * This constructor can be used to instantiate nitro.
     * @param location: Point - x is row index and y is column index
     */
    public Nitro(Point location) {
        super(location);
        rewardValue = 60;
        active = false;
    }

    /**
     * This function adds the special functionality of increasing the car speed of the player.
     */
    @Override
    public void collect(){
        if(active) {
            super.collect();
            count++;
        }
    }

    /**
     * This static method provides the count of Nitros collected.
     * @return count : number of Nitros collected
     */
    public static int getCount(){
        return count;
    }
}
