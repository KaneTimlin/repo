import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class SortedDoubleLinkedList_STUDENT_Test {

    SortedDoubleLinkedList<String> stringSortedList;

    String a = "First";
    String b = "Second";
    String c = "Third";
    String d = "Fourth";

    StringComparator stringComparator;

    @BeforeEach
    void setUp() {
        stringComparator = new StringComparator();

        stringSortedList = new SortedDoubleLinkedList<>(stringComparator);
    }

    @AfterEach
    void tearDown() {
        stringComparator = null;
        stringSortedList = null;
    }

    @Test
    void iterator() {
        stringSortedList.add(a);
        stringSortedList.add(c);
        stringSortedList.add(d);
        stringSortedList.add(b); // a d b c
        ListIterator<String> stringIterator = stringSortedList.iterator();

        assertTrue(stringIterator.hasNext());
        assertEquals(a, stringIterator.next());
        assertEquals(d, stringIterator.next());
        assertEquals(b, stringIterator.next());
        assertEquals(c, stringIterator.next());
        assertTrue(stringIterator.hasPrevious());
        assertEquals(c, stringIterator.previous());
        assertEquals(b, stringIterator.previous());
        assertEquals(d, stringIterator.previous());
        assertEquals(a, stringIterator.previous());
        assertFalse(stringIterator.hasPrevious());
        try {
            stringIterator.previous();
            fail("Did not throw an exception");
        } catch (NoSuchElementException e) {
            assertEquals("No previous element", e.getMessage());
        } catch (Exception e) {
            fail("Wrong exception thrown");
        }
    }

    @Test
    void add() {
        stringSortedList.add(a);
        stringSortedList.add(d);
        stringSortedList.add(b); // a d b
        assertEquals(b, stringSortedList.getLast());
        stringSortedList.add(c);
        assertEquals(c, stringSortedList.getLast());
    }

    @Test
    void addToEnd() {
        try {
            stringSortedList.addToEnd(a);
        } catch (UnsupportedOperationException e) {
            assertEquals("Invalid operation for sorted list.", e.getMessage());
        }
    }

    @Test
    void addToFront() {
        try {
            stringSortedList.addToFront(a);
        } catch (UnsupportedOperationException e) {
            assertEquals("Invalid operation for sorted list.", e.getMessage());
        }
    }

    @Test
    void remove() {
        stringSortedList.add(a);
        stringSortedList.add(c);
        stringSortedList.add(d);
        stringSortedList.add(b); // a d b c
        assertEquals(a, stringSortedList.getFirst());
        stringSortedList.remove(a, stringComparator);
        assertEquals(d, stringSortedList.getFirst());
    }

    private class StringComparator implements Comparator<String>
    {

        @Override
        public int compare(String arg0, String arg1) {
            return arg0.compareTo(arg1);
        }

    }

}