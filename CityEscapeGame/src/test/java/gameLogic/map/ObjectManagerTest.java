package gameLogic.map;

import gameLogic.hurdles.*;
import gameLogic.rewards.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.awt.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the ObjectManager class for reading map data.
 */
class ObjectManagerTest {
    private ObjectManager manager;

    private static Object[] getValues() {
        return new Object[]{
                new Object[]{"", "Number format exception not handled correctly."},
                new Object[]{"tmp.txt", "Null Pointer exception not handled correctly."},
                new Object[]{null, "Null pointer exception not handled correctly."},
        };
    }

    /**
     * Test to ensure that exceptions are being handled correctly for invalid file names.
     */
    @ParameterizedTest
    @MethodSource("getValues")
    public void EmptyStringFileName(String filename, String errorMessage) {
        try {
            manager = new ObjectManager(filename);
        } catch (NumberFormatException e) {
            fail(errorMessage);
        }
    }

    /**
     * Test if rewards (Diamond and Nitro) are correctly initialized from the map file.
     */
    @Test
    void testRewardsInitialization() {
        manager = new ObjectManager("valid_rewards_map.txt");

        // Single assertion using assertAll to check reward initialization
        assertAll("Reward Initialization",
                () -> assertNotNull(manager.getRewardList()),
                () -> assertTrue(manager.getRewardList().stream().anyMatch(r -> r instanceof Diamond)),
                () -> assertTrue(manager.getRewardList().stream().anyMatch(r -> r instanceof Nitro))
        );
    }

    /**
     * Test if hurdles (Pothole and Spike) are correctly initialized from the map file.
     */
    @Test
    void testHurdlesInitialization() {
        manager = new ObjectManager("valid_rewards_map.txt");

        // Single assertion using assertAll to check hurdle initialization
        assertAll("Hurdle Initialization",
                () -> assertNotNull(manager.getHurdleList()),
                () -> assertTrue(manager.getHurdleList().stream().anyMatch(h -> h instanceof Pothole)),
                () -> assertTrue(manager.getHurdleList().stream().anyMatch(h -> h instanceof Spike))
        );
    }

    /**
     * Test if Nitros are correctly initialized from the map file.
     */
    @Test
    void testNitrosInitialization() {
        manager = new ObjectManager("valid_rewards_map.txt");

        // Single assertion using assertAll to check Nitro initialization
        assertAll("Nitro Initialization",
                () -> assertNotNull(manager.getNitroList()),
                () -> assertTrue(manager.getNitroList().stream().anyMatch(n -> n instanceof Nitro))
        );
    }

    /**
     * Test if the ObjectManager handles an empty map file correctly.
     */
    @Test
    void testHandlingEmptyFile() {
        manager = new ObjectManager("empty_rewards_map.txt");

        // Single assertion using assertAll to check handling of empty file
        assertAll("Empty File Handling",
                () -> {
                    List<Reward> rewardList = manager.getRewardList();
                    assertNotNull(rewardList);
                    assertTrue(rewardList.isEmpty());  // No rewards should be created
                },
                () -> {
                    List<Hurdle> hurdleList = manager.getHurdleList();
                    assertNotNull(hurdleList);
                    assertTrue(hurdleList.isEmpty());  // No hurdles should be created
                }
        );
    }
}