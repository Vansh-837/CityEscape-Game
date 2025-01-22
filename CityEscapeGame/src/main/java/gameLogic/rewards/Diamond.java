/**
 * Author: Sanika Goyal
 * 2nd year student at SFU
 * Software Systems major
 */

package gameLogic.rewards;

import java.awt.*;

/**
 * Diamond is general reward.
 */
public class Diamond extends Reward {
    private static int count;
    /**
     * This constructor can be used to instantiate diamond.
     * @param location: Point - x is row index and y is column index
     */
    public Diamond(Point location) {
        super(location);
        rewardValue = -1;
        active = true;
    }
    /**
     * This function marks the diamond to be collected.
     */
    @Override
    public void collect(){
        if(active){
            super.collect();
            count++;
        }
    }

    /**
     * This static method provides the count of Diamond collected.
     * @return count : number of Diamonds collected
     */
    public static int getCount(){
        return count;
    }


}
