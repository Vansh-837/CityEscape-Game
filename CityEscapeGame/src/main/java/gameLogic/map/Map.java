/**
 * Author: Sanika Goyal
 * Cop Chase Logic Author: Andy
 * 2nd year student at SFU
 * Software Systems major
 */

package gameLogic.map;

import gameLogic.character.Cop;
import gameLogic.character.Thief;
import gameLogic.gameItems.GameItem;
import gameLogic.hurdles.Hurdle;
import gameLogic.rewards.Reward;

import java.awt.*;
import java.util.List;
import java.util.PriorityQueue;   // For managing the open list in A*
import java.util.HashSet;         // For managing the closed set          
import java.util.ArrayList;       // For handling the path reconstruction
import java.util.Set;             // For the closed set

/**
 * Map class contains all logic information of the game.
 * Main class shares the object of this class with UI to provide all the update in map.
 * It has gameItems, Rewards, Hurdles, Cops and Thief.
 * It also handles the logic of Thief movement on the map.
 * It also has the logic of cops' chasing Thief.
 */
public class Map {

    GameItem[][] gameItems;     //contains objects of Road, Garden and Building
    List<Reward> rewards;       //Contain objects of Diamond and Nitro
    List<Hurdle> hurdles;       //Contain objects of Spikes and Pothole
    List<Reward> nitros;        //Contain objects of Nitros
    Thief thief;
    Cop cop1;
    Cop cop2;

    Point thiefInitLocation = new Point(1, 0);  //Initial location of Thief
    Point cop1InitLocation = new Point(13, 23); // Initial position of Cop1
    Point cop2InitLocation = new Point(41, 19); //Initial position of Cop2

    int rewardValue;
    int penalty;

    /**
     * This constructor created for testing purpose only.
     * @param mapSite: Provides list of GameItems
     * @param manager: Provides list of rewards and hurdles
     */
    public Map(MapSite mapSite, ObjectManager manager, Cop cop1, Cop cop2,Thief thief) {
        gameItems = mapSite.initMapSite();
        rewards = manager.getRewardList();
        hurdles = manager.getHurdleList();
        this.cop1 = cop1;
        this.cop2 = cop2;
        this.thief = thief;
    }

    /**
     * This constructor initializes the Map by creating GameItems, Rewards and Hurdles.
     * @param filename: String - path to the file with positions of GameItems,Rewards and hurdles.
     */
    public Map(String filename){

        //GameItem creation
        MapSite site = new MapSite(filename);
        gameItems = site.initMapSite();

        //obtaining objects of rewards, hurdles and nitros
        ObjectManager manager = new ObjectManager(filename);
        rewards = manager.getRewardList();
        hurdles = manager.getHurdleList();
        nitros = manager.getNitroList();
    
        //Initialize thief and cop at some initial position with some given speed.
        thief = new Thief(thiefInitLocation);
        cop1 = new Cop(cop1InitLocation);
        cop2 = new Cop(cop2InitLocation);
    }


    /**
     * This function moves thief in the given direction.
     * It checks if the new position is valid for thief before changing.
     * @param direction: can be Left, Right, Up or Down
     */
    public void moveThief(Direction direction){
        Point thiefLocation = thief.getLocation();
        Point simulatedThiefPosition = simulatePosition(direction, thiefLocation);
        if(!overlappingWithCops(simulatedThiefPosition) && isBound(simulatedThiefPosition) && onRoad(simulatedThiefPosition)) {
            thief.setLocation(simulatedThiefPosition);
            processReward(simulatedThiefPosition);
            processHurdle(simulatedThiefPosition);
        }
    }

    /**
     * This function creates a simulated position after applying the direction movement.
     * @param d : direction can be Left, Right, Up, Down.
     * @param location: Point - that needs to be simulated.
     * @return point - the simulation of given location in the given direction.
     */
    public Point simulatePosition(Direction d,Point location){
        int dx = 0;
        int dy = 0;
        switch (d){
            case Up:
                dx = -1;
                break;
            case Down:
                dx = 1;
                break;
            case Left:
                dy = -1;
                break;
            case Right:
                dy = 1;
        }
        return new Point(location.x+dx, location.y+dy);
    }

    /**
     * This function checks the collision of given location with cop.
     * @param location: Point that needs to be tested for the collision with cop.
     * @return true if the given position is overlapping cop
     */
    public boolean overlappingWithCops(Point location){
        if(cop1.overLap(location))
            return true;
        return cop2.overLap(location);
    }

