package gameLogic.map;

import gameLogic.character.*;
import gameLogic.gameItems.*;
import gameLogic.hurdles.*;
import gameLogic.rewards.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * This class test the important features of Map class.
 * It has both integration and unit tests.
 */
class MapTest {

    @Mock
    private ObjectManager mockObjectManager;

    @Mock
    private MapSite mockMapSite;

    @Mock
    private Cop mockCop1;

    @Mock
    private Cop mockCop2;

    //Initialising classes being tested
    private Map map;
    private Thief thief;
    List<Reward> rewardList = new ArrayList<>();
    List<Hurdle> hurdleList = new ArrayList<>();
    GameItem[][] gameItems;

    private void initializeRewardsAndHurdles(){

        //Create reward List
        rewardList.add(new Diamond(new Point(0,1)));
        Reward reward = new Nitro(new Point(0,2));
        reward.setActiveStatus(true);
        rewardList.add(reward);

        //Create Hurdle list
        hurdleList.add(new Spike(new Point(0,3)));
        hurdleList.add(new Pothole(new Point(0,4)));
    }

    private void createMockObjects(){

        //Create a mock object for object manager
        mockObjectManager = mock(ObjectManager.class);
        mockMapSite = mock(MapSite.class);
        mockCop1 = mock(Cop.class);
        mockCop2 = mock(Cop.class);

        //Arrange return values for mock object method calls
        when(mockObjectManager.getRewardList()).thenReturn(rewardList);
        when(mockObjectManager.getHurdleList()).thenReturn(hurdleList);
        when(mockMapSite.initMapSite()).thenReturn(gameItems);
        when(mockCop1.getLocation()).thenReturn(new Point(2,1));
        when(mockCop2.getLocation()).thenReturn(new Point(2,2));
    }

    private void createGameItems(){
        gameItems = new GameItem[4][5];
        for(int i=0; i<gameItems.length; i++) {
            if(i<3){
                for(int j=0; j<gameItems[0].length; j++) {
                    gameItems[i][j] = new Road(new Point(i,j));
                }
            }else{
                for(int j=0; j<gameItems[1].length; j++) {
                    if(j<3){
                        gameItems[i][j] = new Garden(new Point(i,j));
                    }
                    gameItems[i][j] = new Building(new Point(i,j));
                }
            }
        }
    }

    @BeforeEach
    void setUp() {

        //Initialize the actual and mock objects required for testing
        createGameItems();
        initializeRewardsAndHurdles();
        createMockObjects();

        thief = new Thief(new Point(1,3));
        map = new Map(mockMapSite,mockObjectManager,mockCop1,mockCop2,thief);

    }

    @AfterEach
    void tearDown() {
        gameItems = null;
        rewardList = null;
        hurdleList = null;
        thief = null;
        map = null;
    }

    private static Object[] getThiefMoveValues(){
        return new Object[]{
                new Object[]{Direction.Up,new Point(0,3)},
                new Object[]{Direction.Down,new Point(2,3)},
                new Object[]{Direction.Left,new Point(1,2)},
                new Object[]{Direction.Right,new Point(1,4)},
        };
    }

    /**
     * Integration test - to test correct thief movement on the game map
     * Tests confirms thief occupies position on road, since only road is provided.
     * and moves in given direction correctly,
     * @param direction: Up, down, left, right
     * @param p: location of thief before moving
     */
    @ParameterizedTest
    @MethodSource("getThiefMoveValues")
    void moveThiefValidTest(Direction direction,Point p) {

        assertEquals(new Point(1,3),thief.getLocation());
        map.moveThief(direction);
        assertEquals(p,thief.getLocation());
    }

    private static Object[] getBuildingGardenPositions(){
        return new Object[]{
                new Object[]{new Point(2,3),Direction.Down,new Point(2,3)},
                new Object[]{new Point(2,2),Direction.Down,new Point(2,2)},
                new Object[]{new Point(0,1),Direction.Up,new Point(0,1)},
                new Object[]{new Point(2,4),Direction.Right,new Point(2,4)},
                new Object[]{new Point(1,0),Direction.Left,new Point(1,0)}
        };
    }

    /**
     * To test thief do not occupy position on garden/building/out of map index.
     */
    @ParameterizedTest
    @MethodSource("getBuildingGardenPositions")
    void moveThiefInvalidTest(Point init, Direction direction,Point expected) {
        thief = new Thief(init);
        map.moveThief(direction);
        assertEquals(expected,thief.getLocation());
    }

    private static Object[] getValuesForProcessReward(){
        return new Object[]{
                new Object[]{new Point(0,1),-1},    //Diamond
                new Object[]{new Point(0,2),60},    //Nitro
                new Object[]{new Point(0,3),0},     //No Reward
        };
    }

