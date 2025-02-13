package ex5.inspector.parsers;

import ex5.util.Constants;

import java.util.regex.Pattern;

/**
 * this enum is for making access to regex patterns easier
 */
public enum TypeOfLine {
    /**
     * int declaration regex accessor
     */
    INT_DECLARATION(Constants.INT_VARIABLE),
    /**
     * string declaration regex accessor
     */
    STRING_DECLARATION(Constants.STRING_VARIABLE),
    /**
     * char declaration regex accessor
     */
    CHAR_DECLARATION(Constants.CHAR_VARIABLE),
    /**
     * double declaration regex accessor
     */
    DOUBLE_DECLARATION(Constants.DOUBLE_VARIABLE),
    /**
     * boolean declaration regex accessor
     */
    BOOLEAN_DECLARATION(Constants.BOOLEAN_VARIABLE),

    /**
     * variable assignment regex accessor
     */
    VARIABLE_ASSIGNMENT(Constants.VARIABLE_ASSIGNMENT),

    /**
     * method declaration regex accessor
     */
    METHOD_DECLARATION(Constants.METHOD_DECLARATION),

    /**
     * method call regex accessor
     */
    METHOD_CALL(Constants.METHOD_CALL),

    /**
     * if statement regex accessor
     */
    IF_STATEMENT(Constants.IF_STATEMENT),

    /**
     * while statement regex accessor
     */
    WHILE_STATEMENT(Constants.WHILE_STATEMENT),

    /**
     * return statement regex accessor
     */
    RETURN(Constants.RETURN),

    /**
     * block closer statement regex accessor
     */
    BLOCK_CLOSER(Constants.CLOSER),

    /**
     * single line comment regex accessor
     */
    SINGLE_LINE_COMMENT(Constants.SINGLE_LINE_COMMENT),

    /**
     * empty line regex accessor
     */
    EMPTY_LINE(Constants.EMPTY_LINE);



    private final Pattern pattern;

    /**
     * constructor for TypeOfline
     *
     * @param regex a string regex that represents the pattern enum
     */
    TypeOfLine(String regex) {
        this.pattern = Pattern.compile(regex);
    }

    /**
     * @return the specific pattern enum
     */
    public Pattern getPattern() {
        return pattern;
    }

    /**
     * this method returns if there is a match between a pattern and a String
     *
     * @param line String to check for match
     * @return true in case of a match, false otherwise
     */
    public boolean matches(String line) {
        return pattern.matcher(line).matches();
    }
}

