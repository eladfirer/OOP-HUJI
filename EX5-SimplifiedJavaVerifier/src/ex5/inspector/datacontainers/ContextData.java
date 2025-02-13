package ex5.inspector.datacontainers;

import ex5.util.FileLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * this class contains the data for the program, including current variables, methods,scope and
 * file loader.
 */
public class ContextData {


    private final FileLoader fileloader;
    private int currentScope;
    private Map<Integer, ArrayList<Variable>> variables;
    private ArrayList<Method> methods;


    /**
     * Constructor for contextData
     * @param fileLoader file loader with file to check code
     * @see Variable
     * @see Method
     * @see FileLoader
     */
    public ContextData(FileLoader fileLoader) {
        this.fileloader = fileLoader;
        currentScope = 0;
        variables = new HashMap<>();
        variables.put(currentScope, new ArrayList<>());
        methods = new ArrayList<>();
    }

    /**
     * copy constructor for class
     * @param original contextData to deep copy
     */
    public ContextData(ContextData original) {
        this.fileloader = original.fileloader;
        this.currentScope = original.currentScope;

        this.variables = new HashMap<>();
        for (Map.Entry<Integer, ArrayList<Variable>> entry : original.variables.entrySet()) {
            ArrayList<Variable> copiedList = new ArrayList<>();
            for (Variable variable : entry.getValue()) {
                copiedList.add(new Variable(variable));
            }
            this.variables.put(entry.getKey(), copiedList);
        }

        this.methods = new ArrayList<>();
        for (Method method : original.methods) {
            this.methods.add(new Method(method));
        }
    }

    /**
     * adding a method to context data
     * @param method method to add
     * @see Method
     */
    public void AddMethod(Method method) {
        methods.add(method);
    }

    /**
     * adding a variable to context data current scope
     * @param variable variable to add
     * @see Variable
     */
    public void AddVariable(Variable variable) {
        variables.get(currentScope).add(variable);
    }

