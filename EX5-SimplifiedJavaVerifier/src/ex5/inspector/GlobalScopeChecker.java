package ex5.inspector;

import ex5.inspector.checkers.*;
import ex5.inspector.datacontainers.*;
import ex5.inspector.parsers.*;
import ex5.util.FileLoader;
import ex5.util.IChecker;

import java.util.List;

/**
 * global scope chcker class checks that global scope is legal and puts into context methods and
 * global variables
 */
public class GlobalScopeChecker implements IChecker {
    private static final String GLOBAL_SCOPE_BLOCK_START_ERROR = "Line %d: A new block cannot be " +
            "started in the global scope.";
    private static final String GLOBAL_SCOPE_BLOCK_END_ERROR = "Line %d: A block cannot be ended " +
            "in the global scope.";
    private static final String VARIABLE_ASSIGMENT_ERROR = "Line %d: Attempted variable " +
            "assignment in the global scope.";
    private static final String RETURN_STATEMENT_ERROR = "Line %d: Return statement is not " +
            "allowed in the global scope.";
    private static final String METHOD_DECLARATION_ERROR = "Line %d: Method declaration must be " +
            "in global scope";
    private static final String METHOD_CALL_ERROR = "Line %d: Method call must be " +
            "inside a method";

    private final FileLoader fileLoader;
    private final ContextData contextData;


    /**
     * global scope checker constructor
     * @param fileLoader file loader of current file
     */
    public GlobalScopeChecker(FileLoader fileLoader) {
        this.fileLoader = fileLoader;
        contextData = new ContextData(fileLoader);
    }

    /**
     * @return getter for context
     */
    public ContextData getContextData() {
        return contextData;
    }

    /**
     * checks the global scope
     * @throws IllegalLineException in case of an illegal line in global scope throws exception
     */
    public void check() throws IllegalLineException {
        while (fileLoader.hasMoreLines()) {
            checkLine();
            fileLoader.advanceToNextLine();
        }
        // Resetting scope; it may not be 0 (error 100%), but we want to pinpoint the syntax
        // error line
        contextData.resetScope();
        fileLoader.resetLines();
    }


    private void checkLine() throws IllegalLineException {
        String line = fileLoader.getCurrentLine();
        int lineNumber = fileLoader.getCurrentIndex() + 1;

        if (contextData.getCurrentScope() == 0) {
            checkForVariablesDeclaration(line, lineNumber);
            checkForMethodDeclaration(line, lineNumber);
            checkForStartBlocks(line, lineNumber);
            checkForVariablesAssigment(line, lineNumber);
            checkForEndBlocks(line, lineNumber);
            checkForReturnStatement(line, lineNumber);
            checkForMethodCall(line, lineNumber);
        }
        else {
            // although it's an internal method error, we checking it here to make program easier
            checkForMethodDeclaration(line, lineNumber);

            checkForStartBlocks(line, lineNumber);
            checkForEndBlocks(line, lineNumber);
        }

    }

    private void checkForMethodCall(String line, int lineNumber) throws IllegalLineException {
        if (GeneralTypeOfLineMatcher.checkForMethodCall(line) != null) {
            throw new IllegalLineException(String.format(METHOD_CALL_ERROR, lineNumber));
        }
    }

    private void checkForReturnStatement(String line, int lineNumber) throws IllegalLineException {
        if (GeneralTypeOfLineMatcher.checkForReturnStatement(line) != null) {
            throw new IllegalLineException(String.format(RETURN_STATEMENT_ERROR, lineNumber));
        }
    }

    private void checkForEndBlocks(String line, int lineNumber) throws IllegalLineException {
        if (GeneralTypeOfLineMatcher.checkForScopeEnd(line) != null) {
            if (contextData.getCurrentScope() == 0) {
                throw new IllegalLineException(String.format(GLOBAL_SCOPE_BLOCK_END_ERROR,
                        lineNumber));
            }
            contextData.downCurrentScope();
        }
    }

    private void checkForVariablesAssigment(String line, int lineNumber) throws IllegalLineException {
        if (GeneralTypeOfLineMatcher.checkForVariableAssignment(line) != null) {
            throw new IllegalLineException(String.format(VARIABLE_ASSIGMENT_ERROR, lineNumber));
        }
    }

    private void checkForStartBlocks(String line, int lineNumber) throws IllegalLineException {
        if (GeneralTypeOfLineMatcher.checkForCondition(line) != null) {
            if (contextData.getCurrentScope() == 0) {
                throw new IllegalLineException(String.format(GLOBAL_SCOPE_BLOCK_START_ERROR,
                        lineNumber));
            }
            contextData.upCurrentScope();
        }
    }


    private void checkForVariablesDeclaration(String line, int lineNumber) throws IllegalLineException {
        if (GeneralTypeOfLineMatcher.checkForVariableDeclaration(line) != null) {
            TypeOfLine typeOfLine = GeneralTypeOfLineMatcher.checkForVariableDeclaration(line);
            List<Variable> variables =
                    VariableParser.parseVariablesFromDeclarationLine(typeOfLine, line,
                    lineNumber);
            VariableDeclarationLineChecker.checkVariables(variables, contextData);
        }

    }

    private void checkForMethodDeclaration(String line, int lineNumber) throws IllegalLineException {
        if (GeneralTypeOfLineMatcher.checkForMethodStart(line) != null) {
            if (contextData.getCurrentScope() != 0) {
                throw new IllegalLineException(String.format(METHOD_DECLARATION_ERROR, lineNumber));
            }
            TypeOfLine typeOfLine = GeneralTypeOfLineMatcher.checkForMethodStart(line);
            Method method = MethodParser.parseMethodFromLine(line, lineNumber);
            MethodDeclarationLineChecker.checkMethod(method, contextData);

        }
    }
}
