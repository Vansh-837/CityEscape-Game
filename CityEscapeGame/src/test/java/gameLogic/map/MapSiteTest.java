package gameLogic.map;

import gameLogic.gameItems.Building;
import gameLogic.gameItems.Garden;
import gameLogic.gameItems.Road;
import gameLogic.gameItems.GameItem;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;
import java.util.Arrays;

/**
 * This class tests the creation and initialization of various game items within the map.
 * It ensures that the map file is read correctly and that items such as Gardens, Buildings, and Roads
 * are created and placed correctly based on the contents of the map file.
 */
public class MapSiteTest {

    // Constants for valid and invalid map file names
    private static final String VALID_MAP_FILE = "test_map.txt";
    private static final String INVALID_MAP_FILE = "invalid_map.txt";

    /**
     * Unit test - To test that a Garden is correctly created at position (0, 0)
     * when the map file contains a Garden at this location.
     */
    @Test
    public void testGardenCreation() {
        MapSite mapSite = new MapSite(VALID_MAP_FILE); // The file has `0` at (0, 0)
        GameItem[][] gameItems = mapSite.initMapSite();

        // Single assertion using assertAll
        assertAll("Garden at (0, 0)",
                () -> assertTrue(gameItems[0][0] instanceof Garden),
                () -> assertEquals(new Point(0, 0), gameItems[0][0].getLocation())
        );
    }

    /**
     * Unit test - To test that a Building is correctly created at position (1, 1)
     * when the map file contains a Building at this location.
     */
    @Test
    public void testBuildingCreation() {
        MapSite mapSite = new MapSite(VALID_MAP_FILE); // The file has `2` at (1, 1)
        GameItem[][] gameItems = mapSite.initMapSite();

        // Single assertion using assertAll
        assertAll("Building at (1, 1)",
                () -> assertTrue(gameItems[1][1] instanceof Building),
                () -> assertEquals(new Point(1, 1), gameItems[1][1].getLocation())
        );
    }

    /**
     * Unit test - To verify that no Roads are created in the map file
     * and that no Road objects are found in the initialized map.
     */
    @Test
    public void testRoadCreation() {
        MapSite mapSite = new MapSite(VALID_MAP_FILE);
        GameItem[][] gameItems = mapSite.initMapSite();

        // Single assertion to check that no Roads are found
        assertFalse(Arrays.stream(gameItems)
                .flatMap(Arrays::stream)
                .anyMatch(item -> item instanceof Road));
    }

    /**
     * Unit test - To test the creation of mixed GameItems (Gardens and Buildings)
     * based on the contents of the map file.
     */
    @Test
    public void testMixedGameItemCreation() {
        MapSite mapSite = new MapSite(VALID_MAP_FILE); // The file has `0` and `2` at known positions
        GameItem[][] gameItems = mapSite.initMapSite();

        // Single assertion using assertAll
        assertAll("Mixed game items",
                () -> assertTrue(gameItems[0][0] instanceof Garden),
                () -> assertTrue(gameItems[1][1] instanceof Building)
        );
    }
}
