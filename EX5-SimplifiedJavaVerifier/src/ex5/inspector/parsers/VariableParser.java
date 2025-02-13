package ex5.inspector.parsers;

import ex5.inspector.datacontainers.Variable;
import ex5.inspector.datacontainers.VariableTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * this class is responsible for parsing string line of variables into Variables class data
 */
public class VariableParser {


    /**
     * parses a variable declaration into variables
     *
     * @param typeOfLine type of variable declaration
     * @param line       string of variables declaration line
     * @param lineNumber line number of declaration line
     * @return list of variables from variable declaration
     */
    public static List<Variable> parseVariablesFromDeclarationLine(TypeOfLine typeOfLine,
                                                                   String line, int lineNumber) {
        List<Variable> variables = new ArrayList<>();

        Pattern pattern = typeOfLine.getPattern();
        Matcher matcher = pattern.matcher(line);

        // we know its going to match due to previous calculations
        matcher.matches();

        boolean isFinal = matcher.group(1) != null;

        String typeString = typeOfLine.name().toLowerCase().replace("_declaration", "");
        if (typeString.equals("string")) {
            typeString = typeString.substring(0, 1).toUpperCase() + typeString.substring(1);
        }
        String declarationPart = line.replaceFirst("^\\s*((final)\\s+)?" + typeString + "\\s+", "")
                .replaceFirst(";\\s*$", "");
        String[] declarations = declarationPart.split(",");

        for (String declaration : declarations) {
            String[] parts = declaration.split("=");

            String name = parts[0].trim();
            boolean hasValue = parts.length > 1;
            String value = hasValue ? parts[1].trim() : "";


            boolean checkForValueAssignment =
                    hasValue && VariableTypes.variableNamePattern.matcher(value).matches();
            VariableTypes variableType = VariableTypes.fromTypeOfLine(typeOfLine);
            if (variableType == VariableTypes.BOOLEAN) {
                if (value.equals("true") || value.equals("false")) {
                    checkForValueAssignment = false;
                }
            }

            variables.add(new Variable(variableType, name, hasValue, isFinal, value,
                    checkForValueAssignment, lineNumber));
        }


        return variables;
    }

    /**
     * parses a condition values into variables
     *
     * @param typeOfLine type of condition line
     * @param line       string of condition line
     * @param lineNumber line number of condition line
     * @return list of variables to check from condition line
     */
    public static List<Variable> parseVariablesFromConditionLine(TypeOfLine typeOfLine,
                                                                 String line, int lineNumber) {
        List<Variable> variables = new ArrayList<>();

        Matcher matcher = typeOfLine.getPattern().matcher(line);

        if (matcher.matches()) {
            String condition = matcher.group(1);
            if (condition != null) {

                String[] tokens = condition.split("\\s*(\\|\\||&&)\\s*");

                for (String token : tokens) {
                    token = token.trim();

                    if (token.equals("true") || token.equals("false")) {
                        continue;
                    }

                    if (VariableTypes.variableNamePattern.matcher(token).matches()) {
                        variables.add(new Variable(VariableTypes.BOOLEAN,
                                token,
                                true,
                                false,
                                token,
                                true,
                                lineNumber));
                    }
                }
            }
        }

        return variables;
    }

    /**
     * parses an assignment line into variables
     *
     * @param typeOfLine type of assignment line
     * @param line       string of assignment line
     * @param lineNumber line number of assignment line
     * @return list of variables from assignment line
     */
    public static List<Variable> parseVariablesFromAssigmentLine(TypeOfLine typeOfLine,
                                                                 String line, int lineNumber) {
        List<Variable> variables = new ArrayList<>();

        Pattern pattern = typeOfLine.getPattern();
        Matcher matcher = pattern.matcher(line);

        matcher.matches();

        String[] assignments = matcher.group(0).
                replaceFirst(";\\s*$", "").split("\\s*,\\s*"); //
        // Split by commas for multiple assignments

        for (String assignment : assignments) {
            // Split the assignment into variable name and value
            String[] parts = assignment.split("\\s*=\\s*");


            String variableName = parts[0].trim();
            String value = parts[1].trim();


            VariableTypes variableType = VariableTypes.fromValue(value);
            if (variableType == null) {
                variables.add(new Variable(null,
                        variableName,
                        true,
                        false,
                        value,
                        false,
                        lineNumber));
            }
            else {
                variables.add(new Variable(variableType,
                        variableName,
                        true,
                        false,
                        value,
                        false,
                        lineNumber));
            }
        }

        return variables;
    }

}

