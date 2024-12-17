package ascii_art;

/**
 * Output Exception - type of output is illegal
 */
public class OutputException extends RuntimeException {

    /**
     * Constructor for exception
     * @param message - message to read
     */
    public OutputException(String message) {
        super(message);
    }
}
