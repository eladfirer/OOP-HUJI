package ex5.inspector;

import java.io.IOException;

/**
 * exception for when there is an illegal line inside a function.
 */
public class IllegalLineException extends IOException {
    /**
     * Exception constructor
     * @param message message for error
     */
    public IllegalLineException(String message) {
        super(message);
    }
}
