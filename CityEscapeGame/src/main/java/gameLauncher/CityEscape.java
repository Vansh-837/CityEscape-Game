/**
 * Author: Sanika Goyal
 * Edited by: Vansh and Andy
 * 2nd year student at SFU
 * Software Systems major
 */

package gameLauncher;
import gameLogic.ThiefController;
import gameLogic.map.Map;
import gameLogic.rewards.Diamond;
import gameUI.GamePanel;
import gameUI.MainScreenPanel;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class launches the City Escape game, it communicates with
 * logic and UI in timely fashion to maintain smooth operation.
 */
public class CityEscape {

    static final int screenWidth = 1268;
    static final int screenHeight = 776;

    static Map map;
    static final int totalTime = 300;
    static int remainingSec = totalTime;
    static int elapsedSec = 0;
    static final Point endPoint = new Point(45, 55);
    static String filename = "map3.txt";

    /**
     * This method is responsible for managing control flow of the game.
     * @param args default
     */
    public static void main(String[] args) {
        //Initialising map
        map = new Map(filename);
        GamePanel gamePanel = new GamePanel(map);

        //manage the switching of main screen panel with game screen panel.
        setUpGameWindow(gamePanel); 

        //Add Key Listener to the game panel to change the thief's direction on the map.
        ThiefController thiefController = new ThiefController(map);
        gamePanel.addKeyListener(thiefController);

        startGameTimer();   //Start the game timer

        //Update UI every 100 millisecond.
        Timer UpdateUITimer = new Timer(100, e -> {
                updateTimerWithRewardAndHurdle();
                manageNitroActivation();
                gamePanel.repaint();                            //Repaint Ui
                gamePanel.updateTimerDisplay(remainingSec);     //Update timer display
                processGameEnd(gamePanel);
        });
      
        // Timer for moving the cops every 600 milliseconds
        Timer copMoveTimer = new Timer(600, e -> {
             map.moveCopsTowardThief();  // Move the cops toward the thief
             if (map.getCop1().getLocation().equals(map.getThief().getLocation()) ||
             map.getCop2().getLocation().equals(map.getThief().getLocation())) {
                loseGameByCopCatch(gamePanel, UpdateUITimer);
            }
        });
        
        startTimerWithDelay(2000,UpdateUITimer);      // Delay of 2 seconds for the game timer
        startTimerWithDelay(2000,copMoveTimer);       // Delay of 2 seconds for the cop movement timer
    }

    /**
    * This function create the window and add manages the switching
    * of main screen panel with game screen panel.
    * @param gamePanel: Game screen panel responsible for actual game display
    */
    public static void setUpGameWindow(GamePanel gamePanel) {

        //Creating window
        JFrame window = new JFrame("City Escape Game");
        CardLayout cardLayout = new CardLayout();
        JPanel container = new JPanel(cardLayout);

        //Creating Main screen panel
        MainScreenPanel mainScreenPanel = new MainScreenPanel(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(container, "Game");     //switch to game screen
                gamePanel.requestFocusInWindow();
            }
        });

        //Adding both main and game screen to JFrame container
        container.add(mainScreenPanel, "Main");
        container.add(gamePanel, "Game");
        window.add(container);

        //Start with main screen panel
        cardLayout.show(container, "Main");
        setScreenSetting(window);
        gamePanel.requestFocusInWindow();
    }

    /**
    * This function starts timer for tracking game time.
    */
    public static void startGameTimer(){
        Timer timer = new Timer(1000, e -> {
            remainingSec--;
            elapsedSec++;});
        timer.start();
    }

    /**
     * This function set few regular settings for the JFrame screen
     * @param window : JFrame
     */
    public static void setScreenSetting(JFrame window) {
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setPreferredSize(new Dimension(screenWidth, screenHeight));
        window.pack();
        window.setLocationRelativeTo(null);
    }

    /**
     * This function decrease remaining time to win the game by 1 minute if thief hits spike.
     * and increase game time by 1 minute if thief collect Nitro(special reward).
     */
    public static void updateTimerWithRewardAndHurdle(){
        //Decrease game time if player hits spike
        if(map.getPenalty() > 0){
            updateTimer(-map.getPenalty());
            map.resetPenalty();
        }
        //Increase game time if player collects Nitro
        if(map.getRewardValue() > 0){
            updateTimer(map.getRewardValue());
            map.resetRewardValue();
        }
    }

    /**
     * This function manages Nitro activation/deactivation throughout the game.
     */
    public static void manageNitroActivation() {
        //activate and deactivate nitros
        if(elapsedSec == 20) {
            map.activateNitros(true);
        }
        if(elapsedSec == 40) {
            map.activateNitros(false);
        }
    }

    /**
     * This function process game End whether through winning or losing the game.
     * @param gamePanel: GamePanel displays the game end screen.
     */
    public static void processGameEnd(GamePanel gamePanel) {
        //If player wins the game
        if(Diamond.getCount()== 10 && map.getThief().overLap(endPoint)){
            winGame(gamePanel);
        }
        //If player hits the pothole or uns out of the time
        if(map.getPenalty() == -1 || remainingSec == 0){
            loseGame(gamePanel);
        }
    }

    /**
     * This function updates the timer with the given time.
     * @param time seconds that need to be added to the time. Can be positive or negative.
     */
    private static void updateTimer(int time) {
        remainingSec += time;
    }

    /**
     * This function starts the game timer after provided delay time.
     * @param delay: The seconds to delay the game start timer.
     * @param timer: the timer that need to be started after the given delay.
     */
    private static void startTimerWithDelay(int delay, Timer timer) {
        Timer delayTimer = new Timer(delay, e -> {timer.start();});
        delayTimer.setRepeats(false);
        delayTimer.start();
    }

    /**
     * This function performs action when game has been lost by the player.
     * @param gamePanel: The gamePanel to display loose game screen
     */
    public static void loseGame(GamePanel gamePanel) {
        gamePanel.displayLoseGame();
    }

    /**
     * This function performs action when player looses game because cop caught thief
     * @param gamePanel: The game panel to display loseGame screen
     * @param gameTimer: The game timer that needs to stop
     */
    public static void loseGameByCopCatch(GamePanel gamePanel, Timer gameTimer) {
        gameTimer.stop();
        gamePanel.displayLoseGame();
    }

    /**
     * This function performs action when game has been won by the player.
     * @param gamePanel: The game panel to display win game screen.
     */
    public static void winGame(GamePanel gamePanel) {
        int sec = totalTime-remainingSec;
        gamePanel.displayWinGame(sec);
    }
}

