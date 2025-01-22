package gameUI;

import gameLogic.map.Map;
import gameLogic.rewards.Diamond;
import gameLogic.rewards.Nitro;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;


class GamePanelTest {
    private Map mockMap;
    private GamePanel gamePanel;


    @BeforeEach
    void setUp() {
        mockMap = Mockito.mock(Map.class); // Mock the Map class
        gamePanel = new GamePanel(mockMap); // Create the GamePanel with the mock map

       
    }

    @Test
    void testFormatTime() {
        
        
        // Test case 1: 1 hour, 1 minute, 1 second
        String result = gamePanel.formatTime(3661);
        assertEquals("01:01:01", result, "Expected time format is 01:01:01");

        // Test case 2: 0 hours, 59 minutes, 59 seconds
        result = gamePanel.formatTime(3599);
        assertEquals("00:59:59", result, "Expected time format is 00:59:59");

        // Test case 3: 0 hours, 0 minutes, 0 seconds
        result = gamePanel.formatTime(0);
        assertEquals("00:00:00", result, "Expected time format is 00:00:00");

        // Test case 4: 2 hours, 0 minutes, 0 seconds
        result = gamePanel.formatTime(7200);
        assertEquals("02:00:00", result, "Expected time format is 02:00:00");
    }

    @Test
    public void testUpdateCollectiblesDisplay() {
        // Update the collectibles display
        gamePanel.updateCollectiblesDisplay();

        // Access the diamond and nitro labels
        JLabel diamondLabel = (JLabel) gamePanel.getComponent(1);
        JLabel nitroLabel = (JLabel) gamePanel.getComponent(2);

        // Check that the labels are not null
        assertNotNull(diamondLabel,"Diamond label should not be null");
        assertNotNull(nitroLabel,"Nitro label should not be null" );
    }


    @Test
    void testCreateGameControlLabels() {
        // Verify the "PLAY" label is created and has the correct properties
        JLabel playLabel = (JLabel) gamePanel.getComponent(4); // Access the play label
        assertNotNull(playLabel, "Play label should not be null");
        assertEquals(" ENTER ---> ", playLabel.getText(), "Play label should have the correct text");

        // Verify the "EXIT" label is created and has the correct properties
        JLabel exitLabel = (JLabel) gamePanel.getComponent(5); // Access the exit label
        assertNotNull(exitLabel, "Exit label should not be null");
        assertEquals(" ---> EXIT", exitLabel.getText(), "Exit label should have the correct text");
    }
}
