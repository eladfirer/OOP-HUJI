package ex5.inspector.datacontainers;

import java.util.ArrayList;

/**
 * class that represents method
 */
public class Method {
    private final int lineNum;
    private String name;
    private ArrayList<Variable> methodParameters;

    /**
     * Method constructor
     * @param name name of method
     * @param methodParameters method parameters, represented as variables
     * @param lineNum line number of method
     * @see Variable
     */
    public Method(String name, ArrayList<Variable> methodParameters, int lineNum) {
        this.name = name;
        this.methodParameters = methodParameters;
        this.lineNum = lineNum;
    }

    /**
     * copy constructor for method
     * @param original method to deep copy
     */
    public Method(Method original) {
        this.name = original.name;
        this.lineNum = original.lineNum;

        // Deep copy the arguments list
        this.methodParameters = new ArrayList<>();
        for (Variable argument : original.methodParameters) {
            this.methodParameters.add(new Variable(argument));
        }
    }

    /**
     * getter for name
     * @return name.
     */
    public String getName() {
        return name;
    }

    /**
     * getter for parameters list
     * @return the parameters list
     * @see Variable
     */
    public ArrayList<Variable> getMethodParameters() {
        return methodParameters;
    }

    /**
     * @return line number of method
     */
    public int getLineNum() {
        return lineNum;
    }

    /**
     * @return number of arguments of method
     */
    public int numArguments() {
        return methodParameters.size();
    }
}
