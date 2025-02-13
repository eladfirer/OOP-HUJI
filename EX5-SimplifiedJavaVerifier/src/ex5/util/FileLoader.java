package ex5.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * this class is responsible for reading data from sjavac files.
 */
public class FileLoader {


    private static final String FILE_NOT_FOUND = "File not found: ";
    private static final String ERROR_READING_FILE = "Error reading file: ";
    private final ArrayList<String> lines = new ArrayList<>();
    private int currentIndex;

    /**
     * Constructor for class. responsible for checking if the file is legal and extracting data
     *
     * @param filePath to required file
     * @throws IOException in case of an error in reading and extracting data from file
     */
    public FileLoader(String filePath) throws IOException {
        currentIndex = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (FileNotFoundException e) {
            throw new IOException(FILE_NOT_FOUND + filePath);
        } catch (IOException e) {
            throw new IOException(ERROR_READING_FILE + filePath);
        }
    }

    /**
     * checks if the current line index has still a line
     *
     * @return true in case of positive answer, false otherwise
     */
    public boolean hasMoreLines() {
        return currentIndex < lines.size();
    }


    /**
     * @return the current line as a string. null in case of current index exceeding number of lines
     */
    public String getCurrentLine() {
        if ((this.currentIndex >= this.lines.size()) || (this.currentIndex < 0)) {
            return null;
        }
        return this.lines.get(this.currentIndex);
    }

    /**
     * reset the line index to 0
     */
    public void resetLines() {
        this.currentIndex = 0;
    }

    /**
     * advances the line index by one to next line
     */
    public void advanceToNextLine() {
        this.currentIndex++;
    }

    /**
     * reduces the line index by one to the line before
     */
    public void goBackALine() {
        this.currentIndex--;
    }

    /**
     * @return the current line index
     */
    public int getCurrentIndex() {
        return currentIndex;
    }
}
