package ascii_art;

/**
 * CharsetException - excpetion for when there is a problem with charSet
 */
class CharsetException extends Exception {
    /**
     * Constructor for exception
     * @param message - message to read
     */
    public CharsetException(String message) {
        super(message);
    }
}
