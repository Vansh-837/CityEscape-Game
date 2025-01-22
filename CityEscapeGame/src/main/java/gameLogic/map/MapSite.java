/**
 * Author: Sanika Goyal
 * 2nd year student at SFU
 * Software Systems major
 */

package gameLogic.map;

import gameLogic.gameItems.Building;
import gameLogic.gameItems.GameItem;
import gameLogic.gameItems.Garden;
import gameLogic.gameItems.Road;

import java.awt.*;
import java.io.*;
import java.util.Objects;

/**
 * MapSite creates a 2d array of GameItems by reading the map from a txt file.
 */
public class MapSite {

    int rows = 0;               //number of rows
    int cols = 0;               //number of columns
    GameItem[][] gameItems;     //a 2d array containing buildings, roads and gardens.
    String filename;

    /**
     * This constructor saves the filename to read MapSite data from.
     * @param fileName: String - name of the file that contains a fixed map.
     */
    public MapSite(String fileName) {
        this.filename = fileName;
    }

    /**
     * This function initialises the MapSite by creating objects of buildings, roads and gardens.
     * It reads the map data from a txt file to allocate the location of each object.
     * @return GameItem[][]: a 2d array containing objects of buildings, roads and garden.
     */
    
     public GameItem[][] initMapSite() {

        try{
            //Read the map data file to find the dimensions of the array required to store the GameItems
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)));
            // Get the number of rows and columns from the file
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (cols == 0) {
                    cols = line.split(" ").length;
                }
                rows++;
            }

            // Initialize the array with rows and columns
            gameItems = new GameItem[rows][cols];

            // Re-read the file to create objects of buildings, roads, and gardens
            InputStream inputStream2 = getClass().getClassLoader().getResourceAsStream(filename);
            BufferedReader reader2 = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream2)));
            int r = 0;
            while ((line = reader2.readLine()) != null) {
                String[] input = line.trim().split(" ");
                for (int c = 0; c < input.length; c++) {
                    int code = Integer.parseInt(input[c]);
                    createGameItem(code, r, c);
                }
                r++;
            }
        }
        catch(NullPointerException e){
            System.out.println("Map file not found");
        }
        catch(NumberFormatException e){
            System.out.println("Incorrect file");
        }
         catch (IOException e) {
             System.out.println("Error reading file");
        }
         return gameItems;
    }

    /**
     * This function creates a garden, building or road object depending on code value.
     * @param code: int - determines the type of GameItem - Garden, Road or Building.
     * @param r: int - provides the row index of the GameItem location on the map.
     * @param c: int - provides the col index of the GameItem location on the map.
     */
    private void createGameItem(int code, int r, int c){

        switch(code){
            case 0:
                gameItems[r][c] = new Garden(new Point(r,c));
                break;
            case 2:
                gameItems[r][c] = new Building(new Point(r,c)); 
                break;
            default:
                gameItems[r][c] = new Road(new Point(r,c));
                break;
        }
    }
}
