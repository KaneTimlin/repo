import java.util.Comparator;
import java.util.ListIterator;

public class SortedDoubleLinkedList<T> extends BasicDoubleLinkedList<T>{
    Comparator<T> comparator;

    /**
     * A basic constructor that initializes the comparator for the list
     * @param comparator the comparator of the same type of the list
     */
    public SortedDoubleLinkedList(Comparator<T> comparator) {
        super();
        this.comparator = comparator;
    }

    /**
     * A method to add an item to the list, in the correct position to keep the list sorted
     * @param data the item to be added
     */
    public void add(T data) {
        if (size == 0) { // if this is the first item being added, the list needs to be instantiated
            firstNode = lastNode.prev = new Node(data, lastNode, null);
            size++;
            return;
        }
        Node currentNode = firstNode;
        while (currentNode.next != null) { // loop thru the list until an object that is larger than or equal to the data is found
            if (comparator.compare(currentNode.data, data) >= 0) { // then insert the data behind that item
                if (currentNode.prev == null) { // if the item is being inserted in the first position in the list
                    firstNode.prev = new Node(data, firstNode, null); // use this procedure
                    firstNode = firstNode.prev;
                } else { // if the item is being inserted in the middle of the list, use this procedure
                    Node newNode = new Node(data, currentNode, currentNode.prev);
                    currentNode.prev.next = newNode;
                    currentNode.prev = newNode;
                }
                size++; // increment size and return from the method
                return;
            }
            currentNode = currentNode.next; // increment currentNode and continue searching
        }
        Node newNode = new Node(data, lastNode, lastNode.prev); // if the whole list was searched without finding a place for the data
        lastNode.prev.next = newNode; // the data needs to be inserted at the end, so this method is used
        lastNode.prev = newNode;
        size++;
    }

    /**
     * This method is not supported by this class, so it needs to be overwritten and replaced with an exception
     * @param data the object to be added
     */
    @Override
    public void addToEnd(T data) {
        throw new UnsupportedOperationException("Invalid operation for sorted list.");
    }
    /**
     * This method is not supported by this class, so it needs to be overwritten and replaced with an exception
     * @param data the object to be added
     */
    @Override
    public void addToFront(T data) {
        throw new UnsupportedOperationException("Invalid operation for sorted list.");
    }

    /**
     * the iterator method for this class just uses the same method from the super class
     * @return the iterator, which implements ListIterator
     */
    @Override
    public ListIterator<T> iterator() {
        return super.iterator();
    }

    /**
     * The remove method for this class just uses the same method from the super class
     * @param data the object to be removed
     * @param comparator a comparator object of the type T, allowing us to search the list without knowing the type of the object
     * @return the data that was removed
     */
    @Override
    public T remove(T data, Comparator<T> comparator) {
        return super.remove(data, comparator);
    }


}
