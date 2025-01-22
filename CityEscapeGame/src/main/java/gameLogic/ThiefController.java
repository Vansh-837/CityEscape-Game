package gameLogic;

import gameLogic.map.Direction;
import gameLogic.map.Map;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * This class is responsible for listening arrow keys and is required for thief movement
 */
public class ThiefController implements KeyListener {
    Map map;

    public ThiefController(Map map) {
        this.map = map;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * This function listen to the arrow keys and initiate thief movement using map class
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                map.moveThief(Direction.Left);
                break;
            case KeyEvent.VK_RIGHT:
                map.moveThief(Direction.Right);
                break;
            case KeyEvent.VK_UP:
                map.moveThief(Direction.Up);
                break;
            case KeyEvent.VK_DOWN:
                map.moveThief(Direction.Down);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
