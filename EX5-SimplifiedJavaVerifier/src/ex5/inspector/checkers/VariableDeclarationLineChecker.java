package ex5.inspector.checkers;

import ex5.inspector.datacontainers.ContextData;
import ex5.inspector.datacontainers.Variable;

import java.util.List;
/**
 * This class is responsible for checking if a variable Declaration is legal
 */
public class VariableDeclarationLineChecker {

    private static final String ALREADY_DECLARED_ERROR = " is already declared as a variable in " +
            "current scope";
    private static final String LINE = "In line ";
    private static final String FINAL_ERROR = " is final and can't be uninitialized";
    private static final String VALUE_ERROR = " is either uninitialized, undefined, or of an " +
            "incorrect type across current program scopes.";

    /**
     * a static method that gets variables in a declaration and checks if its legal
     * @param variables the variables to checks
     * @param contextData data with current program info
     * @throws IllegalVariableDeclarationException in case of illegal variable declaration, throws exception.
     * @see Variable
     * @see ContextData
     */
    public static void checkVariables(List<Variable> variables, ContextData contextData)
            throws IllegalVariableDeclarationException {
        for (Variable variable : variables) {
            if (variable.isFinal() && !variable.hasValue()) {
                throw new IllegalVariableDeclarationException(LINE + variable.getLineNumber() +
                        " " + variable.getName() + FINAL_ERROR);
            }
            if (contextData.checkIfVariableAlreadyDeclaredInScope(variable)) {
                throw new IllegalVariableDeclarationException(LINE + variable.getLineNumber()
                        + " " + variable.getName() + ALREADY_DECLARED_ERROR);
            }
            if (variable.isCheckForValueAssigment() &&
                    !contextData.checkIfVariableValueExistsAndMatches(variable)) {
                throw new IllegalVariableDeclarationException(LINE + variable.getLineNumber()
                        + " " + variable.getValue() + VALUE_ERROR);
            }
            contextData.AddVariable(variable);

        }
    }
}
