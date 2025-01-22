/**
 * Author: Sanika Goyal
 * 2nd year student at SFU
 * Software Systems major
 */

package gameLogic.map;

import gameLogic.hurdles.*;
import gameLogic.rewards.*;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * RewardHurdleManager creates a list of rewards and hurdles by
 * reading location information from the file provided.
 */
public class ObjectManager {

    List<Reward> rewardList = new ArrayList<>();        //list of Diamonds and Nitros
    List<Hurdle> hurdleList = new ArrayList<>();        //list of potholes and spikes
    List<Reward> nitroList = new ArrayList<>();

    /**
     * This constructor creates the list of rewards and hurdles by
     * reading location information from the file provided.
     * @param filename: String - file name that has map data, i.e., location information of the objects
     */
    public ObjectManager(String filename) {
        try (BufferedReader reader = getBufferedReader(filename)) {
            String line;
            int row = 0;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                String[] inputLine = line.split(" ");

                for (int col = 0; col < inputLine.length; col++) {
                    int code = Integer.parseInt(inputLine[col]);
                    createObject(code, row, col); // creates an object of reward/hurdle
                }
                row++;
            }
        } catch (NullPointerException e) {
            System.out.println("Map file not found");
        } catch (NumberFormatException e) {
            System.out.println("Incorrect file format");
        } catch (IOException e) {
            System.out.println("Error reading file");
        }
    }

    /**
     * Protected method to fetch a BufferedReader.
     * This can be overridden in tests to provide mocked input data.
     *
     * @param filename: String - file name containing the map data
     * @return BufferedReader instance for reading the file
     * @throws IOException if an error occurs while accessing the file
     */
    protected BufferedReader getBufferedReader(String filename) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename);
        return new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)));
    }

    /**
     * This function creates an object of Diamond, Nitro, Pothole, or Spike depending on the code value.
     * @param code: int - determines the type of object - Diamond, Nitro, Pothole, or Spike.
     * @param row: int - provides the row index of the object location on the map.
     * @param col: int - provides the col index of the object location on the map.
     */
    private void createObject(int code, int row, int col) {
        switch (code) {
            case 3:
                rewardList.add(new Diamond(new Point(row, col)));
                break;
            case 4:
                Nitro reward = new Nitro(new Point(row, col));
                rewardList.add(reward);
                nitroList.add(reward);
                break;
            case 5:
                hurdleList.add(new Spike(new Point(row, col)));
                break;
            case 6:
                hurdleList.add(new Pothole(new Point(row, col)));
                break;
            default:
                // Log invalid code or handle gracefully
                //System.out.println("Invalid code encountered: " + code);
        }
    }

    /**
     * This function provides the list of rewards.
     * @return List : objects of Diamond and Nitro
     */
    public List<Reward> getRewardList() {
        return rewardList;
    }

    /**
     * This function provides a list of hurdles.
     * @return List : objects of Pothole and Spike
     */
    public List<Hurdle> getHurdleList() {
        return hurdleList;
    }

    /**
     * This function provides a list of Nitros.
     * @return List of Rewards containing Nitros only.
     */
    public List<Reward> getNitroList() {
        return nitroList;
    }
}
