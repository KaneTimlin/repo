import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.zip.DeflaterOutputStream;

import static org.junit.jupiter.api.Assertions.*;

class BasicDoubleLinkedList_STUDENT_Test {

    BasicDoubleLinkedList<Double> doubleBasicList;
    DoubleComparator doubleComparator;


    double num1 = 1.0;
    double num2 = 3.6;
    double num3 = 66.3;
    double num4 = 41.5;

    @BeforeEach
    void setUp() {
        doubleBasicList = new BasicDoubleLinkedList<>();
        doubleComparator = new DoubleComparator();
    }

    @AfterEach
    void tearDown() {
        doubleBasicList = null;
        doubleComparator = null;
    }

    @Test
    void iterator() {
        doubleBasicList.addToEnd(num1);
        doubleBasicList.addToEnd(num2);
        doubleBasicList.addToEnd(num4);
        doubleBasicList.addToEnd(num3);
        ListIterator<Double> doubleIterator = doubleBasicList.iterator();
        assertTrue(doubleIterator.hasNext());
        assertEquals(num1, doubleIterator.next());
        assertEquals(num2, doubleIterator.next());
        assertEquals(num4, doubleIterator.next());
        assertEquals(num3, doubleIterator.next());
        assertFalse(doubleIterator.hasNext());
        assertEquals(num3, doubleIterator.previous());
        assertEquals(num4, doubleIterator.previous());
        assertEquals(num2, doubleIterator.previous());
        assertEquals(num1, doubleIterator.previous());
        assertFalse(doubleIterator.hasPrevious());
        try {
            doubleIterator.previous();
            fail("Did not throw an exception");
        } catch (NoSuchElementException e) {
            assertEquals("No previous element", e.getMessage());
        } catch (Exception e) {
            fail("Wrong exception thrown");
        }
    }

    @Test
    void addToEnd() {
        doubleBasicList.addToEnd(num3);
        assertEquals(num3, doubleBasicList.getLast());
        doubleBasicList.addToEnd(num2);
        assertEquals(num2, doubleBasicList.getLast());
        doubleBasicList.addToEnd(num1);
        assertEquals(num1, doubleBasicList.getLast());
        assertEquals(num3, doubleBasicList.getFirst());
    }

    @Test
    void addToFront() {
        doubleBasicList.addToFront(num3);
        assertEquals(num3, doubleBasicList.getFirst());
        doubleBasicList.addToFront(num2);
        assertEquals(num2, doubleBasicList.getFirst());
        doubleBasicList.addToFront(num1);
        assertEquals(num1, doubleBasicList.getFirst());
        assertEquals(num3, doubleBasicList.getLast());
    }


    @Test
    void remove() {
        doubleBasicList.addToEnd(num3);
        doubleBasicList.addToEnd(num1);
        doubleBasicList.addToEnd(num4);
        doubleBasicList.addToEnd(num2);
        assertEquals(num2, doubleBasicList.getLast());
        doubleBasicList.remove(num2, doubleComparator);
        assertEquals(num4, doubleBasicList.getLast());
        doubleBasicList.remove(num3, doubleComparator);
        assertEquals(num1, doubleBasicList.getFirst());
    }

    @Test
    void retrieveFirstElement() {
        doubleBasicList.addToEnd(num3);
        doubleBasicList.addToEnd(num2);
        doubleBasicList.addToEnd(num4);
        doubleBasicList.addToEnd(num3);
        assertEquals(num3, doubleBasicList.retrieveFirstElement());
        assertEquals(num2, doubleBasicList.retrieveFirstElement());
        assertEquals(num4, doubleBasicList.retrieveFirstElement());
        assertEquals(num3, doubleBasicList.retrieveFirstElement());
    }

    @Test
    void retrieveLastElement() {
        doubleBasicList.addToEnd(num2);
        doubleBasicList.addToEnd(num4);
        doubleBasicList.addToFront(num1);
        doubleBasicList.addToEnd(num3);
        assertEquals(num3, doubleBasicList.retrieveLastElement());
        assertEquals(num4, doubleBasicList.retrieveLastElement());
        assertEquals(num2, doubleBasicList.retrieveLastElement());
        assertEquals(num1, doubleBasicList.retrieveLastElement());
    }

    @Test
    void toArrayList() {
        doubleBasicList.addToEnd(num3);
        doubleBasicList.addToEnd(num1);
        doubleBasicList.addToEnd(num4);
        doubleBasicList.addToEnd(num3);

        ArrayList<Double> doubleList = new ArrayList<>(4);
        doubleList.add(num3);
        doubleList.add(num1);
        doubleList.add(num4);
        doubleList.add(num3);
        assertEquals(doubleList, doubleBasicList.toArrayList());
    }

    private class DoubleComparator implements Comparator<Double>
    {

        @Override
        public int compare(Double arg0, Double arg1) {
            return arg0.compareTo(arg1);
        }

    }

}