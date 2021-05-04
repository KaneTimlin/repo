import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class Town_STUDENT_Test {

    Town town1;
    Town town2;

    @BeforeEach
    void setUp() {

        town1 = new Town("Town1");
        town2 = new Town("Town2");

    }

    @AfterEach
    void tearDown() {
        town1 = town2 = null;
    }

    @Test
    void addAdjacentTown() {
        town1.addAdjacentTown(town2);
        ArrayList<Town> adjacentTowns = town1.getAdjacentTowns();
        assertTrue(adjacentTowns.contains(town2));
    }

    @Test
    void removeAdjacentTown() {
        town1.addAdjacentTown(town2);
        town1.addAdjacentTown(new Town ("Town3"));
        ArrayList<Town> adjacentTowns = town1.getAdjacentTowns();
        assertTrue(adjacentTowns.contains(town2));
        town1.removeAdjacentTown(town2);
        adjacentTowns = town1.getAdjacentTowns();
        assertFalse(adjacentTowns.contains(town2));
    }

    @Test
    void compareTo() {
        Town town1Clone = new Town("Town1");
        assertEquals(0, town1.compareTo(town1Clone));
        assertEquals(-1, town1.compareTo(town2));
    }

    @Test
    void testEquals() {
        Town town1Clone = new Town("Town1");
        assertTrue(town1.equals(town1Clone));
    }

    @Test
    void testHashCode() {
        assertEquals(town1.getName().hashCode(), town1.hashCode());
    }

    @Test
    void testToString() {
        assertEquals(town2.getName(), town2.toString());
    }
}