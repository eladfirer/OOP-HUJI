package ex5.inspector.checkers;

import ex5.inspector.IllegalLineException;


/**
 * exception for when there is an illegal variable Assigment inside a function.
 */
public class IllegalVariableAssigmentException extends IllegalLineException {
    /**
     * Exception constructor
     * @param message message for error
     */
    public IllegalVariableAssigmentException(String message) {
        super(message);
    }
}