    /**
     * Unit test - to test the reward value after thief passing through cell containing reward/no reward.
     * @param p: location of the thief
     * @param expected: reward value after thief collecting active/not pre-collected reward
     */
    @ParameterizedTest
    @MethodSource("getValuesForProcessReward")
    void processRewardTest(Point p,int expected) {

        //To test the correct behaviour based on reward location.
        assertEquals(0,map.getRewardValue());
        map.processReward(p);
        assertEquals(expected,map.getRewardValue());
    }

    /**
     * Unit test - to test the reward value after thief pass through the cell containing pre collected reward.
     */
    @Test
    void processCollectedRewardTest(){

        //To ensure same reward is not collected multiple times
        rewardList.get(0).collect();
        assertEquals(0, map.getRewardValue());     //reward value before thief occupying this position
        map.processReward(new Point(0,1));
        assertEquals(0, map.getRewardValue());     //reward value after thief occupying cell of pre-collected reward.
    }

    private static Object[] getValuesForProcessHurdle(){
        return new Object[]{
                new Object[]{new Point(0,3),60},    //spike
                new Object[]{new Point(0,4),-1},    //Pothole
                new Object[]{new Point(1,4),0},     //No hurdle
        };
    }

    /**
     * This is a unit test function - thief passing through pothole, spike and no hurdle cell.
     * @param p: location of thief
     * @param expected: expected penalty value after passing through the given location.
     */
    @ParameterizedTest
    @MethodSource("getValuesForProcessHurdle")
    void processActiveHurdleTest(Point p,int expected) {

        //To test the correct behaviour based on location of hurdle.
        assertEquals(0,map.getPenalty());
        map.processHurdle(p);
        assertEquals(expected,map.getPenalty());
    }

    /**
     * Unit test - to test correct operation of penalty value after hitting inactive hurdle
     */
    @Test
    void processInactiveHurdleTest(){

        //To test the correct behaviour when hurdle is inactive.
        hurdleList.get(0).setInactive();
        assertEquals(0,map.getPenalty());   //penalty value before hitting inactive hurdle
        map.processHurdle(new Point(0,3));
        assertEquals(0,map.getPenalty());   //penalty value remain unchanged
    }

    private static Object[] getCopMoveValues() {
        return new Object[]{
            new Object[]{new Point(1, 1), new Point(1, 3), new Point(1, 2)}, // Cop to Thief without collision
            new Object[]{new Point(2, 1), new Point(2, 3), new Point(2, 1)}, // Cop avoids merging with another cop
            new Object[]{new Point(3, 1), new Point(3, 0), new Point(3, 1)}, // Cop remains stationary due to invalid target
        };
    }
    
    /**
     * Integration test - Ensures cop moves towards thief correctly and avoids invalid locations.
     * @param copStart Starting location of the cop.
     * @param thiefLocation Target location of the thief.
     * @param expected Cop's expected location after moving.
     */
    @ParameterizedTest
    @MethodSource("getCopMoveValues")
    void moveCopValidTest(Point copStart, Point thiefLocation, Point expected) {
        Cop cop = new Cop(copStart);
        thief.setLocation(thiefLocation);
    
        // Simulate moving cop towards thief
        map.moveCopTowards(cop, thief.getLocation(), mockCop2.getLocation());
        assertEquals(expected, cop.getLocation());
    }
    
    /**
     * Unit test for A* pathfinding - Ensures the shortest path is generated for valid targets.
     */
    @Test
    void aStarPathfindingTest() {
        Point start = new Point(1, 1);
        Point target = new Point(1, 3);
    
        List<Point> path = map.aStarPathfinding(start, target);
        assertNotNull(path);
        assertEquals(3, path.size());
        assertEquals(new Point(1, 2), path.get(1));
    }
    
    /**
     * Integration test - Ensures cops avoid merging into the same location.
     */
    @Test
    void avoidCopCollisionTest() {
        Point cop1Start = new Point(1, 1);
        Point cop2Start = new Point(1, 2);
    
        Cop cop1 = new Cop(cop1Start);
        Cop cop2 = new Cop(cop2Start);
    
        map.moveCopTowards(cop1, thief.getLocation(), cop2.getLocation());
        map.moveCopTowards(cop2, thief.getLocation(), cop1.getLocation());
    
        assertNotEquals(cop1.getLocation(), cop2.getLocation());
    }
    
    /**
     * Boundary test - Ensures cops do not move onto invalid tiles.
     */
//    @Test
//    void copInvalidMoveTest() {
//        Point copStart = new Point(3, 1); // In a garden
//        Cop cop = new Cop(copStart);
//
//        map.moveCopTowards(cop, thief.getLocation(), mockCop2.getLocation());
//        assertEquals(copStart, cop.getLocation()); // Cop does not move
//    }

}