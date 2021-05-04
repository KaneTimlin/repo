import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TownGraphManager_STUDENT_Test {

    private TownGraphManager graph;
    private String[] town;

    @BeforeEach
    void setUp() {
        graph = new TownGraphManager();
        town = new String[6];

        for (int i = 0; i < town.length; i++) {
            town[i] ="Town" + i;
            graph.addTown(town[i]);
        }

        graph.addRoad(town[0], town[1], 2, "Road1");
        graph.addRoad(town[0], town[2], 1, "Road2");
        graph.addRoad(town[1], town[2], 7, "Road3");
        graph.addRoad(town[2], town[3], 7, "Road4");
        graph.addRoad(town[4], town[3], 8, "Road5");
        graph.addRoad(town[1], town[4], 8, "Road6");
        graph.addRoad(town[1], town[5], 4, "Road7");
        graph.addRoad(town[5], town[4], 5, "Road8");
        graph.addRoad(town[5], town[3], 4, "Road9");
        graph.addRoad(town[5], town[2], 3, "Road10");

    }

    @AfterEach
    void tearDown() {
        graph = null;
        town = null;
    }

    @Test
    void addRoad() {
        ArrayList<String> roads = graph.allRoads();
        assertEquals("Road1", roads.get(0));
        assertEquals("Road10", roads.get(1));
        assertEquals("Road2", roads.get(2));
        assertEquals("Road3", roads.get(3));
        graph.addRoad(town[4], town[2], 1,"Road12");
        roads = graph.allRoads();
        assertEquals("Road1", roads.get(0));
        assertEquals("Road10", roads.get(1));
        assertEquals("Road12", roads.get(2));
        assertEquals("Road2", roads.get(3));
        assertEquals("Road3", roads.get(4));
    }

    @Test
    void getRoad() {
        assertEquals("Road1", graph.getRoad(town[1], town[0]));
        assertEquals("Road4", graph.getRoad(town[2], town[3]));
    }

    @Test
    void addTown() {
        assertFalse(graph.containsTown("Town6"));
        graph.addTown("Town6");
        assertTrue(graph.containsTown("Town6"));
    }

    @Test
    void containsTown() {
        assertTrue(graph.containsTown(town[4]));
    }

    @Test
    void containsRoadConnection() {
        assertTrue(graph.containsRoadConnection(town[1], town[4]));
        assertFalse(graph.containsRoadConnection(town[0], town[5]));
    }

    @Test
    void allRoads() {
        ArrayList<String> roadArrayList = graph.allRoads();
        assertEquals("Road2", roadArrayList.get(2));
        assertEquals("Road3", roadArrayList.get(3));
        assertEquals("Road10", roadArrayList.get(1));
    }

    @Test
    void deleteRoadConnection() {
        assertTrue(graph.containsRoadConnection(town[2], town[5]));
        graph.deleteRoadConnection(town[2], town[5], "Road10");
        assertFalse(graph.containsRoadConnection(town[2], town[5]));
    }

    @Test
    void deleteTown() {
        assertTrue(graph.containsTown(town[5]));
        graph.deleteTown(town[5]);
        assertFalse(graph.containsTown(town[5]));
        assertFalse(graph.containsRoadConnection(town[5], town[2]));
        assertFalse(graph.containsRoadConnection(town[2], town[5]));
        assertFalse(graph.containsRoadConnection(town[5], town[1]));
        assertFalse(graph.containsRoadConnection(town[1], town[5]));
    }

    @Test
    void allTowns() {
        ArrayList<String> towns = graph.allTowns();
        assertEquals("Town0", towns.get(0));
        assertEquals("Town1", towns.get(1));
        assertEquals("Town2", towns.get(2));
        assertEquals("Town3", towns.get(3));
    }

    @Test
    void getPathA() {
        ArrayList<String> path = graph.getPath(town[0], town[5]);
        assertEquals("Town0 via Road2 to Town2 1 mi", path.get(0));
        assertEquals("Town2 via Road10 to Town5 3 mi", path.get(1));
    }

    @Test
    void getPathB() {
        ArrayList<String> path = graph.getPath(town[4], town[2]);
        assertEquals("Town4 via Road8 to Town5 5 mi", path.get(0));
        assertEquals("Town5 via Road10 to Town2 3 mi", path.get(1));
    }


    @Test
    void populateTownGraph() {
        String userDirectory = System.getProperty("user.dir");
        File file = new File(userDirectory + "\\src\\MD Towns.txt");
        try {
            graph.populateTownGraph(file);
        } catch (IOException e) {
            e.printStackTrace();
            fail("File not found");
        }
        assertTrue(graph.containsTown("Germantown"));
        assertTrue(graph.containsTown("Boyds"));
        graph.containsRoadConnection("Clarksburg", "Germantown");
    }
}