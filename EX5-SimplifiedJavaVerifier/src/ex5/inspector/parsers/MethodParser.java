package ex5.inspector.parsers;


import ex5.inspector.datacontainers.Method;
import ex5.inspector.datacontainers.Variable;
import ex5.inspector.datacontainers.VariableTypes;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * this class is a method parser
 */
public class MethodParser {

    /**
     * parses a method declaration into a method
     * @param line string of method declaration
     * @param lineNum line number of method declaration
     * @return the method
     */
    public static Method parseMethodFromLine(String line, int lineNum) {
        // Compile the method declaration pattern
        Pattern pattern = TypeOfLine.METHOD_DECLARATION.getPattern();;
        Matcher matcher = pattern.matcher(line);

        matcher.matches();

        String methodName = matcher.group(1);

        String parameterList = matcher.group(3);

        ArrayList<Variable> methodParameters = parseParametersFromString(parameterList);

        return new Method(methodName, methodParameters, lineNum);

    }


    private static ArrayList<Variable> parseParametersFromString(String parameterList) {
        ArrayList<Variable> variables = new ArrayList<>();

        if (parameterList == null || parameterList.trim().isEmpty()) {
            return variables;
        }

        String[] parameters = parameterList.split(",");

        for (String parameter : parameters) {
            parameter = parameter.trim();
            String[] parts = parameter.split("\\s+");

            if (parts.length == 2 || (parts.length == 3 && parts[0].equals("final"))) {
                boolean isFinal = parts.length == 3;
                String type = parts[isFinal ? 1 : 0];
                String name = parts[isFinal ? 2 : 1];

                VariableTypes variableType = VariableTypes.fromTypeString(type);

                variables.add(new Variable(variableType, name, false, isFinal, "", false,0));
            }
        }

        return variables;
    }

    /**
     * parses a method call into a method
     * @param line string of method call
     * @param lineNum line number of method call
     * @return the method
     */
    public static Method parseMethodCallFromLine(TypeOfLine typeOfLine, String line, int lineNum) {
        Pattern pattern = typeOfLine.getPattern();
        Matcher matcher = pattern.matcher(line);

        matcher.matches();

        // Extract the method name (group 1)
        String methodName = matcher.group(1);

        String argumentsGroup = matcher.group(3).trim();
        ArrayList<Variable> arguments = new ArrayList<>();

        if (!argumentsGroup.isEmpty()) {

            String[] argumentTokens = argumentsGroup.split("\\s*,\\s*");

            for (String token : argumentTokens) {
                VariableTypes type = VariableTypes.fromValue(token);

                if (type == null) {
                    arguments.add(new Variable(null, token, false, false, "", false,lineNum));
                }
                else{
                    arguments.add(new Variable(type, token, true, false, token, false,lineNum));
                }
            }
        }

        return new Method(methodName, arguments, lineNum);
    }
}
