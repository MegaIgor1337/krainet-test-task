package krainet.test.api.exceptions;

public class DirectionNotFoundException extends RuntimeException {
    public DirectionNotFoundException(String message) {
        super(message);
    }
}
