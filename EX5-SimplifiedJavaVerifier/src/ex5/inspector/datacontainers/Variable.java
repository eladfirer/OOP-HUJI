package ex5.inspector.datacontainers;

/**
 * class that represents variable in program.
 */
public class Variable {

    private VariableTypes type;
    private final String name;
    private boolean hasValue;
    private boolean isFinal;
    private String value;
    private boolean checkForValueAssigment;
    private int lineNumber;


    /**
     * Variable constructor
     * @param type type of variable
     * @param name name of variable
     * @param hasValue does the paramter has value (initialized)
     * @param isFinal is the parameter final
     * @param value parameter value. "" in case of no value.
     * @param checkForValueAssigment do we need to check for value (another variable value)
     * @param lineNumber line number of variable
     */
    public Variable(VariableTypes type,
                    String name,
                    boolean hasValue,
                    boolean isFinal,
                    String value,
                    boolean checkForValueAssigment,
                    int lineNumber) {
        this.type = type;
        this.name = name;
        this.hasValue = hasValue;
        this.isFinal = isFinal;
        this.value = value;
        this.checkForValueAssigment = checkForValueAssigment;
        this.lineNumber = lineNumber;
    }

    /**
     * copy constructor for variable
     * @param original variable to deep copy
     */
    public Variable(Variable original) {
        this.type = original.type;
        this.name = original.name;
        this.hasValue = original.hasValue;
        this.isFinal = original.isFinal;
        this.value = original.value;
        this.checkForValueAssigment = original.checkForValueAssigment;
        this.lineNumber = original.lineNumber;
    }


    /**
     * @return type of variable
     */
    public VariableTypes getType() {
        return type;
    }

    /**
     * @return name of variable
     */
    public String getName() {
        return name;
    }

    /**
     * @return true in case that variable has value, false otherwise.
     */
    public boolean hasValue() {
        return hasValue;
    }

    /**
     * @return true in case that variable is final, false otherwise.
     */
    public boolean isFinal() {
        return isFinal;
    }

    /**
     * @return variable value
     */
    public String getValue() {
        return value;
    }

    /**
     * @return true in case that variable needs to be checked, false otherwise.
     */
    public boolean isCheckForValueAssigment() {
        return checkForValueAssigment;
    }

    /**
     * @return line number of variable
     */
    public int getLineNumber() {
        return lineNumber;
    }


    /**
     * setter for has value
     * @param hasValue value to give has value.
     */
    public void setHasValue(boolean hasValue) {
        this.hasValue = hasValue;
    }

    /**
     * setter for type
     * @param type type to put for variable
     */
    public void setType(VariableTypes type) {
        this.type = type;
    }

    /**
     * setter for value
     * @param value value to put in value
     */
    public void setValue(String value) {
        this.value = value;
    }
}
