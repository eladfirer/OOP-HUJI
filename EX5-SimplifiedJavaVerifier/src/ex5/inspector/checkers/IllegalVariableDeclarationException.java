package ex5.inspector.checkers;

import ex5.inspector.IllegalLineException;


/**
 * exception for when there is an illegal variable declaration inside a function.
 */
public class IllegalVariableDeclarationException extends IllegalLineException {
    /**
     * Exception constructor
     * @param message message for error
     */
    public IllegalVariableDeclarationException(String message) {
        super(message);
    }
}
