package exceptions;

public class WeakPasswordException extends Exception {

    public WeakPasswordException() {
        super("The password is too weak");
    }

    public WeakPasswordException(String message) {
        super(message);
    }
}
