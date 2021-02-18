import java.util.ArrayList;

public class NotationStack<T> implements StackInterface<T> {

    private int maxLength;
    private int numOfObjects = 0;
    T[] stack;

    @SuppressWarnings("unchecked")
    public NotationStack(int maxLength) {
        this.maxLength = maxLength;
        stack = (T[]) new Object[maxLength];
    }

    @Override
    public boolean isEmpty() {
        return stack[0] == null;
    }

    @Override
    public boolean isFull() {
        return numOfObjects >= maxLength;
    }

    @Override
    public T pop() throws StackUnderflowException {

        if (isEmpty()) {
            throw new StackUnderflowException();
        } else {
            T temp = stack[numOfObjects-1];
            stack[numOfObjects-1] = null;
            numOfObjects--;
            return temp;
        }
    }

    @Override
    public T top() throws StackUnderflowException {
        if (isEmpty()) {
            throw new StackUnderflowException();
        } else {
            return stack[numOfObjects-1];
        }
    }

    @Override
    public int size() {
        return numOfObjects;
    }

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

    public String toString() {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < numOfObjects; i++) {
            out.append(stack[i]);
        }
        return out.toString();
    }

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
