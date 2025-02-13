package ex5.inspector.checkers;

import ex5.inspector.datacontainers.ContextData;
import ex5.inspector.datacontainers.Variable;

import java.util.List;

/**
 * This class is responsible for checking if a condition line variables are legal
 */
public class ConditionLineChecker {

    private static final String CONDITION_ERROR = "Line %d: Parameter %s is either " +
            "uninitialized, undefined, or of an incorrect type across current program scopes.";

    /**
     * a static method that gets variables of condition line and checks if they are legal
     * @param variables variables to check
     * @param contextData data with current program info
     * @throws IllegalConditionException in case of illegal variables, throws exception.
     * @see ContextData
     * @see Variable
     */
    public static void checkVariables(List<Variable> variables, ContextData contextData)
            throws IllegalConditionException {
        for (Variable variable : variables) {
            if (!contextData.checkIfVariableValueExistsAndMatches(variable)) {
                throw new IllegalConditionException(String.format(CONDITION_ERROR,
                        variable.getLineNumber(), variable.getName()));
            }
        }
    }
}
