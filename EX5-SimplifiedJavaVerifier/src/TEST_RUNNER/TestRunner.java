package TEST_RUNNER;

import ex5.main.Sjavac;

import java.io.*;
import java.nio.file.*;

public class TestRunner {
    public static void main(String[] args) {
        // Directory containing test files
        String testsDir = "supplied_material/tests";
        // Directory to store the output files
        String outputDir = "supplied_material/tests_outputs";

        // Create the output directory if it doesn't exist
        File outputDirectory = new File(outputDir);
        if (!outputDirectory.exists()) {
            outputDirectory.mkdir();
        }

        try {
            // Get all files in the tests directory
            File dir = new File(testsDir);
            File[] testFiles = dir.listFiles();

            if (testFiles == null || testFiles.length == 0) {
                System.out.println("No test files found in the directory: " + testsDir);
                return;
            }

            // Iterate through each test file
            for (File testFile : testFiles) {
                if (testFile.isFile()) { // Ensure it's a file and not a subdirectory
                    // Get the name of the test file
                    String testFileName = testFile.getName();
                    // Output file path
                    String outputFilePath = outputDir + File.separator + testFileName + ".out";

                    // Redirect output to the output file
                    PrintStream originalOut = System.out; // Save original System.out
                    try (PrintStream output = new PrintStream(new FileOutputStream(outputFilePath))) {
                        System.setOut(output); // Redirect System.out to the file

                        // Run Sjavac main with the test file as an argument
                        Sjavac.main(new String[]{testFile.getAbsolutePath()});
                    } finally {
                        System.setOut(originalOut); // Restore original System.out
                    }
                    System.out.println("Processed test: " + testFileName + " -> Output: " + outputFilePath);
                }
            }
        } catch (Exception e) {
            System.err.println();;
        }
    }
}