    /**
     * This function checks if the given position is in bound of the map.
     * @param location: Point that needs to be verified for in bound.
     * @return true if the given point is inBound otherwise false.
     */
    public boolean isBound(Point location){
        if((location.x) >=0 && (location.x) < gameItems.length){
            return (location.y) >= 0 && (location.y) < gameItems[0].length;
        }
        return false;
    }

    /**
     * This function checks if the given position is on the road.
     * @param location: Point that needs to be verified.
     * @return true if given point is on the road otherwise false.
     */
    public boolean onRoad(Point location){
        return gameItems[location.x][location.y].isPath();
    }

    /**
     * This function checks if the given player location has a reward and collects it.
     * It also updates the reward value if player collects the reward.
     * @param location : Point - the location of the player.
     */
    public void processReward(Point location){
        for (Reward reward : rewards) {
            if(reward.getLocation().equals(location) && !reward.isCollected() && reward.isActive()){
                reward.collect();
                rewardValue = reward.getRewardValue();
            }
        }
    }

    /**
     * This function checks if the given player location has a hurdle on it and charges the penalty.
     * It also updates the penalty value if player hits a hurdle.
     * @param location: Point - the location of the player
     */
    public void processHurdle(Point location){
        for(Hurdle hurdle : hurdles){
            if(hurdle.getLocation().equals(location) && hurdle.isActive()){
                hurdle.setInactive();
                penalty = hurdle.getPenalty();
            }
        }
    }

   /**
      * Moves both cops toward the thief.
      */
    public void moveCopsTowardThief() {
        Point thiefLocation = thief.getLocation();
        moveCopTowards(cop1, thiefLocation, cop2.getLocation());
        moveCopTowards(cop2, thiefLocation, cop1.getLocation());
    }

    /**
     * Moves the specified Cop towards the target location using the A* pathfinding algorithm.
     * This method calculates the optimal path to the target and ensures that the cop moves
     * along the path without colliding with another cop at the next step.
     *
     * @param cop The Cop object to be moved.
     * @param targetLocation The target location (typically the Thief's location) towards which the cop will move.
     * @param otherCopLocation The location of the other cop, to prevent both cops from occupying the same tile.
     */
    protected void moveCopTowards(Cop cop, Point targetLocation, Point otherCopLocation) {
        Point nextStep = findNextStep(cop.getLocation(), targetLocation, otherCopLocation);
        if (nextStep != null) {
            executeCopMove(cop, nextStep);
        }
    }
    
    private Point findNextStep(Point start, Point target, Point otherCopLocation) {
        List<Point> path = aStarPathfinding(start, target);
        if (path == null || path.size() <= 1 || path.get(1).equals(otherCopLocation)) {
            return null; // No valid path or conflict with other cop
        }
        return path.get(1);
    }
    
    private void executeCopMove(Cop cop, Point nextStep) {
        // Simulate the position using the direction from determineDirection
        Direction direction = determineDirection(cop.getLocation(), nextStep);
        Point simulatedPosition = simulatePosition(direction, cop.getLocation());
        
        // Update the cop's location
        cop.setLocation(simulatedPosition);
    }
    
    /**
     * Determines the direction from the current location to the next step.
     * @param currentLocation The current location of the Cop.
     * @param nextStep The next step location for the Cop.
     * @return The direction to move.
     */
    private Direction determineDirection(Point currentLocation, Point nextStep) {
        if (nextStep.x < currentLocation.x) return Direction.Up;
        if (nextStep.x > currentLocation.x) return Direction.Down;
        if (nextStep.y < currentLocation.y) return Direction.Left;
        if (nextStep.y > currentLocation.y) return Direction.Right;
        return null; // Should never happen if input is valid
    }
  
    /**
     * This function provides a thief object.
     * @return Thief: player
     */
    public Thief getThief(){
        return thief;
    }

    /**
     * This function provides the Cop1 object.
     * @return Cop2: Enemy
     */
    public Cop getCop1(){
        return cop1;
    }

    /**
     * This function provides the Cop2 object.
     * @return Cop2: Enemy
     */
    public Cop getCop2(){
        return cop2;
    }

    /**
     * This function provides the reward value
     * @return int: 0 if no reward has been collected at thief's current position,
     * otherwise the reward value of the reward collected.
     */
    public int getRewardValue(){
        return rewardValue;
    }

    /**
     * This function provides the penalty value.
     * @return int: 0 if no hurdle is hit at current thief's current position,
     * otherwise the penalty value of the hurdle hit.
     */
    public int getPenalty(){
        return penalty;
    }

