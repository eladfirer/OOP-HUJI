package ex5.inspector;


import ex5.inspector.parsers.GeneralTypeOfLineMatcher;
import ex5.util.FileLoader;
import ex5.util.IChecker;

/**
 * this class checks if there is an illegal line program (line that doesn't match any syntax line)
 */
public class IllegalLineChecker implements IChecker {


    private static final String IS_A_FORBIDDEN_LINE = " is a forbidden line: ";
    private static final String LINE = "Line ";
    private final FileLoader fileLoader;

    /**
     * Constructor for class
     * @param fileLoader file loader of current file
     */
    public IllegalLineChecker(FileLoader fileLoader) {
        this.fileLoader = fileLoader;
    }

    /**
     * checks if there is an illegal line program (line that doesn't match any syntax line)
     * @throws IllegalLineException in case of an illegal line in program throws exception
     */
    public void check() throws IllegalLineException {
        while(fileLoader.hasMoreLines()){
            checkLine(fileLoader.getCurrentLine(),fileLoader.getCurrentIndex());
            fileLoader.advanceToNextLine();
        }
        fileLoader.resetLines();
    }
    private void checkLine(String line, int lineNumber) throws IllegalLineException {
        if(GeneralTypeOfLineMatcher.checkForAnyType(line) == null){
            throw new IllegalLineException(LINE + (lineNumber + 1) + IS_A_FORBIDDEN_LINE + line);
        }
    }

}
