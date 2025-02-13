package ex5.inspector.checkers;

import ex5.inspector.IllegalLineException;



/**
 * exception for when there is an Illegal method declaration inside a function.
 */
public class IllegalMethodDeclarationException extends IllegalLineException {
    /**
     * Exception constructor
     * @param message message for error
     */
    public IllegalMethodDeclarationException(String message) {
        super(message);
    }
}
