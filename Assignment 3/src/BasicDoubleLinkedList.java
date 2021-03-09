import java.util.ArrayList;
import java.util.Comparator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * @author Kane Timlin
 * @param <T> the type of object the class should store
 */

public class BasicDoubleLinkedList<T> implements Iterable<T>{

    protected Node firstNode;
    protected Node lastNode;
    protected int size;


    public BasicDoubleLinkedList() {
        lastNode = new Node(); // init lastNode as dummy node, have to use lastNode.prev as lastNode for all non-iterative operations
                               // iterative operations do not work otherwise
        size = 0;
    }

    /**
     * A method to get the number of stored objects
     * @return the number of stored objects
     */
    public int getSize() {
        return size;
    }

    /**
     * A method to add an object to the end of the list
     * @param data the object to be added
     */
    public void addToEnd(T data) {
        Node newNode = new Node(data); // init the object as a new node
        if (size == 0) { // if this is the first object in the list, it needs to be initialized
            firstNode = lastNode.prev = newNode;
            firstNode.next = lastNode;
        } else {
            lastNode.prev.next = newNode; // otherwise, link the node to the end of the list
            newNode.prev = lastNode.prev;
            lastNode.prev = newNode;
            newNode.next = lastNode;

        }
        size++; // increment size
    }

    /**
     * A method to add an object to the front of the list
     * @param data the object to be added
     */
    public void addToFront(T data) {
        Node newNode = new Node(data); // init the object as a new node
        if (size == 0) { // initialize the list if this is the first object
            firstNode = lastNode.prev = newNode;
            firstNode.next = lastNode;
        } else {
            firstNode.prev = newNode; // link the newNode into the beginning of the list
            newNode.next = firstNode;
            firstNode = firstNode.prev;
        }
        size++; // increment size
    }

    /**
     * A method to get the first object in the list
     * @return the first object in the list
     */
    public T getFirst() {
        return firstNode.data;
    }

    /**
     * A method to get the last object in the list
     * @return the last object in the list
     */
    public T getLast() {
        return lastNode.prev.data;
    }

    /**
     * The overridden method to return the iterator for this specific list, since Iterable has been implemented
     * @return the iterator, which implements ListIterator
     */
    @Override
    public ListIterator<T> iterator() {
        return new LinkedListIterator();
    }

    /**
     * A method to remove a specified
     * @param data the object to be removed
     * @param comparator a comparator object for the type T, allowing us to search the list without knowing the type of the object
     * @return the removed object
     */
    public T remove(T data, Comparator<T> comparator) {
        Node currentNode = firstNode;
        while (currentNode.next != null) { // it is necessary to check currentNode.next for being null instead of just currentNode
                                           // because of the dummy node at the end of the list
            if (comparator.compare(currentNode.data, data) == 0) {
                if (currentNode.prev == null) { // code to insert the node at the beginning of the list
                    T temp = firstNode.data;
                    firstNode = firstNode.next;
                    firstNode.prev = null;
                    size--;
                    return temp;
                } else {
                    // since there is a dummy node at the end of the list, code to run specifically when a node is being inserted at the end
                    // is not necessary since it would be the same as inserting a node in between two others
                    currentNode.prev.next = currentNode.next;
                    currentNode.next.prev = currentNode.prev;
                    size--;
                    return currentNode.data;
                }
            }
            currentNode = currentNode.next; // increment to the next node in the list
        }
        return null; // return null if the data to remove was not found in the list
    }

    /**
     * A method to remove and return the first item in the list
     * @return the first item in the list
     */
    public T retrieveFirstElement() {
        T temp = firstNode.data;
        if (size <= 1) { // if this is the last item in the list, return the list to the position it was in when first instantiated
            firstNode = lastNode.prev = null;
        } else { // otherwise, link the firstNode variable to the next position in the list and unlink the previous first node
            firstNode = firstNode.next;
            firstNode.prev = null;
        }
        size--; // decrement size and return the data
        return temp;
    }

    /**
     * A method to remove and return the last item in the list
     * @return the last item in the list
     */
    public T retrieveLastElement() {
        T temp = lastNode.prev.data; // this method operates similar to retrieveFirstElement
        if (size <= 1) {
            firstNode = lastNode.prev = null;
        } else {
            lastNode.prev = lastNode.prev.prev;
            lastNode.prev.next = null;
        }
        size--;
        return temp;
    }

    /**
     * A method to covert the LinkedList to an ArrayList
     * @return the ArrayList representation of the LinkedList
     */
    public ArrayList<T> toArrayList() {
        Node currentNode = firstNode;
        ArrayList<T> out = new ArrayList<>(size);
        while (currentNode.next != null) {
            out.add(currentNode.data);
            currentNode = currentNode.next;
        }
        return out;
    }

    /**
     * @author Kane Timlin
     */
    protected class Node {

        protected T data;
        protected Node next;
        protected Node prev;

        /**
         * A dummy constructor, for making an empty node with no connections or data stored
         */
        protected Node() { // dummy constructor

        }

        /**
         * A constructor for making a node with data but no connections
         * @param data the data to be stored in the node
         */
        protected Node (T data) {
            this.data = data;
        }

        /**
         * A constructor for making a node with data and both connections
         * @param data the data to be stored in the node
         * @param next the next node in the list
         * @param prev the previous node in the list
         */
        protected Node(T data, Node next, Node prev) {
            this.next = next;
            this.data = data;
            this.prev = prev;
        }

    }

    /**
     * @author Kane Timlin
     */
    protected class LinkedListIterator implements ListIterator<T> {
        private Node nextNode;

        /**
         * A constructor to set up the iterator when it is initialized
         */
        public LinkedListIterator() {
            nextNode = firstNode;
        }

        /**
         * A method to find whether the current node has another one following it
         * @return true if the node has a next, false if not
         */
        @Override
        public boolean hasNext() {
            try {
                return nextNode.next != null;
            } catch (NullPointerException e ) {
                return false;
            }
        }

        /**
         * A method to move the iterator cursor to the next item in the list and return the item it just crossed over
         * @return The item the cursor just crossed over
         * @throws NoSuchElementException if there is no item to return
         */
        @Override
        public T next() throws NoSuchElementException {
            if (!hasNext()) {
                throw new NoSuchElementException("No next element");
            }
            T temp = nextNode.data;
            nextNode = nextNode.next;
            return temp;
        }

        /**
         * A method to find whether nextNode has one behind it
         * @return true if nextNode has a previous node, fale if not
         */
        @Override
        public boolean hasPrevious() {
            try {
                return nextNode.prev != null;
            } catch (NullPointerException e ) {
                return false;
            }
        }

        /**
         * A method to move the iterator cursor back an item in the list and return the item it just crossed over
         * @return the item the cursor just passed over
         * @throws NoSuchElementException if there is no item behind the cursor in the list
         */
        @Override
        public T previous() throws NoSuchElementException {
            if (!hasPrevious()) {
                throw new NoSuchElementException("No previous element");
            }
            nextNode = nextNode.prev;
            return nextNode.data;
        }

        @Override
        public int nextIndex() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int previousIndex() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void set(T t) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(T t) {
            throw new UnsupportedOperationException();
        }
    }

}
