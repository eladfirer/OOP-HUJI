package ex5.inspector.checkers;

import ex5.inspector.datacontainers.ContextData;
import ex5.inspector.datacontainers.Method;
import ex5.inspector.datacontainers.Variable;
import ex5.inspector.datacontainers.VariableTypes;

import java.util.List;

/**
 * This class is responsible for checking if a method call line is legal
 */
public class MethodCallLineChecker {
    private static final String METHOD_NAME_ERROR = "Line %d: Method '%s' doesn't exists in " +
            "program!";
    private static final String METHOD_NUM_ARGUMENTS_ERROR = "Line %d: Method '%s' in line %d was" +
            " " +
            "called with incorrect number of arguments!";
    private static final String METHOD_ARGUMENTS_STATE_ERROR = "Line %d: Method '%s' in line %d " +
            "was called with a variable that is uninitialized or undefined across current program" +
            " " +
            "scopes.";
    private static final String METHOD_ARGUMENTS_TYPE_ERROR = "Line %d: The method '%s' on line " +
            "%d was called with mismatched argument types.";

    /**
     * a static method that gets method call and checks if its legal
     *
     * @param method             the method to check
     * @param contextDataReplica data with current program info
     * @throws IllegalMethodCallException in case of illegal method call, throws exception.
     * @see Method
     * @see Variable
     * @see ContextData
     * @see VariableTypes
     */
    public static void checkMethodCall(Method method, ContextData contextDataReplica)
            throws IllegalMethodCallException {
        Method calledMethod = contextDataReplica.getMethod(method.getName());
        if (calledMethod == null) {
            throw new IllegalMethodCallException(String.format(METHOD_NAME_ERROR,
                    method.getLineNum(), method.getName()));
        }
        if (calledMethod.numArguments() != method.numArguments()) {
            throw new IllegalMethodCallException(String.format(METHOD_NUM_ARGUMENTS_ERROR,
                    method.getLineNum(), method.getName(), calledMethod.getLineNum()));
        }
        if (!contextDataReplica.checkIfVariablesExistAndIntialized(method)) {
            throw new IllegalMethodCallException(String.format(METHOD_ARGUMENTS_STATE_ERROR,
                    method.getLineNum(), method.getName(), calledMethod.getLineNum()));
        }
        List<Variable> calledMethodVariables = calledMethod.getMethodParameters();
        List<Variable> methodVariables = method.getMethodParameters();
        for (int i = 0; i < method.numArguments(); i++) {
            if (!checkForVariableTypeMatch(calledMethodVariables.get(i).getType(),
                    methodVariables.get(i).getType())) {
                throw new IllegalMethodCallException(String.format(METHOD_ARGUMENTS_TYPE_ERROR,
                        method.getLineNum(), method.getName(), calledMethod.getLineNum()));
            }
        }
    }

    private static boolean checkForVariableTypeMatch(VariableTypes calledMethodType,
                                                     VariableTypes methodType) {
        if (methodType == calledMethodType) {
            return true;

        }
        if (calledMethodType == VariableTypes.BOOLEAN) {
            return methodType == VariableTypes.INT ||
                    methodType == VariableTypes.DOUBLE;
        }
        if (calledMethodType == VariableTypes.DOUBLE) {
            return methodType == VariableTypes.INT;
        }
        return false;
    }
}
