import java.util.ArrayList;

/**
 * @author Kane Timlin
 * @param <T> generic parameter
 */

public class NotationQueue<T> implements QueueInterface<T>{

    private int numOfObjects = 0;
    private int maxObjects;

    // the two reference variables that point to the locations of the first and last nodes in the Queue
    private Node firstNode;
    private Node lastNode;

    public NotationQueue(int maxObjects) {
        this.maxObjects = maxObjects;
    }

    /**
     * Checks if queue is empty
     * @return true if empty, false if not
     */
    @Override
    public boolean isEmpty() {
        return firstNode == null;
    }

    /**
     * Checks if queue is full
     * @return true if full, false if not
     */
    @Override
    public boolean isFull() {
        return numOfObjects >= maxObjects;
    }

    /**
     * Removes an item from the front of the queue and returns it
     * @return the item removed from the queue
     * @throws QueueUnderflowException thrown if the queue is empty when the method is called
     */
    @Override
    public T dequeue() throws QueueUnderflowException {
        // point currentNode to the node you want to work with, in this case the first node in the queue
        Node currentNode = firstNode;
        // check if the queue is empty, if it is, throw an exception
        if (isEmpty()) {
            throw new QueueUnderflowException();
        } else if (firstNode.next == null) { // check if there is only one item left in the queue
            lastNode = null; // set firstNode and lastNode to null, since there are no items left int the queue
            firstNode = null;
            // decrement numOfObjects
            numOfObjects--;
            // return the data
            return currentNode.data;
        } else {
            // set firstNode to the current second node in the queue,
            // effectively removing the first element to be picked up by garbage collection
            firstNode = firstNode.next;
            numOfObjects--; // decrement numOfObjects and return the data
            return currentNode.data;
        }

    }

    /**
     * Finds the size of the queue
     * @return the size of the queue
     */
    @Override
    public int size() {
        return numOfObjects;
    }

    /**
     * Adds an item to the end of the queue
     * @param e the element to add to the end of the Queue
     * @return true if the element was successfully added
     * @throws QueueOverflowException thrown when the queue is already full when the method is called
     */
    @Override
    public boolean enqueue(T e) throws QueueOverflowException {
        // create a new node that contains the data to be added
        Node newNode = new Node(e);
        // check if the queue is full. if it is, throw an exception
        if (isFull()) {
            throw new QueueOverflowException();
        } else if (isEmpty()) { // check if the queue is empty. if it is, it will need special instructions for the first item
            firstNode = lastNode = newNode; // set the first and last node to both point to what is now the only item in the queue
            numOfObjects++; // increment numOfObjects and return true to signal that the item has been successfully added
            return true;
        } else {
            lastNode.next = newNode; // set lastNode.next, previously null, to the new node, adding another item to the chain
            lastNode = newNode; // set last node to what is now the new last item in the queue
            numOfObjects++; // increment numOfObjects and return true to signal the the item has been successfully added
            return true;
        }
    }

    /**
     * Turns the contents of the queue into a string, with each element separated by the delimiter
     * @param delimiter the string that separates each item
     * @return the string representation of the queue
     */
    @Override
    public String toString(String delimiter) {
        // create a StringBuilder to store the string in while it is being constructed
        StringBuilder out = new StringBuilder();
        // create a currentNode object to point to where the trip thru the queue will start
        // in this case, it is the first node
        Node currentNode = firstNode;
        // loop thru the length of the queue
        for (int i = 0; (i < numOfObjects) && (currentNode != null); i++) {
            // append the string representation of the data, followed by the delimiter to the StringBuilder
            out.append(currentNode.data.toString()).append(delimiter);
            // set currentNode to currentNode.next to increment thru the queue
            currentNode = currentNode.next;
        }
        // since the algorithm above will always end with an extra copy of the delimiter on the end,
        // this searches for the last occurrence of the delimiter and removes it
        // This removes having extra spaces or commas at the end of the String representation of the queue
        int lastIndex = out.lastIndexOf(delimiter);
        out.delete(lastIndex, out.length());
        // return the String representation of the queue
        return out.toString();
    }

    /**
     * Turns the contents of the queue into a string
     * @return the string representation of the queue
     */
    // this method operates much the same as the previous one except that it does not add a delimiter
    // and does not remove the delimiter at the end
    public String toString() {
        StringBuilder out = new StringBuilder();
        Node currentNode = firstNode;
        for (int i = 0; (i < numOfObjects) && (currentNode != null); i++) {
            out.append(currentNode.data.toString());
            currentNode = currentNode.next;
        }
        return out.toString();
    }

    /**
     * Fills the queue with a given arraylist
     * @param list elements to be added to the Queue
     */
    @Override
    public void fill(ArrayList<T> list) {
        // set the max size of the queue to be however long the ArrayList is
        maxObjects = list.size();
        // create a new node as a starting point for the first item in the queue
        Node newNode = new Node(list.get(0));
        // set firstNode and lastNode equal to this new node
        firstNode = lastNode = newNode;
        // increment numOfObjects
        numOfObjects++;
        // loop thru the list and add each item to the end of the queue
        // the only reason .enqueue was not used here was because i would have to include some code to deal with exceptions
        // and since those would not occur unless the code was faulty, i decided to use this implementation
        for (int i = 1; i < maxObjects; i++) {
            newNode = new Node(list.get(i));
            lastNode.next = newNode;
            lastNode = newNode;
            numOfObjects++;
        }
    }

    /**
     * The private inner class Node, to hold the data stored in the queue
     */
    private class Node {

        private T data;
        private Node next;

        private Node (T data) {
            this(data, null);
        }

        private Node(T data, Node next) {
            this.next = next;
            this.data = data;
        }
    }

}
