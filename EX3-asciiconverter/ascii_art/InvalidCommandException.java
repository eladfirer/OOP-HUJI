package ascii_art;

/**
 * InvalidCommandException - user command is invalid
 */
public class InvalidCommandException extends RuntimeException {
    /**
     * Constructor for exception
     * @param message - message to read
     */
    public InvalidCommandException(String message) {
        super(message);
    }
}