    /**
     * a method that checks if variable is already declared in current scope
     * @param variableToCheck variable to check
     * @return true in case of positive result, false otherwise
     * @see Variable
     */
    public boolean checkIfVariableAlreadyDeclaredInScope(Variable variableToCheck) {
        for (var variable : variables.get(currentScope)) {
            if (variable.getName().equals(variableToCheck.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * checks if variable value exists and matches variable type
     * @param variableToCheck variable to check value and type
     * @return true in case of positive value, false otherwise.
     * @see Variable
     */
    public boolean checkIfVariableValueExistsAndMatches(Variable variableToCheck) {
        Variable variable = searchForVariable(variableToCheck.getValue());
        if (variable != null) {
            return checkForMatch(variableToCheck, variable);
        }
        return false;
    }

    private boolean checkForMatch(Variable variableToCheck, Variable variable) {
        if (!variable.hasValue()) {
            return false;
        }
        VariableTypes variableType = variable.getType();
        VariableTypes variableToCheckType = variableToCheck.getType();
        if (variableToCheckType == VariableTypes.BOOLEAN) {
            if (variableType == VariableTypes.INT || variableType == VariableTypes.DOUBLE) {
                return true;
            }
        }
        if(variableToCheckType ==  VariableTypes.DOUBLE){
            return variableType == VariableTypes.INT || variableType == VariableTypes.DOUBLE;
        }
        return variableToCheckType == variableType;
    }


    /**
     * checks if a method with same name exists
     * @param methodToCheck method to check
     * true in case of positive answer, false otherwise.
     * @see Method
     */
    public boolean checkIfMethodWithSameNameExists(Method methodToCheck) {
        String methodToCheckName = methodToCheck.getName();
        for (var method : methods) {
            String methodName = method.getName();
            if (methodName.equals(methodToCheckName)) {
                return true;
            }
        }
        return false;
    }


    /**
     * takes down current scope
     */
    public void downCurrentScope() {
        variables.remove(currentScope);
        currentScope--;
    }

    /**
     * takes up current scope
     */
    public void upCurrentScope() {
        currentScope++;
        variables.put(currentScope, new ArrayList<>());
    }


    /**
     * getter for file loder
     * @return file loader
     * @see FileLoader
     */
    public FileLoader getFileLoader() {
        return fileloader;
    }

    /**
     * getter for scope
     * @return current scope
     */
    public int getCurrentScope() {
        return currentScope;
    }

    /**
     * reset scope
     */
    public void resetScope() {
        currentScope = 0;
    }

    /**
     * search for a mthod using line number.
     * @param lineNum line number to check for method
     * @return null in case of no method in line num, the method otherwise.
     * @see Method
     */
    public Method searchForMethod(int lineNum) {
        for (Method method : methods) {
            if (method.getLineNum() == lineNum) {
                return method;
            }
        }
        return null;
    }

    /**
     * returns the method according to name of method
     * @param name name of method to search
     * @return null in case of no method with name, the method otherwise.
     */
    public Method getMethod(String name) {
        for (Method method : methods) {
            if (method.getName().equals(name)) {
                return method;
            }
        }
        return null;
    }

    /**
     * checks if a variables in method call exist and initialized
     * @param method method to check variables
     * @return false in case of one of the variables doesn't exist and initialized, true otherwise
     * @see Method
     * @see Variable
     */
    public boolean checkIfVariablesExistAndIntialized(Method method) {
        for (var variable : method.getMethodParameters()) {
            VariableTypes type = variable.getType();
            if (type == null) {
                VariableTypes matchedType = checkVariableMatch(variable);
                if (matchedType == null) {
                    return false;
                }
            }
        }
        return true;
    }

    private VariableTypes checkVariableMatch(Variable checkedVariable) {
        Variable variable = searchForVariable(checkedVariable.getName());
        if (variable != null) {
            if (!variable.hasValue()) {
                return null;
            }
            checkedVariable.setHasValue(true);
            checkedVariable.setType(variable.getType());
            checkedVariable.setValue(variable.getValue());
            return checkedVariable.getType();
        }
        return null;
    }


    /**
     * checks if variable exist in program
     * @param variableAssignment variable to check
     * @return true oin case of positive answer, false otherwise.
     * @see Variable
     */
    public boolean checkIfVariableExist(Variable variableAssignment) {
        Variable variable = searchForVariable(variableAssignment.getName());
        return variable != null;
    }


    private Variable searchForVariable(String variableName) {
        for (int i = currentScope; i >= 0; i--) {
            for (var variableInContext : variables.get(i)) {
                if (variableInContext.getName().equals(variableName)) {
                    return variableInContext;
                }
            }
        }
        return null;
    }


    /**
     * checks if variable is final
     * @param variableAssignment variable to check
     * @return true in case of final, false otherwise.
     * @see Variable
     */
    public boolean checkIfVariableIsFinal(Variable variableAssignment) {
        Variable variable = searchForVariable(variableAssignment.getName());
        if (!variable.isFinal()) {
            return false;
        }
        return true;
    }

    /**
     * checks if variable value is legal according to variable name.
     * @param variableAssignment variable to check
     * @return true in case of positive answer, false otherwise.
     * @see Variable
     */
    public boolean checkIfVariableValueLegal(Variable variableAssignment) {
        Variable variable = searchForVariable(variableAssignment.getName());
        if (variableAssignment.getType() == null) {
            variableAssignment = searchForVariable(variableAssignment.getValue());
            if (variableAssignment != null) {
                variable.setHasValue(variableAssignment.hasValue());
                variable.setValue(variableAssignment.getValue());
                return variableAssignment.hasValue();
            }
            return false;
        }
        variable.setHasValue(true);
        variable.setValue(variableAssignment.getValue());
        if (variableAssignment.getType() == variable.getType()) {
            return true;
        }
        if (variableAssignment.getType() == VariableTypes.BOOLEAN) {
            return variable.getType() == VariableTypes.INT || variable.getType() == VariableTypes.DOUBLE;
        }
        return false;
    }
}
