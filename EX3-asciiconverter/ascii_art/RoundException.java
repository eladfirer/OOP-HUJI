package ascii_art;

/**
 * Round exception - problem with round parameter
 */
public class RoundException extends RuntimeException {
    /**
     * Constructor for exception
     * @param message - message to read
     */
    public RoundException(String message) {
        super(message);
    }
}
