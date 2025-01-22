package gameUI;

import gameLogic.gameItems.GameItem;
import gameLogic.gameItems.Garden;
import gameLogic.gameItems.Road;
import gameLogic.gameItems.Building;
import gameLogic.map.Map;
import gameLogic.hurdles.Hurdle;
import gameLogic.hurdles.Pothole;
import gameLogic.hurdles.Spike;
import gameLogic.rewards.Diamond;
import gameLogic.rewards.Nitro;
import gameLogic.rewards.Reward;
import gameLogic.character.Cop;
import gameLogic.character.Thief;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import java.awt.Point;


/**
 * Handles the terrain and character rendering in the City Escape Game.
 * Manages the loading of tile and character images, and the mapping of 
 * game items, rewards, and hurdles to tiles in the game grid.
 */
public class TerrainHandler {

    private static final int TILE_DIMENSION = 14; //changes
    private static final int WORLD_COLUMNS = 56;
    private static final int WORLD_ROWS = 47;
    private Map gameMap;
    public Tile[] terrainTiles;
    public int[][] tileMap;
    private Cop cop1;
    private Cop cop2;
    private Thief thief;
    public BufferedImage copImage;
    public BufferedImage thiefImage;
    
    /**
     * Constructs a TerrainHandler and initializes the terrain and map layout.
     * @param map the game map containing game items, rewards, and characters
     */
    public TerrainHandler(Map map) {
        this.gameMap = map;
        this.cop1 = map.getCop1();
        this.cop2 = map.getCop2(); // Assuming cop1 is used here
        this.thief = map.getThief();
        terrainTiles = new Tile[7];  // Adjust the size if you have more tile types
        tileMap = new int[WORLD_ROWS][WORLD_COLUMNS];

        loadTiles();
        loadCharacterImages();
    }
   
    /**
    * Initializes the map tiles by mapping game items, rewards, and hurdles to specific tile types.
    */
    public void initializeMapTiles() {
        initializeGameItems();
        mapRewardsToTiles();
        mapHurdlesToTiles();
    }

    /**
     * Maps game items to their corresponding tile indices.
     */
    private void initializeGameItems() {
        GameItem[][] gameItems = gameMap.getGameItems();

        for (int r = 0; r < WORLD_ROWS; r++) {
            for (int c = 0; c < WORLD_COLUMNS; c++) {
                GameItem item = gameItems[r][c];
                int tileIndex = getTileIndex(item);
                tileMap[r][c] = tileIndex;
            }
        }
    }

    /**
     * Maps rewards to their specific tile indices.
     */
    private void mapRewardsToTiles() {
        List<Reward> rewards = gameMap.getRewardList();

        for (Reward reward : rewards) {
            int row = reward.getLocation().x;
            int col = reward.getLocation().y;

            if (!reward.isCollected()) {
                if (reward instanceof Nitro) {
                    tileMap[row][col] = reward.isActive() ? 4 : 1; // Nitro tile or road
                } else if (reward instanceof Diamond) {
                    tileMap[row][col] = 3; // Diamond tile
                }
            } else {
                tileMap[row][col] = 1; // Road if collected
            }
        }
    }

    /**
     * Maps hurdles to their specific tile indices.
     */
    private void mapHurdlesToTiles() {
        List<Hurdle> hurdles = gameMap.getHurdleList();

        for (Hurdle hurdle : hurdles) {
            int row = hurdle.getLocation().x;
            int col = hurdle.getLocation().y;

            if (hurdle.isActive()) {
                if (hurdle instanceof Spike) {
                    tileMap[row][col] = 5; // Spike tile
                } else if (hurdle instanceof Pothole) {
                    tileMap[row][col] = 6; // Pothole tile
                }
            } else {
                tileMap[row][col] = 1; // Road if inactive
            }
        }
    }


