/**
 * Author: Sanika Goyal
 * 2nd year student at SFU
 * Software Systems major
 */

package gameLogic.rewards;

import java.awt.*;

/**
 * This is an abstraction for rewards.
 * Class extending reward can override collect method to set specific
 * functionality for the reward that player should receive upon collecting it.
 */
public abstract class Reward {

    Point location;             //x is row index and y is column index
    boolean collected;
    boolean active;
    int rewardValue;


    /**
     * This constructor can be used by subclasses to set their location.
     * @param location: Point - x is row index and y is column index.
     */
    public Reward(Point location) {
        this.location = location;
    }

    /**
     * This function provides the location of the reward
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
     * This function provides the collected status of the reward.
     * @return boolean: true if reward has been collected, false otherwise
     */
    public boolean isCollected() {
        return collected;
    }

    /**
     * This function marks the reward to be collected.
     * Subclass can override this method to implement special reward benefits.
     */
    public void collect(){
        collected = true;
    }

    /**
     * This function implies if the reward is active or not.
     * @return true: if reward is active, otherwise false.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * This function sets the active status of the reward to true or false.
     * @param active: true activates the reward, false deactivates the reward.
     */
    public void setActiveStatus(boolean active) {
        this.active = active;
    }

    /**
     * This function provides the reward value.
     * @return int: Reward value of the reward
     */
    public int getRewardValue() {
        return rewardValue;
    }
}
