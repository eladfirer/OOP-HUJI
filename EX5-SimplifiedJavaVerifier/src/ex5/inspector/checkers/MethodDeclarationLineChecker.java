package ex5.inspector.checkers;

import ex5.inspector.datacontainers.ContextData;
import ex5.inspector.datacontainers.Method;
import ex5.inspector.datacontainers.Variable;

import java.util.List;

/**
 * This class is responsible for checking if a method declaration inside a method line is legal
 */
public class MethodDeclarationLineChecker {

    private static final String USED_ERROR = " is already been used before in program";
    private static final String METHOD_NAME = "The method name: ";
    private static final String LINE = " in line ";
    private static final String THE_METHOD = "The method ";
    private static final String GLOBAL_SCOPE_ERROR = " is not in global scope!";
    private static final String METHOD_ARGUMENTS_ERROR = "Line %d: The method '%s' on line " +
            "was declared with argument name '%s' more than once";

    /**
     * a static method that gets method declaration and checks if its legal
     *
     * @param method  the method to check
     * @param context data with current program info
     * @throws IllegalMethodDeclarationException in case of illegal method declaration, throws
     * exception.
     * @see Method
     * @see Variable
     * @see ContextData
     */
    public static void checkMethod(Method method, ContextData context)
            throws IllegalMethodDeclarationException {
        if (context.checkIfMethodWithSameNameExists(method)) {
            throw new IllegalMethodDeclarationException(METHOD_NAME +
                    method.getName() + LINE + method.getLineNum() +
                    USED_ERROR);
        }
        if (context.getCurrentScope() != 0) {
            throw new IllegalMethodDeclarationException(THE_METHOD +
                    method.getName() + LINE + method.getLineNum() +
                    GLOBAL_SCOPE_ERROR);
        }
        List<Variable> variables = method.getMethodParameters();
        for (int i = 0; i < method.numArguments(); i++) {
            for (int j = i + 1; j < method.numArguments(); j++) {
                if (variables.get(i).getName().equals(variables.get(j).getName())) {
                    throw new IllegalMethodDeclarationException(
                            String.format(METHOD_ARGUMENTS_ERROR,
                            method.getLineNum(), method.getName(), variables.get(i).getName()));
                }
            }
        }
        context.AddMethod(method);
        context.upCurrentScope();
    }
}
