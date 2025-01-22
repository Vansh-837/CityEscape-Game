package gameUI;

import gameUI.TerrainHandler;
import gameLogic.map.Map;
import gameLogic.rewards.Diamond;
import gameLogic.rewards.Nitro;
import gameLogic.rewards.Reward;
import gameLogic.character.Cop;
import gameLogic.character.Thief;
import gameLogic.gameItems.GameItem;
import gameLogic.hurdles.Hurdle;
import gameLogic.hurdles.Pothole;
import gameLogic.hurdles.Spike;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class TerrainHandlerTest {

    @Mock
    private Map mockMap;

    @Mock
    private Cop mockCop1, mockCop2;

    @Mock
    private Thief mockThief;

    @Mock
    private Graphics2D mockGraphics;

    @Mock
    private Nitro mockNitro;

    @Mock
    private Diamond mockDiamond;

    @Mock
    private Spike mockSpike;

    @Mock
    private Pothole mockPothole;


    private TerrainHandler terrainHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Setup mock map with cop and thief for TerrainHandler constructor
        when(mockMap.getCop1()).thenReturn(mockCop1);
        when(mockMap.getCop2()).thenReturn(mockCop2);
        when(mockMap.getThief()).thenReturn(mockThief);

        // Set predefined locations
        when(mockCop1.getLocation()).thenReturn(new Point(15, 15));
        when(mockCop2.getLocation()).thenReturn(new Point(10, 10));
        when(mockThief.getLocation()).thenReturn(new Point(3, 3));

        GameItem[][] mockGameItems = new GameItem[47][56];
        when(mockMap.getGameItems()).thenReturn(mockGameItems);


        terrainHandler = new TerrainHandler(mockMap);
    }

    @Test
    void terraintilesInitialization() {
        assertNotNull(terrainHandler.terrainTiles, "Terrain tiles should be initialized");
    }

    @Test
    void tilemapinitialization(){
        assertNotNull(terrainHandler.tileMap, "Tile map should be initialized");
    }

    @Test
    void tilemapsizetest(){
        assertEquals(7, terrainHandler.terrainTiles.length, "Terrain tiles array should have correct size");
    }

    @Test
    void grasstest() {
        terrainHandler.loadTiles();
        assertNotNull(terrainHandler.terrainTiles[0].image, "Image for grass should be loaded");
    }

    @Test
    void roadtest() {
        terrainHandler.loadTiles();
        assertNotNull(terrainHandler.terrainTiles[1].image, "Image for road should be loaded");
    }

    @Test
    void huttest() {
        terrainHandler.loadTiles();
        assertNotNull(terrainHandler.terrainTiles[2].image, "Image for hut should be loaded ");
    } 
    
    @Test
    void diamondtest() {
        terrainHandler.loadTiles();
        assertNotNull(terrainHandler.terrainTiles[3].image, "Image for diamond should be loaded");
    } 
    
    @Test
    void nitrotest() {
        terrainHandler.loadTiles();
        assertNotNull(terrainHandler.terrainTiles[4].image, "Image for nitro should be loaded");
    } 
    
    @Test
    void spiketest() {
        terrainHandler.loadTiles();
        assertNotNull(terrainHandler.terrainTiles[5].image, "Image for spikes should be loaded");
    } 
    
    @Test
    void potholetest() {
        terrainHandler.loadTiles();
        assertNotNull(terrainHandler.terrainTiles[6].image, "Image for pothole should be loaded");
    }


    @Test
    void coptest() {
        assertNotNull(terrainHandler.copImage, "Cop image should be loaded");  
    }

    @Test
    void thieftest(){
        assertNotNull(terrainHandler.thiefImage, "Thief image should be loaded");
    }

    @Test
    void testCorrectTileImageIsRenderedAtLocation() {
        terrainHandler.tileMap[3][3] = 1; // Set tile index for testing (e.g., index 1 corresponds to "Road" tile)

        List<Integer> offsetValues = new ArrayList<>();
        offsetValues.add(0);
        offsetValues.add(0);
        offsetValues.add(0);
        offsetValues.add(0);

        terrainHandler.renderTiles(mockGraphics, offsetValues);


        verify(mockGraphics, atLeastOnce()).drawImage(
            eq(terrainHandler.terrainTiles[1].image),
            eq(3 * 14),
            eq(3 * 14),
            eq(14),
            eq(14),
            any()
        );
    }

    @Test
    void testCop1Render() {
        // Render tiles and check image rendering for cops and thief based on predefined locations
        List<Integer> offsetValues = new ArrayList<>();
        offsetValues.add(0);
        offsetValues.add(0);
        offsetValues.add(0);
        offsetValues.add(0);

        terrainHandler.renderTiles(mockGraphics, offsetValues);


        verify(mockGraphics).drawImage(
            eq(terrainHandler.copImage),
            eq(15 * 14),
            eq(15 * 14),
            eq(14 ),
            eq(14 ),
            any()
        );
    }

    @Test
    void testCop2Render() {
        List<Integer> offsetValues = new ArrayList<>();
        offsetValues.add(0);
        offsetValues.add(0);
        offsetValues.add(0);
        offsetValues.add(0);

        terrainHandler.renderTiles(mockGraphics, offsetValues);

        
        verify(mockGraphics).drawImage(
            eq(terrainHandler.copImage),
            eq(10 * 14),
            eq(10 * 14),
            eq(14 ),
            eq(14 ),
            any()
        );
    }

    @Test
    void testThiefRender() {

        List<Integer> offsetValues = new ArrayList<>();
        offsetValues.add(0);
        offsetValues.add(0);
        offsetValues.add(0);
        offsetValues.add(0);

        terrainHandler.renderTiles(mockGraphics, offsetValues);

        
        verify(mockGraphics).drawImage(
            eq(terrainHandler.thiefImage),
            eq(3 * 14),
            eq(3 * 14),
            eq(14 ),
            eq(14 ),
            any()
        );
    }

    @Test
    void testTileMapInitializedWithNitro() {
        ArrayList<Reward> rewards = new ArrayList<>();
        when(mockNitro.getLocation()).thenReturn(new Point(4, 4));
        when(mockNitro.isCollected()).thenReturn(false);
        when(mockNitro.isActive()).thenReturn(true);
        rewards.add(mockNitro);
        when(mockMap.getRewardList()).thenReturn(rewards);

        terrainHandler.initializeMapTiles();

        assertEquals(4, terrainHandler.tileMap[4][4], "Tile index for active Nitro should be 4.");
    }

    @Test
    void testTileMapInitializedWithDiamond() {
        ArrayList<Reward> rewards = new ArrayList<>();
        when(mockDiamond.getLocation()).thenReturn(new Point(9, 9));
        when(mockDiamond.isCollected()).thenReturn(false);
        rewards.add(mockDiamond);
        when(mockMap.getRewardList()).thenReturn(rewards);

        terrainHandler.initializeMapTiles();

        assertEquals(3, terrainHandler.tileMap[9][9], "Tile index for uncollected Diamond should be 3.");
    }

    @Test
    void testTileMapInitializedWithSpike() {
        ArrayList<Hurdle> hurdles = new ArrayList<>();
        when(mockSpike.getLocation()).thenReturn(new Point(20, 20));
        when(mockSpike.isActive()).thenReturn(true);
        hurdles.add(mockSpike);
        when(mockMap.getHurdleList()).thenReturn(hurdles);

        terrainHandler.initializeMapTiles();

        assertEquals(5, terrainHandler.tileMap[20][20], "Tile index for active Spike should be 5.");
    }

    @Test
    void testTileMapInitializedWithPothole() {
        ArrayList<Hurdle> hurdles = new ArrayList<>();
        when(mockPothole.getLocation()).thenReturn(new Point(37, 37));
        when(mockPothole.isActive()).thenReturn(true);
        hurdles.add(mockPothole);
        when(mockMap.getHurdleList()).thenReturn(hurdles);

        terrainHandler.initializeMapTiles();

        assertEquals(6, terrainHandler.tileMap[37][37], "Tile index for active Pothole should be 6.");
    }

   
    
}