import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Road_STUDENT_Test {

    Town town1, town2, town3, town4;
    Road road1, road2;

    @BeforeEach
    void setUp() {
        town1 = new Town("Town1");
        town2 = new Town("Town2");
        town3 = new Town("Town3");
        town4 = new Town("Town4");

        road1 = new Road(town1, town2, 4, "Road1");
        road2 = new Road(town3, town4, 2, "Road2");
    }

    @AfterEach
    void tearDown() {
        town1 = town2 = town3 = town4 = null;
        road1 = road2 = null;
    }

    @Test
    void compareTo() {
        assertEquals(-1, road1.compareTo(road2));
    }

    @Test
    void contains() {
        assertTrue(road1.contains(town1));
        assertTrue(road2.contains(town4));
    }

    @Test
    void testEquals() {
        Road road1Copy = new Road(road1);
        assertEquals(road1Copy, road1);
        assertNotEquals(road1, road2);
    }

    @Test
    void isEmpty() {
        Road emptyRoad = new Road();
        assertEquals("Empty", emptyRoad.getName());
        assertTrue(emptyRoad.isEmpty());
    }

    @Test
    void testToString() {
        assertEquals("Road1", road1.toString());
    }
}