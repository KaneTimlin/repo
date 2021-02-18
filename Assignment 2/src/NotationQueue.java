import java.util.ArrayList;

public class NotationQueue<T> implements QueueInterface<T>{

    private int numOfObjects = 0;
    private int maxObjects;

    private Node firstNode;
    private Node lastNode;

    public NotationQueue(int maxObjects) {
        this.maxObjects = maxObjects;
    }

    @Override
    public boolean isEmpty() {
        return firstNode == null;
    }

    @Override
    public boolean isFull() {
        return numOfObjects >= maxObjects;
    }

    @Override
    public T dequeue() throws QueueUnderflowException {
        Node currentNode = firstNode;
        if (isEmpty()) {
            throw new QueueUnderflowException();
        } else if (firstNode.next == null) {
            lastNode = null;
            firstNode = null;
            numOfObjects--;
            return currentNode.data;
        } else {
            firstNode = firstNode.next;
            numOfObjects--;
            return currentNode.data;
        }

    }

    @Override
    public int size() {
        return numOfObjects;
    }

    @Override
    public boolean enqueue(T e) throws QueueOverflowException {
        Node newNode = new Node(e);

        if (isFull()) {
            throw new QueueOverflowException();
        } else if (isEmpty()) {
            firstNode = lastNode = newNode;
            numOfObjects++;
            return true;
        } else {
            lastNode.next = newNode;
            lastNode = newNode;
            numOfObjects++;
            return true;
        }
    }

    @Override
    public String toString(String delimiter) {
        StringBuilder out = new StringBuilder();
        Node currentNode = firstNode;
        for (int i = 0; (i < numOfObjects) && (currentNode != null); i++) {
            out.append(currentNode.data.toString()).append(delimiter);
            currentNode = currentNode.next;
        }
        int lastIndex = out.lastIndexOf(delimiter);
        out.delete(lastIndex, out.length());
        return out.toString();
    }

    public String toString() {
        StringBuilder out = new StringBuilder();
        Node currentNode = firstNode;
        for (int i = 0; (i < numOfObjects) && (currentNode != null); i++) {
            out.append(currentNode.data.toString());
            currentNode = currentNode.next;
        }
        return out.toString();
    }

    @Override
    public void fill(ArrayList<T> list) {
        maxObjects = list.size();
        Node newNode = new Node(list.get(0));
        firstNode = lastNode = newNode;
        numOfObjects++;
        for (int i = 1; i < maxObjects; i++) {
            newNode = new Node(list.get(i));
            lastNode.next = newNode;
            lastNode = newNode;
            numOfObjects++;
        }
    }

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