    /**
     * This function resets the reward value to zero.
     */
    public void resetRewardValue(){
        this.rewardValue = 0;
    }

    /**
     * This function resets the penalty value to zero.
     */
    public void resetPenalty(){
        this.penalty = 0;
    }

    /**
     * This function returns the rewardList
     * @return List of Rewards containing Nitros and Diamonds
     */
    public List<Reward> getRewardList(){
        return rewards;
    }

    /**
     * This function returns the hurdleList.
     * @return List of hurdles containing Spikes and Potholes
     */
    public List<Hurdle> getHurdleList(){
        return hurdles;
    }

    /**
     * This function returns the gameItems  2d-array
     * @return 2d-Array of GameItems containing objects of Building, Garden and Road
     */
    public GameItem[][] getGameItems(){
        return gameItems;
    }

    /**
     * This function activates or deactivates the nitros based on the given boolean value.
     */
    public void activateNitros(boolean activate){
        for(Reward reward : nitros) {
            reward.setActiveStatus(activate);
        }
    }
    
    /**
    * Implements the A* pathfinding algorithm to find the shortest path from the start point to the target point.
    * @param start The starting point of the path.
    * @param target The target point of the path.
    * @return A list of Points representing the path from start to target, or null if no path exists.
    */
    protected List<Point> aStarPathfinding(Point start, Point target) {
        PriorityQueue<Node> openList = initializeOpenList(start, target);
        Set<Point> closedSet = new HashSet<>();
    
        while (!openList.isEmpty()) {
            Node current = openList.poll();
            if (isTargetReached(current, target)) {
                return reconstructPath(current);
            }
            closedSet.add(current.location);
            processNeighbors(current, target, openList, closedSet);
        }
        return null; 
    }
    
    private PriorityQueue<Node> initializeOpenList(Point start, Point target) {
        PriorityQueue<Node> openList = new PriorityQueue<>();
        openList.add(new Node(start, null, 0, calculateHeuristic(start, target)));
        return openList;
    }
    
    private boolean isTargetReached(Node current, Point target) {
        return current.location.equals(target);
    }
    
    private void processNeighbors(Node current, Point target, PriorityQueue<Node> openList, Set<Point> closedSet) {
        for (Point neighbor : getNeighbors(current.location)) {
            if (closedSet.contains(neighbor) || !gameItems[neighbor.x][neighbor.y].isPath()) {
                continue;
            }
            int gCost = current.gCost + 1;
            int hCost = calculateHeuristic(neighbor, target);
            Node neighborNode = new Node(neighbor, current, gCost, hCost);
    
            if (isBetterPath(openList, neighborNode)) {
                openList.add(neighborNode);
            }
        }
    }
    
    private boolean isBetterPath(PriorityQueue<Node> openList, Node neighborNode) {
        return openList.stream().noneMatch(n -> n.location.equals(neighborNode.location) && n.getFCost() <= neighborNode.getFCost());
    }
    
    /**
     * Calculates the distance heuristic between two points.
     * This heuristic estimates the cost to reach the target from a given point.
     * @param a The starting point.
     * @param b The target point.
     * @return The Manhattan distance between points a and b.
     */
    private int calculateHeuristic(Point a, Point b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

    /**
     * Retrieves the neighboring points of a given location.
     * @param location The current location to find neighbors.
     * @return A list of Points representing the neighboring locations.
     */
    private List<Point> getNeighbors(Point location) {
        List<Point> neighbors = new ArrayList<>();
        int x = location.x;
        int y = location.y;

        if (x > 0 && gameItems[x - 1][y].isPath()) neighbors.add(new Point(x - 1, y)); // up
        if (x < gameItems.length - 1 && gameItems[x + 1][y].isPath()) neighbors.add(new Point(x + 1, y)); // down
        if (y > 0 && gameItems[x][y - 1].isPath()) neighbors.add(new Point(x, y - 1)); // left
        if (y < gameItems[0].length - 1 && gameItems[x][y + 1].isPath()) neighbors.add(new Point(x, y + 1)); // right

        return neighbors;
    }
    /**
     * Reconstructs the path from the end node to the start node by following parent nodes.
     * @param node The end node of the path.
     * @return A list of Points representing the path from start to target.
     */
    private List<Point> reconstructPath(Node node) {
        List<Point> path = new ArrayList<>();
        while (node != null) {
            path.add(0, node.location); 
            node = node.parent;
        }
        return path;
    }
    
}

