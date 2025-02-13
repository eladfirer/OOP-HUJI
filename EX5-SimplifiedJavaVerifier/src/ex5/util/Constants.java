package ex5.util;

public final class Constants {

    private Constants() {
    }

    /**
     * comments and empty lines regexes
     */
    public static final String SINGLE_LINE_COMMENT = "//.*"; // Matches single-line comments
    public static final String EMPTY_LINE = "\\s*";

    /**
     * end statements regexes
     */
    public static final String RETURN = "\\s*return\\s*;\\s*";
    public static final String CLOSER = "\\s*}\\s*";
    /**
     * type of values regexes
     */
    public static final String INT_NUMBER = "[+-]?\\d+";
    public static final String DOUBLE_NUMBER = "[+-]?(\\d+([\\.]\\d*)?|\\d*[\\.]\\d+)";
    public static final String STRING_LITERAL = "\".*\"";
    public static final String BOOLEAN_LITERAL = "true" + "|"
            + "false" + "|"
            + DOUBLE_NUMBER + "|"
            + INT_NUMBER;
    public static final String CHAR_LITERAL = "'.'";
    public static final String VARIABLE_NAME = "(([a-zA-Z]|_\\w)\\w*)";
    private static final String FINAL = "((final)\\s+)?";
    private static final String VALUES = "(" +
            DOUBLE_NUMBER + "|" + BOOLEAN_LITERAL + "|" + CHAR_LITERAL + "|" + INT_NUMBER + "|" +
            STRING_LITERAL + "|" + VARIABLE_NAME  +
            ")";

    /**
     * fields of value regex
     */
    private static final String PARAMTERS = "(" + "String" + "|" + "int" + "|" +
            "double" + "|" + "boolean" + "|" + "char" +
            ")";

    private static final String EQUAL = "\\s*=\\s*";
    private static final String COMMA = "\\s*,\\s*";
    /**
     * conditions regexes
     */
    private static final String CONDITION = "(" + VARIABLE_NAME +
            "|" + BOOLEAN_LITERAL + ")" +
            "(" + "\\s*(\\|\\||&&)\\s*" +
            "(" + VARIABLE_NAME + "|" + BOOLEAN_LITERAL +
            ")" + ")*";



    /**
     * method regexes
     */
    private static final String METHOD_VALUES_CALL =
            "\\(\\s*((\\s*)|" + VALUES + "(" + COMMA + "(" + VALUES + "))*)\\s*\\)";
    private static final String METHOD_NAME = "(([a-zA-Z])\\w*)";
    public static final String METHOD_CALL =
            "\\s*" + METHOD_NAME + "\\s*" + METHOD_VALUES_CALL + "\\s*;\\s*";
    private static final String METHOD_VALUES_DECLARATION = "\\(\\s*(()|" + FINAL + PARAMTERS + "\\s" +
            "+" + VARIABLE_NAME + "(" + COMMA + "(" + FINAL + PARAMTERS + "\\s+" + VARIABLE_NAME +
            "))*)\\s*\\)";
    public static final String METHOD_DECLARATION =
            "\\s*" + "void" + "\\s*" + METHOD_NAME + "\\s*" + METHOD_VALUES_DECLARATION + "\\s" +
                    "*\\{\\s*";
    /**
     * if and while regexes
     */
    private static final String IF = "if";
    public static final String IF_STATEMENT = "\\s*" + IF + "\\s*\\(\\s*(" + CONDITION + ")\\s*\\)" +
            "\\s*\\{\\s*";
    private static final String WHILE = "while";
    public static final String WHILE_STATEMENT = "\\s*" + WHILE + "\\s*\\(\\s*(" + CONDITION +
            ")\\s*\\)\\s*\\{\\s*";

    /**
     * variables regexes
     */
    public static final String INT_VARIABLE = "\\s*" + FINAL + "int" + "\\s+" +
            VARIABLE_NAME + "(" + COMMA + "(" + VARIABLE_NAME + ")" + "|" +
            EQUAL + "(" + INT_NUMBER + "|" + VARIABLE_NAME + ")" + ")*"
            + "(\\s*);\\s*";
    public static final String DOUBLE_VARIABLE = "\\s*" +
            FINAL + "double" + "\\s+" + VARIABLE_NAME + "(" + COMMA + "(" + VARIABLE_NAME + ")" +
            "|" +
            EQUAL + "(" + DOUBLE_NUMBER + "|" + VARIABLE_NAME + ")" + ")*"
            + "(\\s*);\\s*";
    public static final String CHAR_VARIABLE = "\\s*" + FINAL + "char" + "\\s+" +
            VARIABLE_NAME + "(" + COMMA + "(" + VARIABLE_NAME + ")" +
            "|" + EQUAL + "(" + CHAR_LITERAL + "|" + VARIABLE_NAME + ")" + ")*"
            + "(\\s*);\\s*";
    public static final String BOOLEAN_VARIABLE = "\\s*" +
            FINAL + "boolean" + "\\s+" + VARIABLE_NAME + "(" + COMMA +
            "(" + VARIABLE_NAME + ")" + "|" + EQUAL +
            "(" + BOOLEAN_LITERAL + "|" + VARIABLE_NAME + ")" + ")*"
            + "(\\s*);\\s*";
    public static final String STRING_VARIABLE = "\\s*" + FINAL + "String" + "\\s+" + VARIABLE_NAME +
            "(" + COMMA + "(" + VARIABLE_NAME + ")" + "|" + EQUAL +
            "(" + STRING_LITERAL + "|" + VARIABLE_NAME + ")" + ")*"
            + "(\\s*);\\s*";

    public static final String VARIABLE_ASSIGNMENT = "\\s*" + VARIABLE_NAME + EQUAL + VALUES +
            "(" + COMMA + VARIABLE_NAME + EQUAL + VALUES + "\\s*)*" + "\\s*;\\s*";




}
