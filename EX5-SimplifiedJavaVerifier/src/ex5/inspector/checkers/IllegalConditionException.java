package ex5.inspector.checkers;

import ex5.inspector.IllegalLineException;


/**
 * exception for when there is an Illegal condition inside a function.
 */
public class IllegalConditionException extends IllegalLineException {
    /**
     * Exception constructor
     * @param message message for error
     */
    public IllegalConditionException(String message) {
        super(message);
    }
}
