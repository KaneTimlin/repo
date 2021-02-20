import java.util.ArrayList;

public class NotationStack<T> implements StackInterface<T> {

    private int maxLength;
    private int numOfObjects = 0;
    // an array of type T to store the items of the stack
    T[] stack;

    @SuppressWarnings("unchecked")
    public NotationStack(int maxLength) {
        this.maxLength = maxLength;
        // initialize the array with the proper length
        stack = (T[]) new Object[maxLength];
    }

    /**
     * Checks if the stack is empty
     * @return true if empty, false if not
     */
    @Override
    public boolean isEmpty() {
        return stack[0] == null;
    }

    /**
     * Checks if the stack is full
     * @return true if full, false if not
     */
    @Override
    public boolean isFull() {
        return numOfObjects >= maxLength;
    }

    /**
     * Removes the top item from the stack and returns it
     * @return the top item of the stack
     * @throws StackUnderflowException thrown if the stack is already empty when the method is called
     */
    @Override
    public T pop() throws StackUnderflowException {
        // checks if the stack is empty. If it is, throw an exception
        if (isEmpty()) {
            throw new StackUnderflowException();
        } else {
            T temp = stack[numOfObjects-1]; // make a temporary object to store the top item in the stack
            stack[numOfObjects-1] = null; // delete the reference to that object from the array
            numOfObjects--; // decrement numOfObjects and return the data
            return temp;
        }
    }

    /**
     * Returns the value on the top of the stack without removing it from the stack
     * @return the top item of the stack
     * @throws StackUnderflowException thrown if the stack is already empty when the method is called
     */
    @Override
    public T top() throws StackUnderflowException {
        // check if the stack is empty. If it is, throw an exception
        if (isEmpty()) {
            throw new StackUnderflowException();
        } else {
            return stack[numOfObjects-1]; // return the top item of the array
        }
    }

    /**
     * Checks how many items are on the stack
     * @return the size of the stack
     */
    @Override
    public int size() {
        return numOfObjects;
    }

    /**
     * Pushes an item onto the top of the stack
     * @param e the element to add to the top of the Stack
     * @return true if the operation was successful
     * @throws StackOverflowException thrown if the array is already full when the method is called
     */
    @Override
    public boolean push(T e) throws StackOverflowException {
        if (isFull()) {
            throw new StackOverflowException();
        } else {
            stack[numOfObjects] = e;
            numOfObjects++;
            return true;
        }
    }

    /**
     * Turns the contents of the stack into a string, with each element separated by the delimiter
     * @param delimiter the string that separates each item
     * @return the string representation of the stack
     */
    @Override
    public String toString(String delimiter) {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < numOfObjects; i++) {
            out.append(stack[i]).append(delimiter);
        }
        int lastIndex = out.lastIndexOf(delimiter);
        out.delete(lastIndex, out.length());
        return out.toString();
    }
    /**
     * Turns the contents of the stack into a string
     * @return the string representation of the stack
     */
    public String toString() {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < numOfObjects; i++) {
            out.append(stack[i]);
        }
        return out.toString();
    }
    /**
     * Fills the stack with a given arraylist
     * @param list elements to be added to the stack
     */
    @Override
    @SuppressWarnings("unchecked")
    public void fill(ArrayList<T> list) {
        this.stack = (T[]) new Object[list.size()];
        for (int i = 0; i < this.stack.length; i++) {
            this.stack[i] = list.get(i);
            numOfObjects++;
        }
    }
}
