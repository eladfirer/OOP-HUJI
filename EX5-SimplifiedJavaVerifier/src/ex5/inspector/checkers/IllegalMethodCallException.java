package ex5.inspector.checkers;

import ex5.inspector.IllegalLineException;


/**
 * exception for when there is an Illegal method call inside a function.
 */
public class IllegalMethodCallException extends IllegalLineException {
    /**
     * Exception constructor
     * @param message message for error
     */
    public IllegalMethodCallException(String message) {
        super(message);
    }
}
