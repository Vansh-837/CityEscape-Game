package gameUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import gameLogic.map.Map;
import gameLogic.rewards.Diamond;
import gameLogic.rewards.Nitro;

/**
 * GamePanel is the main display panel for the game, handling the rendering of
 * game elements and controls, such as the pause menu, timer, and collectible displays.
 */
public class GamePanel extends JPanel {

    private TerrainHandler th;
    private Map gameMap;

    final int screenWidth = 1268;
    final int screenHeight = 776;
   
    private JLabel timerLabel;
    private JLabel diamondLabel;  // Display diamonds collected
    private JLabel nitroLabel;    // Display nitro collected
   
    private JButton pauseButton;  
    private JLabel playLabel;  // Add play label
    private JLabel exitLabel;

   
    private boolean isPaused = false;  

    private JDialog pauseMenu;  // Pause menu dialog

    /**
     * Constructs a GamePanel object.
     *
     * @param map The Map object representing the game layout and state.
     */
    public GamePanel(Map map) {
        this.gameMap = map;
        this.th = new TerrainHandler(map);
        setLayout(null);  
        setBackground(Color.BLACK);  

        initializeExitButton();
        initializeCollectiblesDisplay();  // Initialize diamond and nitro labels
        createPauseMenu();  // Create the pause menu dialog
        displayTimer();
        createGameControlLabels();

    }

