package ex5.inspector.parsers;

/**
 * this class is a type line matcher that matches between the regex's and the program
 */
public class GeneralTypeOfLineMatcher {

    private static TypeOfLine checkForMatch(String line, TypeOfLine... types) {
        for (TypeOfLine type : types) {
            if (type.matches(line)) {
                return type;
            }
        }
        return null;
    }

    /**
     * checks if a string is a condition line
     * @param line line to check
     * @return true in case of positive result, false otherwise.
     * @see TypeOfLine
     */
    public static TypeOfLine checkForCondition(String line) {
        return checkForMatch(line, TypeOfLine.IF_STATEMENT, TypeOfLine.WHILE_STATEMENT);
    }


    /**
     * checks if a string is a scope end line
     * @param line line to check
     * @return true in case of positive result, false otherwise.
     * @see TypeOfLine
     */
    public static TypeOfLine checkForScopeEnd(String line) {
        return checkForMatch(line, TypeOfLine.BLOCK_CLOSER);
    }


    /**
     * checks if a string is a method declaration line
     * @param line line to check
     * @return true in case of positive result, false otherwise.
     * @see TypeOfLine
     */
    public static TypeOfLine checkForMethodStart(String line) {
        return checkForMatch(line, TypeOfLine.METHOD_DECLARATION);
    }

    /**
     * checks if a string is a variable declaration line
     * @param line line to check
     * @return true in case of positive result, false otherwise.
     * @see TypeOfLine
     */
    public static TypeOfLine checkForVariableDeclaration(String line) {
        return checkForMatch(line, TypeOfLine.BOOLEAN_DECLARATION,
                TypeOfLine.STRING_DECLARATION,
                TypeOfLine.CHAR_DECLARATION,
                TypeOfLine.DOUBLE_DECLARATION,
                TypeOfLine.INT_DECLARATION);
    }


    /**
     * checks if a string is a legal line
     * @param line line to check
     * @return true in case of positive result, false otherwise.
     * @see TypeOfLine
     */
    public static TypeOfLine checkForAnyType(String line) {
        return checkForMatch(line, TypeOfLine.values());
    }

    /**
     * checks if a string is a method call line
     * @param line line to check
     * @return true in case of positive result, false otherwise.
     * @see TypeOfLine
     */
    public static TypeOfLine checkForMethodCall(String line) {
        return checkForMatch(line,TypeOfLine.METHOD_CALL);
    }

    /**
     * checks if a string is an empty line
     * @param line line to check
     * @return true in case of positive result, false otherwise.
     * @see TypeOfLine
     */
    public static TypeOfLine checkForEmptyLine(String line) {
        return checkForMatch(line, TypeOfLine.EMPTY_LINE);
    }

    /**
     * checks if a string is a comment line
     * @param line line to check
     * @return true in case of positive result, false otherwise.
     * @see TypeOfLine
     */
    public static TypeOfLine checkForComment(String line) {
        return checkForMatch(line,TypeOfLine.SINGLE_LINE_COMMENT);
    }

    /**
     * checks if a string is a variable assignment
     * @param line line to check
     * @return true in case of positive result, false otherwise.
     * @see TypeOfLine
     */
    public static TypeOfLine checkForVariableAssignment(String line) {
        return checkForMatch(line,TypeOfLine.VARIABLE_ASSIGNMENT);
    }

    /**
     * checks if a string is a return statement
     * @param line line to check
     * @return true in case of positive result, false otherwise.
     * @see TypeOfLine
     */
    public static TypeOfLine checkForReturnStatement(String line) {
        return checkForMatch(line,TypeOfLine.RETURN);
    }
}
