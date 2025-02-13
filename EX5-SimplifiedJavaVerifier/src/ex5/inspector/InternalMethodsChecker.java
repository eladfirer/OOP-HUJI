package ex5.inspector;

import ex5.inspector.checkers.ConditionLineChecker;
import ex5.inspector.checkers.MethodCallLineChecker;
import ex5.inspector.checkers.VariableAssigmentLineChecker;
import ex5.inspector.checkers.VariableDeclarationLineChecker;
import ex5.inspector.datacontainers.*;
import ex5.inspector.parsers.*;
import ex5.util.FileLoader;
import ex5.util.IChecker;

import java.util.List;

/**
 * this class is responsible for checking the syntax inside every method in the current running file
 */
public class InternalMethodsChecker implements IChecker {

    private static final String CLOSING_METHOD_ERROR = "Line %d: The method is missing a closing " +
            "'}' brace.";
    private static final String COMMENT_IN_METHOD_ERROR = "Line %d: There is a comment inside a " +
            "the method %s";
    private static final String RETURN_STATEMENT_INT_METHOD_ERROR = "Line %d: there isn't a return" +
            " statement at the end of '%s' method";
    private final FileLoader fileLoader;
    private ContextData contextData;

    /**
     * Constructor for class
     * @param fileLoader file loader of current file
     * @see FileLoader
     * @see ContextData
     */
    public InternalMethodsChecker(FileLoader fileLoader) {
        this.fileLoader = fileLoader;
        contextData = null;
    }

    /**
     * this is a setter for the context data
     * @param contextData - context data to set
     */
    public void updateContextData(ContextData contextData) {
        this.contextData = contextData;
    }

    /**
     * this function is called to check the program
     * @throws IllegalLineException in case of an illegal line in one of the methods throws exception
     */
    public void check() throws IllegalLineException {
        checkForMethod();
        fileLoader.resetLines();
    }

    private void checkForMethod() throws IllegalLineException {
        while (fileLoader.hasMoreLines()) {
            String line = fileLoader.getCurrentLine();
            int lineNum = fileLoader.getCurrentIndex() + 1;
            if (GeneralTypeOfLineMatcher.checkForMethodStart(line) != null) {
                ContextData contextDataReplica = new ContextData(contextData);
                // we are guaranteed to get the method here - we saved it in this line num before
                Method method = contextDataReplica.searchForMethod(lineNum);
                contextDataReplica.upCurrentScope();
                fileLoader.advanceToNextLine();
                startMethodInspection(contextDataReplica, method);
            }
            else {
                fileLoader.advanceToNextLine();
            }
        }

    }

    private void startMethodInspection(ContextData contextDataReplica, Method method)
            throws IllegalLineException {
        for (var variable : method.getMethodParameters()) {
            variable.setHasValue(true);
            contextDataReplica.AddVariable(variable);
        }
        while (fileLoader.hasMoreLines()) {
            if (checkLine(contextDataReplica, method.getName())) {
                break;
            }
            fileLoader.advanceToNextLine();
        }

        checkScope(contextDataReplica, method.getLineNum());
    }


    private boolean checkLine(ContextData contextDataReplica, String methodName)
            throws IllegalLineException {
        if (contextDataReplica.getCurrentScope() == 0) {
            return true;
        }
        String line = fileLoader.getCurrentLine();
        int lineNum = fileLoader.getCurrentIndex() + 1;
        checkForCommentLine(line, lineNum, methodName);
        checkForVariableDeclaration(line, lineNum, contextDataReplica);
        checkForCondition(line, lineNum, contextDataReplica);
        checkForMethodCall(line, lineNum, contextDataReplica);
        checkForEndBlock(line, lineNum, contextDataReplica, methodName);
        checkForVariableAssignment(line, lineNum, contextDataReplica);

        return false;
    }

    private void checkForVariableAssignment(String line, int lineNum, ContextData contextDataReplica)
            throws IllegalLineException {
        TypeOfLine typeOfLine = GeneralTypeOfLineMatcher.checkForVariableAssignment(line);
        if(typeOfLine != null) {
            List<Variable> variables = VariableParser.parseVariablesFromAssigmentLine(typeOfLine,
                    line,
                    lineNum);
            VariableAssigmentLineChecker.checkVariables(variables, contextDataReplica);
        }

    }

    private void checkForEndBlock(String line, int lineNum, ContextData contextDataReplica,
                                  String methodName)
            throws IllegalLineException {
        TypeOfLine typeOfLine = GeneralTypeOfLineMatcher.checkForScopeEnd(line);
        if (typeOfLine != null) {
            contextDataReplica.downCurrentScope();
            if (contextDataReplica.getCurrentScope() == 0) {
                fileLoader.goBackALine();
                String lastLine = fileLoader.getCurrentLine();
                if (GeneralTypeOfLineMatcher.checkForReturnStatement(lastLine) == null) {
                    throw new IllegalLineException(String.format(RETURN_STATEMENT_INT_METHOD_ERROR,
                            lineNum, methodName));
                }
                fileLoader.advanceToNextLine();
            }
        }
    }

    private void checkForMethodCall(String line, int lineNum, ContextData contextDataReplica)
            throws IllegalLineException {
        TypeOfLine typeOfLine = GeneralTypeOfLineMatcher.checkForMethodCall(line);
        if (typeOfLine != null) {
            Method method = MethodParser.parseMethodCallFromLine(typeOfLine, line,
                    lineNum);
            MethodCallLineChecker.checkMethodCall(method, contextDataReplica);
        }
    }

    private void checkForCondition(String line, int lineNum, ContextData contextDataReplica)
            throws IllegalLineException {
        if (GeneralTypeOfLineMatcher.checkForCondition(line) != null) {
            TypeOfLine typeOfLine = GeneralTypeOfLineMatcher.checkForCondition(line);
            List<Variable> variables = VariableParser.parseVariablesFromConditionLine(typeOfLine,
                    line,
                    lineNum);
            ConditionLineChecker.checkVariables(variables, contextDataReplica);
            contextDataReplica.upCurrentScope();
        }
    }

    private void checkForVariableDeclaration(String line, int lineNum,
                                             ContextData contextDataReplica)
            throws IllegalLineException {
        if (GeneralTypeOfLineMatcher.checkForVariableDeclaration(line) != null) {
            TypeOfLine typeOfLine = GeneralTypeOfLineMatcher.checkForVariableDeclaration(line);
            List<Variable> variables =
                    VariableParser.parseVariablesFromDeclarationLine(typeOfLine, line,
                            lineNum);
            VariableDeclarationLineChecker.checkVariables(variables, contextDataReplica);
        }
    }

    private void checkForCommentLine(String line, int lineNum, String methodName)
            throws IllegalLineException {
        if (GeneralTypeOfLineMatcher.checkForComment(line) != null) {
            throw new IllegalLineException(String.format(COMMENT_IN_METHOD_ERROR, lineNum,
                    methodName));
        }
    }

    private void checkScope(ContextData contextDataReplica, int lineNum)
            throws IllegalLineException {
        if (contextDataReplica.getCurrentScope() != 0) {
            throw new IllegalLineException(String.format(CLOSING_METHOD_ERROR, lineNum));
        }
    }
}