    /**
     * Sets up the control labels for "Play" and "Exit" directions.
     */
    private void createGameControlLabels() {
        // "PLAY" label setup
        playLabel = new JLabel(" ENTER ---> ");
        playLabel.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 24));
        playLabel.setForeground(Color.WHITE);
        playLabel.setBounds(7, 65, 140, 30);  // Positioning at top-left
        add(playLabel);

        // "EXIT" label setup
        exitLabel = new JLabel(" ---> EXIT");
        exitLabel.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 24));
        exitLabel.setForeground(Color.WHITE);
        exitLabel.setBounds(screenWidth - 350, 680, 150, 30);  // Positioning at top-right
        add(exitLabel);
    }

    /**
     * Initializes and displays the game timer label.
     */
    public void displayTimer() {
        timerLabel = new JLabel("00:00:00");
        timerLabel.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 36));
        timerLabel.setForeground(Color.WHITE);  
        timerLabel.setBounds(1050, 10, 200, 50);  
        add(timerLabel);
    }

    /**
     * Updates the timer display with the elapsed game time.
     *
     * @param elapsedSeconds Total elapsed seconds since the game started.
     */
    public void updateTimerDisplay(int elapsedSeconds) {
        timerLabel.setText(formatTime(elapsedSeconds));
    }


    /**
     * Initializes the display for collectibles (diamonds and nitro).
     */
    private void initializeCollectiblesDisplay() {
        // Diamond count label
        diamondLabel = new JLabel("Diamonds: 0 / 10");
        diamondLabel.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 24));
        diamondLabel.setForeground(Color.WHITE);
        diamondLabel.setBounds(1050, 70, 200, 50);  
        add(diamondLabel);

        // Nitro count label
        nitroLabel = new JLabel("Nitro: 0 / 2");
        nitroLabel.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 24));
        nitroLabel.setForeground(Color.WHITE);
        nitroLabel.setBounds(1050, 130, 200, 50);  
        add(nitroLabel);
    }

    /**
     * Updates the display with the current count of diamonds and nitro collected.
     */
    public void updateCollectiblesDisplay() {
        diamondLabel.setText("Diamonds: " + Diamond.getCount() + " / 10");
        nitroLabel.setText("Nitro: " + Nitro.getCount() + " / 2");
    }


    /**
     * Formats the given time in seconds into HH:MM:SS format.
     *
     * @param totalSeconds Total seconds to format.
     * @return The formatted time string.
     */
    public String formatTime(int totalSeconds) {
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }


    /**
     * Initializes the exit button with its display and action listener.
     */
    private void initializeExitButton() {
        pauseButton = new JButton("Quit");
        pauseButton.setFont(new Font("Roman", Font.BOLD, 20));
        pauseButton.setBounds(screenWidth - 220, screenHeight - 100, 200, 50);
        pauseButton.setBackground(Color.LIGHT_GRAY);
        pauseButton.setForeground(Color.BLACK);

        pauseButton.addActionListener(this::togglePause);
        add(pauseButton);
    }

    /**
     * Creates and configures the exit menu dialog.
     */
    private void createPauseMenu() {
        pauseMenu = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Paused", true);
        pauseMenu.setSize(400, 200);
        pauseMenu.setLayout(new GridLayout(3, 1));
        pauseMenu.setLocationRelativeTo(null); 

        JLabel prompt = new JLabel("Are you sure you want to leave?", SwingConstants.CENTER);
        prompt.setFont(new Font("Roman", Font.BOLD, 18));
        prompt.setForeground(Color.WHITE);
        prompt.setBackground(Color.BLACK);
        prompt.setOpaque(true);

       
        JButton exitButton = new JButton("Quit");

        
        exitButton.setFont(new Font("Roman", Font.BOLD, 18));

        

        exitButton.addActionListener(e -> System.exit(0));

        pauseMenu.add(prompt);
    
        pauseMenu.add(exitButton);

        pauseMenu.getContentPane().setBackground(Color.BLACK);
    }


    /**
     * Displays the "Game Over" dialog if the player loses the game.
     */
    public void displayLoseGame() {
        JDialog loseDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Game Over", true);
        loseDialog.setSize(400, 200);
        loseDialog.setLayout(new GridLayout(3, 1));
        loseDialog.setLocationRelativeTo(null); 
    
        JLabel messageLabel = new JLabel("Better luck next time!", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Serif", Font.BOLD, 18));
        messageLabel.setForeground(Color.RED);
        messageLabel.setOpaque(true);
    
        JLabel diamondsLabel = new JLabel("Diamonds Collected: " + Diamond.getCount(), SwingConstants.CENTER);
        diamondsLabel.setFont(new Font("Serif", Font.BOLD, 16));
        diamondsLabel.setForeground(Color.WHITE);
        
        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Roman", Font.BOLD, 18));
        exitButton.addActionListener(e -> System.exit(0));
    
        loseDialog.add(messageLabel);
        loseDialog.add(diamondsLabel);
        loseDialog.add(exitButton);
    
        loseDialog.getContentPane().setBackground(Color.BLACK);
        loseDialog.setVisible(true);
    }
    
    /**
     * Displays the "Victory" dialog if the player wins the game.
     *
     * @param totalSeconds Total time taken to win the game.
     */
    public void displayWinGame(int totalSeconds) {
        JDialog winDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Congratulations!", true);
        winDialog.setSize(400, 200);
        winDialog.setLayout(new GridLayout(3, 1));
        winDialog.setLocationRelativeTo(null); 
    
        JLabel messageLabel = new JLabel("Congratulations! You Escaped the City!", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Serif", Font.BOLD, 18));
        messageLabel.setForeground(Color.GREEN);
        
        String formattedTime = formatTime(totalSeconds);
        JLabel timeLabel = new JLabel("Time Taken: " + formattedTime, SwingConstants.CENTER);
        timeLabel.setFont(new Font("Serif", Font.BOLD, 16));
        timeLabel.setForeground(Color.WHITE);
    
        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Roman", Font.BOLD, 18));
        exitButton.addActionListener(e -> System.exit(0));
    
        winDialog.add(messageLabel);
        winDialog.add(timeLabel);
        winDialog.add(exitButton);
    
        winDialog.getContentPane().setBackground(Color.BLACK);
        winDialog.setVisible(true);
    }

    
    /**
     * Toggles the game's pause state.
     *
     * @param e The action event triggering the pause toggle.
     */

    private void togglePause(ActionEvent e) {
        if (isPaused) {
            resumeGame();
        } else {
            pauseGame();
        }
    }

    /**
     * Pauses the game and displays the pause menu.
     */
    private void pauseGame() {
        //gameTimer.stop();
        isPaused = true;
        pauseButton.setText("Paused");
        pauseMenu.setVisible(true);
    }


    /**
     * Resumes the game after being paused.
     */
    private void resumeGame() {
        //gameTimer.start();
        isPaused = false;
        pauseButton.setText("Pause");
    }

    /**
     * Paints the game components on the panel, including tiles and collectibles.
     *
     * @param g The Graphics object used for painting.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        int mapX = 500;
        int mapY = 330;
        int monitorX = screenWidth / 2;
        int monitorY = screenHeight / 2;
        List<Integer> offsetValues = new ArrayList<>();
        offsetValues.add(mapX);
        offsetValues.add(mapY);
        offsetValues.add(monitorX);
        offsetValues.add(monitorY);

        

        th.initializeMapTiles();
        th.renderTiles(g2, offsetValues);
        updateCollectiblesDisplay();
    }
    
}