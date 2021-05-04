import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class Graph_STUDENT_Test {

    private Graph graph;
    private Town[] town;

    @BeforeEach
    void setUp() {
        graph = new Graph();
        town = new Town[6];

        for (int i = 0; i < town.length; i++) {
            town[i] = new Town("Town" + i);
            graph.addVertex(town[i]);
        }

        graph.addEdge(town[0], town[1], 2, "Road1");
        graph.addEdge(town[0], town[2], 1, "Road2");
        graph.addEdge(town[1], town[2], 7, "Road3");
        graph.addEdge(town[2], town[3], 7, "Road4");
        graph.addEdge(town[4], town[3], 8, "Road5");
        graph.addEdge(town[1], town[4], 8, "Road6");
        graph.addEdge(town[1], town[5], 4, "Road7");
        graph.addEdge(town[5], town[4], 5, "Road8");
        graph.addEdge(town[5], town[3], 4, "Road9");
        graph.addEdge(town[5], town[2], 3, "Road10");

    }

    @AfterEach
    void tearDown() {
        graph = null;
        town = null;
    }

    @Test
    void getEdge() {
        assertEquals(new Road(town[3], town[2], 7, "Road4"), graph.getEdge(town[2], town[3]));
        assertEquals(new Road(town[5], town[2], 3, "Road10"), graph.getEdge(town[5], town[2]));
        assertNull(graph.getEdge(town[0], town[5]));
    }

    @Test
    void addEdge() {
        assertFalse(graph.containsEdge(town[2], town[4]));
        graph.addEdge(town[2], town[4], 1, "Road11");
        assertTrue(graph.containsEdge(town[2], town[4]));
    }

    @Test
    void addVertex() {
        Town newTown = new Town("Town6");
        assertFalse(graph.containsVertex(newTown));
        graph.addVertex(newTown);
        assertTrue(graph.containsVertex(newTown));
    }

    @Test
    void containsEdge() {
        assertTrue(graph.containsEdge(town[5], town[4]));
        assertFalse(graph.containsEdge(town[0], town[3]));
    }

    @Test
    void containsVertex() {
        assertTrue(graph.containsVertex(town[4]));
        assertFalse(graph.containsVertex(new Town("Not In Graph")));
    }

    @Test
    void edgeSet() {
        Set<Road> roads = graph.edgeSet();
        ArrayList<String> roadArrayList = new ArrayList<String>();
        for(Road road : roads)
            roadArrayList.add(road.getName());
        Collections.sort(roadArrayList);
        assertEquals("Road2", roadArrayList.get(2));
        assertEquals("Road3", roadArrayList.get(3));
        assertEquals("Road10", roadArrayList.get(1));
    }

    @Test
    void edgesOf() {
        Set<Road> roads = graph.edgesOf(town[1]);
        ArrayList<String> roadArrayList = new ArrayList<String>();
        for(Road road : roads)
            roadArrayList.add(road.getName());
        Collections.sort(roadArrayList);
        assertEquals("Road3", roadArrayList.get(1));
        assertEquals("Road6", roadArrayList.get(2));
        assertEquals("Road7", roadArrayList.get(3));
    }

    @Test
    void removeEdge() {
        assertTrue(graph.containsEdge(town[2], town[5]));
        graph.removeEdge(town[2], town[5], 3, "Road10");
        assertFalse(graph.containsEdge(town[2], town[5]));
    }

    @Test
    void removeVertex() {
        assertTrue(graph.containsVertex(town[5]));
        graph.removeVertex(town[5]);
        assertFalse(graph.containsVertex(town[5]));
        assertFalse(graph.containsEdge(town[5], town[2]));
        assertFalse(graph.containsEdge(town[2], town[5]));
        assertFalse(graph.containsEdge(town[5], town[1]));
        assertFalse(graph.containsEdge(town[1], town[5]));
    }

    @Test
    void vertexSet() {
        Set<Town> townSet = graph.vertexSet();
        assertTrue(townSet.contains(town[0]));
        assertTrue(townSet.contains(town[1]));
        assertTrue(townSet.contains(town[2]));
        assertTrue(townSet.contains(town[3]));
        assertTrue(townSet.contains(town[4]));
        assertTrue(townSet.contains(town[5]));
    }

    @Test
    void testTown0ToTown4() {
        ArrayList<String> path = graph.shortestPath(new Town(town[0]), new Town(town[4]));
        assertNotNull(path);
        assertTrue(path.size() > 0);
        assertEquals("Town0 via Road2 to Town2 1 mi", path.get(0));
        assertEquals("Town2 via Road10 to Town5 3 mi", path.get(1));
        assertEquals("Town5 via Road8 to Town4 5 mi", path.get(2));
    }

    @Test
    void testTown1ToTown2() {
        ArrayList<String> path = graph.shortestPath(new Town(town[1]), new Town(town[2]));
        assertNotNull(path);
        assertTrue(path.size() > 0);
        assertEquals("Town1 via Road1 to Town0 2 mi", path.get(0));
        assertEquals("Town0 via Road2 to Town2 1 mi", path.get(1));
    }

    @Test
    void getTownIndex() {
        Town clarksburg = new Town("Clarksburg");
        Town boyds = new Town("Boyds");
        graph.addVertex(clarksburg);
        int index = graph.getTownIndex(clarksburg);
        assertEquals(45, index);
    }

    @Test
    void getNewTownIndex() {
        Town clarksburg = new Town("Clarksburg");
        Town boyds = new Town("Boyds");
        graph.addVertex(clarksburg);
        int index = graph.getNewTownIndex(boyds);
        assertEquals(30, index);
    }
}