    /**
     * Loads character images for the cops and the thief, and resizes them for display on the map.
     */
    private void loadCharacterImages() {
        try {
            copImage = ImageIO.read(getClass().getResourceAsStream("/cop.png"));
            thiefImage = ImageIO.read(getClass().getResourceAsStream("/thief.png"));
            // Resize images for 2x2 tile display
            copImage = resizeImage(copImage, TILE_DIMENSION * 2, TILE_DIMENSION * 2);
            thiefImage = resizeImage(thiefImage, TILE_DIMENSION * 2, TILE_DIMENSION * 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Loads tile images and assigns their collision properties.
     */
    public void loadTiles() {
        String[] imagePaths = {"/grass", "/road", "/Hut","/diamond","/nitro", "/spikes", "/hole"}; // Added paths for rewards and hurdles


        try {
            for (int i = 0; i < imagePaths.length; i++) {
                terrainTiles[i] = new Tile();
                terrainTiles[i].image = ImageIO.read(getClass().getResourceAsStream(imagePaths[i] + ".png"));

                // Resize hut image to 2x2 or adjust if needed
                if (i == 2) {
                    terrainTiles[i].image = resizeImage(terrainTiles[i].image, TILE_DIMENSION * 2, TILE_DIMENSION * 2);
                } else {
                    terrainTiles[i].image = resizeImage(terrainTiles[i].image, TILE_DIMENSION, TILE_DIMENSION);
                }
            }
        }
        catch(NumberFormatException | IOException | NullPointerException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * Determines the tile index corresponding to a given GameItem.
     * @param item the game item to be converted
     * @return the tile index for the specified item, or -1 if unrecognized
     */
    private int getTileIndex(GameItem item) {
        if (item instanceof Garden) return 0; // Grass
        if (item instanceof Road) return 1; // Road
        if (item instanceof Building) return 2; // Building
        return -1;  // Invalid or unknown GameItem
    }

    /**
     * Resizes an image to specified dimensions.
     * @param original the original image
     * @param width the target width
     * @param height the target height
     * @return a resized version of the original image
     */
    private BufferedImage resizeImage(BufferedImage original, int width, int height) {
        if (original == null || width <= 0 || height <= 0) return null;
        BufferedImage resizedImage = new BufferedImage(width, height, original.getType());
        Graphics2D graphics = resizedImage.createGraphics();
        graphics.drawImage(original, 0, 0, width, height, null);
        graphics.dispose();
        return resizedImage;
    }

    /**
     * Renders a character on the screen at a specified location.
     * @param graphics the graphics context for rendering
     * @param characterImage the image of the character
     * @param location the location of the character on the map
     * @param offsetValues the list of offset values.
    */
    private void renderCharacter(Graphics2D graphics, BufferedImage characterImage, Point location,List<Integer> offsetValues ) {
        int offsetX=offsetValues.get(0);
        int offsetY=offsetValues.get(1);
        int screenX=offsetValues.get(2);
        int screenY=offsetValues.get(3);
        int x = location.y * TILE_DIMENSION - offsetX + screenX;
        int y = location.x * TILE_DIMENSION - offsetY + screenY;
        graphics.drawImage(characterImage, x, y, TILE_DIMENSION * 1, TILE_DIMENSION * 1, null);
    }

    /**
     * Renders the terrain tiles on the screen based on their positions in the map.
     * @param graphics the graphics context for rendering
     * @param offsetValues the offset values list for rendering the map
     */
    public void renderTiles(Graphics2D graphics, List<Integer> offsetValues) {
        int offsetX =offsetValues.get(0);
        int offsetY = offsetValues.get(1);
        int screenX = offsetValues.get(2);
        int screenY = offsetValues.get(3);

        for (int row = 0; row < WORLD_ROWS; row++) {
            for (int col = 0; col < WORLD_COLUMNS; col++) {
                int tileIndex = tileMap[row][col];
                
                int tileX = col * TILE_DIMENSION - offsetX + screenX;
                int tileY = row * TILE_DIMENSION - offsetY + screenY;


                graphics.drawImage(
                    terrainTiles[tileIndex].image,
                    tileX, tileY,
                    TILE_DIMENSION * 1, TILE_DIMENSION * 1,  
                    null
                );
            }
        }
        renderCharacter(graphics, copImage, cop1.getLocation(), offsetValues);
        renderCharacter(graphics, copImage, cop2.getLocation(), offsetValues);
        renderCharacter(graphics, thiefImage, thief.getLocation(), offsetValues);
    }
      
}