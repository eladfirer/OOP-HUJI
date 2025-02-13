package ex5.inspector.datacontainers;

import ex5.inspector.parsers.TypeOfLine;
import ex5.util.Constants;

import java.util.regex.Pattern;

/**
 * enum for variable types
 */
public enum VariableTypes {

    /**
     * variables type
     * @see Constants
     */
    /**
     * int value regex accessor
     */
    INT(Pattern.compile(Constants.INT_NUMBER)),
    /**
     * boolean value regex accessor
     */
    BOOLEAN(Pattern.compile(Constants.BOOLEAN_LITERAL)),
    /**
     * string value regex accessor
     */
    STRING(Pattern.compile(Constants.STRING_LITERAL)),
    /**
     * char value regex accessor
     */
    CHAR(Pattern.compile(Constants.CHAR_LITERAL)),
    /**
     * double value regex accessor
     */
    DOUBLE(Pattern.compile(Constants.DOUBLE_NUMBER));

    private final Pattern pattern;

    /**
     * constructor for type to match to pattern
     * @param pattern pattern to put on enum
     */
    VariableTypes(Pattern pattern) {
        this.pattern = pattern;
    }

    /**
     * returns the variable type pattern
     * @return the pattern
     */
    public Pattern getPattern() {
        return pattern;
    }

    /**
     * variable name pattern accessor
     */
    public static final Pattern variableNamePattern = Pattern.compile(Constants.VARIABLE_NAME);

    /**
     * a method that from a string value returns the variable type
     * @param value value to check
     * @return the variable type of value, null in case of no match.
     */
    public static VariableTypes fromValue(String value) {
        // Explicitly check INT and DOUBLE first (BOOLEAN is INT and DOUBLE too)
        if (INT.getPattern().matcher(value).matches()) {
            return INT;
        }
        if (DOUBLE.getPattern().matcher(value).matches()) {
            return DOUBLE;
        }

        if (BOOLEAN.getPattern().matcher(value).matches()) {
            return BOOLEAN;
        }

        if (STRING.getPattern().matcher(value).matches()) {
            return STRING;
        }
        if (CHAR.getPattern().matcher(value).matches()) {
            return CHAR;
        }

        return null;
    }

    /**
     * a method that from a type of line returns the variable type
     * @param typeOfLine value to check
     * @return the variable type of line, null in case of no match.
     * @see TypeOfLine
     */
    public static VariableTypes fromTypeOfLine(TypeOfLine typeOfLine) {
        switch (typeOfLine) {
            case INT_DECLARATION:
                return VariableTypes.INT;
            case DOUBLE_DECLARATION:
                return VariableTypes.DOUBLE;
            case CHAR_DECLARATION:
                return VariableTypes.CHAR;
            case BOOLEAN_DECLARATION:
                return VariableTypes.BOOLEAN;
            case STRING_DECLARATION:
                return VariableTypes.STRING;
            default:
                return null;
        }
    }

    /**
     * a method that from a string type of variable returns the variable type
     * @param type variable type to check
     * @return the variable type of the string, null in case of no match.
     */
    public static VariableTypes fromTypeString(String type) {
        switch (type) {
            case "int":
                return VariableTypes.INT;
            case "double":
                return VariableTypes.DOUBLE;
            case "boolean":
                return VariableTypes.BOOLEAN;
            case "char":
                return VariableTypes.CHAR;
            case "String":
                return VariableTypes.STRING;
            default:
                return null;
        }
    }

}
