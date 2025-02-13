package ex5.inspector.checkers;

import ex5.inspector.datacontainers.ContextData;
import ex5.inspector.datacontainers.Variable;

import java.util.List;


/**
 * This class is responsible for checking if a variable Assignment inside a method line is legal
 */
public class VariableAssigmentLineChecker {

    private static final String VARIABLE_DOSENT_EXIST_ERROR = "Line %d: variable '%s' dosen't " +
            "exists across current program scopes";
    private static final String VARIABLE_VALUE_ERROR = "Line %d: variable '%s' value '%s' does " +
            "not exist or dosen't match variable type";
    private static final String VARIABLE_FINAL_ERROR = "Line %d: variable '%s' is final";

    /**
     * a static method that gets variables assignment and checks if its legal
     * @param variables the variables to checks
     * @param contextDataReplica data with current program info
     * @throws IllegalVariableAssigmentException in case of illegal variable assignment, throws exception.
     * @see Variable
     * @see ContextData
     */
    public static void checkVariables(List<Variable> variables, ContextData contextDataReplica)
            throws IllegalVariableAssigmentException {
        for (Variable variable : variables) {
            if (!contextDataReplica.checkIfVariableExist(variable)) {
                throw new IllegalVariableAssigmentException(String.format(VARIABLE_DOSENT_EXIST_ERROR,
                        variable.getLineNumber(), variable.getName()));
            }
            if(contextDataReplica.checkIfVariableIsFinal(variable)) {
                throw new IllegalVariableAssigmentException(String.format(VARIABLE_FINAL_ERROR,
                        variable.getLineNumber(), variable.getName()));
            }
            if (!contextDataReplica.checkIfVariableValueLegal(variable)) {
                throw new IllegalVariableAssigmentException(String.format(VARIABLE_VALUE_ERROR,
                        variable.getLineNumber(), variable.getName(), variable.getValue()));
            }
        }
    }
}
