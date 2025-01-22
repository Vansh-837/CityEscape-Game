package gameLogic.map;

import java.awt.Point;

/**
 * Node class represents a point in the A* pathfinding algorithm.
 * Each Node has a location on the map, a reference to its parent node (for path reconstruction),
 * and associated costs (gCost and hCost) to evaluate the best path.
 */
public class Node implements Comparable<Node> {
    public Point location;
    public Node parent;
    public int gCost; 
    public int hCost; 

    /**
     * Constructor to initialize a Node with location, parent, gCost, and hCost.
     * @param location The coordinates of this node.
     * @param parent The parent node of this node in the path.
     * @param gCost The cost from the start node to this node.
     * @param hCost The estimated cost from this node to the target node.
     */
    public Node(Point location, Node parent, int gCost, int hCost) {
        this.location = location;
        this.parent = parent;
        this.gCost = gCost;
        this.hCost = hCost;
    }

    /**
     * Calculates the total cost (fCost) of this node, which is gcost + hcost.
     * The fCost is used to prioritize nodes in the A* algorithm's list.
     * @return The total cost (fCost) of this node.
     */
    public int getFCost() {
        return gCost + hCost;
    }

    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.getFCost(), other.getFCost());
    }
}