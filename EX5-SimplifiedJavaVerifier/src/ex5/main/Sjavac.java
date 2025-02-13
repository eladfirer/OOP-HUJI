package ex5.main;

import ex5.inspector.IllegalLineException;
import ex5.inspector.Inspector;
import ex5.inspector.checkers.*;
import ex5.util.FileLoader;

import java.io.IOException;

/**
 * main class that runs all the program
 */
public class Sjavac {

    private static final String ILLEGAL_NUMBER_OF_ARGUMENTS_FOR_THE_PROGRAM = "Illegal number of " +
            "arguments for the program";
    private static final String ONLY_SJAVA_FILES_ARE_ALLOWED = "Invalid file type. Only .sjava " +
            "files are allowed: ";

    /**
     * main methos that runs all program
     *
     * @param args arguments from command line
     */
    public static void main(String[] args) {

        try {
            if (args.length != 1) {
                throw new IOException(ILLEGAL_NUMBER_OF_ARGUMENTS_FOR_THE_PROGRAM);
            }
            String filePath = args[0];
            if (!filePath.endsWith(".sjava")) {
                throw new IOException(ONLY_SJAVA_FILES_ARE_ALLOWED + filePath);
            }
            FileLoader fileLoader = new FileLoader(filePath);
            Inspector inspector = new Inspector(fileLoader);
            inspector.checkCode();
            System.out.println(ProgramOutput.SUCCESS.getValue());
        }
        // in the requirements for exercise we instructed to catch any exception separately
        catch (IllegalVariableDeclarationException e) {
            System.out.println(ProgramOutput.FAILURE.getValue());
            System.err.println(e.getMessage());
        } catch (IllegalVariableAssigmentException e) {
            System.out.println(ProgramOutput.FAILURE.getValue());
            System.err.println(e.getMessage());
        } catch (IllegalMethodDeclarationException e) {
            System.out.println(ProgramOutput.FAILURE.getValue());
            System.err.println(e.getMessage());
        } catch (IllegalMethodCallException e) {
            System.out.println(ProgramOutput.FAILURE.getValue());
            System.err.println(e.getMessage());
        } catch (IllegalConditionException e) {
            System.out.println(ProgramOutput.FAILURE.getValue());
            System.err.println(e.getMessage());
        } catch (IllegalLineException e) {
            System.out.println(ProgramOutput.FAILURE.getValue());
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(ProgramOutput.ERROR.getValue());
            System.err.println(e.getMessage());
        }

    }
}

