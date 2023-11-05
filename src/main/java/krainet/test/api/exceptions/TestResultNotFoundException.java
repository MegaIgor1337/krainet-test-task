package krainet.test.api.exceptions;

public class TestResultNotFoundException extends RuntimeException {
    public TestResultNotFoundException(String message) {
        super(message);
    }
}
