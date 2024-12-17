package ascii_art;

import ascii_output.ConsoleAsciiOutput;
import ascii_output.HtmlAsciiOutput;
import image.Image;
import image.ImageConverter;
import image_char_matching.SubImgCharMatcher;

import java.io.IOException;
import java.util.TreeSet;

/**
 * Shell - this class is responsible for running the UI
 */
public class Shell {
    private static final String ERROR_RESOLUTION = "Did not change resolution due to incorrect " +
            "format.";
    private static final String ERROR_RESOLUTION_BOUNDARY = "Did not change resolution due to " +
            "exceeding boundaries.";
    private static final String ERROR_ADD = "Did not add due to incorrect format.";
    private static final String ERROR_REMOVE = "Did not remove due to incorrect format.";
    private static final String ERROR_ROUNDING = "Did not change rounding method due to incorrect" +
            " format.";
    private static final String ERROR_OUTPUT = "Did not change output method due to incorrect " +
            "format.";
    private static final String ERROR_SMALL_CHARSET = "Did not execute. Charset is too small.";
    private static final String ERROR_INVALID_COMMAND = "Did not execute due to incorrect command.";

    private int resolution = 2; // Default resolution
    private int maxResolution;
    private int minResolution;
    private String outputDestination = "console";

    /**
     * this function is responsible for running the program; taking input from user,
     * calling methods in class and catching exceptions.
     *
     * @param imageName - image path
     * @see Image
     * @see ImageConverter
     * @see KeyboardInput
     * @see SubImgCharMatcher
     * @see InvalidCommandException
     * @see ResolutionException
     * @see CharsetException
     * @see RoundException
     * @see OutputException
     */
    public void run(String imageName) {
        try {
            Image image = new Image(imageName);
            TreeSet<Character> charsSet = new TreeSet<>();
            int imgWidth = ImageConverter.closestHigherPowerOfTwo(image.getWidth());
            int imgHeight = ImageConverter.closestHigherPowerOfTwo(image.getHeight());
            maxResolution = imgWidth;
            minResolution = Math.max(1, imgWidth / imgHeight);
            for (int i = 0; i < 10; i++) {
                charsSet.add((char) (i + '0'));
            }
            SubImgCharMatcher charsOrganizer = new SubImgCharMatcher(charsSet);

            while (true) {
                System.out.println(">>> ");
                String playerInput = KeyboardInput.readLine().trim();
                String[] parts = playerInput.split("\\s+");
                String firstInput = parts[0];
                try {
                    switch (firstInput) {
                        case "exit":
                            return;
                        case "chars":
                            printChars(charsSet);
                            break;
                        case "add":
                            handleAddCommand(parts, charsSet, charsOrganizer);
                            break;
                        case "remove":
                            handleRemoveCommand(parts, charsSet, charsOrganizer);
                            break;
                        case "res":
                            handleResCommand(parts, imgWidth, imgHeight);
                            break;
                        case "round":
                            handleRoundCommand(parts, charsOrganizer);
                            break;
                        case "output":
                            handleOutputCommand(parts);
                            break;
                        case "asciiArt":
                            handleAsciiArt(image, resolution, charsOrganizer, charsSet);
                            break;
                        default:
                            throw new InvalidCommandException(ERROR_INVALID_COMMAND);
                    }
                } catch (InvalidCommandException | ResolutionException | CharsetException |
                         OutputException | RoundException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * print the chars currently inside set in ascending order
     *
     * @param charsSet - set containg all chars curretly in program
     */
    private void printChars(TreeSet<Character> charsSet) {
        for (char c : charsSet) {
            System.out.print(c + " ");
        }
        System.out.println();
    }

    /**
     * this method runs the program in the case the user wants to add chars into
     * the charSystem
     *
     * @param parts          player input
     * @param charsSet       TreeSet of chars
     * @param charsOrganizer SubImgCharMatcher who matches the chars to their brightness
     * @throws CharsetException Exception in case of invalid input
     * @see SubImgCharMatcher
     * @see TreeSet
     */
    private void handleAddCommand(String[] parts, TreeSet<Character> charsSet,
                                  SubImgCharMatcher charsOrganizer) throws CharsetException {
        if (parts.length < 2) {
            throw new CharsetException(ERROR_ADD);
        }

        String argument = parts[1];

        if (argument.length() == 1) {
            char c = argument.charAt(0);
            if (isValidAscii(c)) {
                if (!charsSet.contains(c)) {
                    charsOrganizer.addChar(c);
                    charsSet.add(c);
                }
            }
            else {
                throw new CharsetException(ERROR_ADD);
            }
        }
        else if (argument.equals("all")) {
            // Add all valid ASCII characters (32 to 126)
            for (char c = 32; c <= 126; c++) {
                if (!charsSet.contains(c)) {
                    charsOrganizer.addChar(c);
                    charsSet.add(c);
                }
            }
        }
        else if (argument.equals("space")) {
            if (!charsSet.contains(' ')) {
                charsOrganizer.addChar(' ');
                charsSet.add(' ');
            }
        }
        else if (argument.contains("-")) {
            // Add range of characters
            handleRange(argument, charsSet, charsOrganizer, true);
        }
        else {
            throw new CharsetException(ERROR_ADD);
        }
    }

    /**
     * this method runs the program in the case the user wants to remove chars from
     * the charSystem
     *
     * @param parts          player input
     * @param charsSet       TreeSet of chars
     * @param charsOrganizer SubImgCharMatcher who matches the chars to their brightness
     * @throws CharsetException Exception in case of invalid input
     * @see SubImgCharMatcher
     * @see TreeSet
     */
    private void handleRemoveCommand(String[] parts, TreeSet<Character> charsSet,
                                     SubImgCharMatcher charsOrganizer) throws CharsetException {
        if (parts.length < 2) {
            throw new CharsetException(ERROR_REMOVE);
        }

        String argument = parts[1];

        if (argument.length() == 1) {
            char c = argument.charAt(0);
            if (charsSet.contains(c)) {
                charsOrganizer.removeChar(c);
                charsSet.remove(c);
            }
        }
        else if (argument.equals("all")) {
            // Remove all valid ASCII characters (32 to 126)
            for (char c = 32; c <= 126; c++) {
                if (charsSet.contains(c)) {
                    charsOrganizer.removeChar(c);
                    charsSet.remove(c);
                }
            }
        }
        else if (argument.equals("space")) {
            if (charsSet.contains(' ')) {
                charsOrganizer.removeChar(' ');
                charsSet.remove(' ');
            }
        }
        else if (argument.contains("-")) {
            // Remove range of characters
            handleRange(argument, charsSet, charsOrganizer, false);
        }
        else {
            throw new CharsetException(ERROR_REMOVE);
        }
    }

    /**
     * this method runs the program in the case the user wants to add/remove chars from
     * the chars system.
     *
     * @param range          player desired range
     * @param charsSet       TreeSet of chars
     * @param charsOrganizer SubImgCharMatcher who matches the chars to their brightness
     * @throws CharsetException Exception in case of invalid input
     * @see SubImgCharMatcher
     * @see TreeSet
     */
    private void handleRange(String range, TreeSet<Character> charsSet,
                             SubImgCharMatcher charsOrganizer, boolean isAdd)
            throws CharsetException {
        String[] bounds = range.split("-");
        if (bounds.length == 2 && bounds[0].length() == 1 && bounds[1].length() == 1) {
            char start = bounds[0].charAt(0);
            char end = bounds[1].charAt(0);

            if (isValidAscii(start) && isValidAscii(end)) {
                if (start <= end) {
                    for (char c = start; c <= end; c++) {
                        if (isAdd && !charsSet.contains(c)) {
                            charsOrganizer.addChar(c);
                            charsSet.add(c);
                        }
                        else if (!isAdd && charsSet.contains(c)) {
                            charsOrganizer.removeChar(c);
                            charsSet.remove(c);
                        }
                    }
                }
                else {
                    for (char c = start; c >= end; c--) {
                        if (isAdd && !charsSet.contains(c)) {
                            charsOrganizer.addChar(c);
                            charsSet.add(c);
                        }
                        else if (!isAdd && charsSet.contains(c)) {
                            charsOrganizer.removeChar(c);
                            charsSet.remove(c);
                        }
                    }
                }
            }
            else {
                if (isAdd) {
                    throw new CharsetException(ERROR_ADD);
                }
                else {
                    throw new CharsetException(ERROR_REMOVE);
                }
            }
        }
        else {
            if (isAdd) {
                throw new CharsetException(ERROR_ADD);
            }
            else {
                throw new CharsetException(ERROR_REMOVE);
            }
        }
    }

    /**
     * this method is being called in case the user wants to change/see resolution
     * of program
     *
     * @param parts     user inout
     * @param imgWidth  image width
     * @param imgHeight image height
     * @throws ResolutionException throws exception in case of invalid input/program limitations
     */
    private void handleResCommand(String[] parts, int imgWidth, int imgHeight)
            throws ResolutionException {
        if (parts.length == 1) {
            System.out.println("Resolution set to " + resolution + ".");
        }
        else {
            String argument = parts[1];

            if (argument.equals("up")) {
                int newResolution = resolution * 2;
                if (newResolution <= maxResolution) {
                    resolution = newResolution;
                    System.out.println("Resolution set to " + resolution + ".");
                }
                else {
                    throw new ResolutionException(ERROR_RESOLUTION_BOUNDARY);
                }
            }
            else if (argument.equals("down")) {
                int newResolution = resolution / 2;
                if (newResolution >= minResolution) {
                    resolution = newResolution;
                    System.out.println("Resolution set to " + resolution + ".");
                }
                else {
                    throw new ResolutionException(ERROR_RESOLUTION_BOUNDARY);
                }
            }
            else {
                throw new ResolutionException(ERROR_RESOLUTION);
            }
        }
    }

    /**
     * this method is being called in case the user wants to change the way
     * charsOrganizer the parameter defining which character is most similar in
     * brightness to a given sub image
     *
     * @param parts          use input
     * @param charsOrganizer SubImgCharMatcher who matches the chars to their brightness
     * @throws RoundException throws round exception in case the user did give a valid input
     */
    private void handleRoundCommand(String[] parts, SubImgCharMatcher charsOrganizer) throws RoundException {

        String argument = parts[1];

        switch (argument) {
            case "up":
                charsOrganizer.setRoundingMethod(SubImgCharMatcher.RoundingMethod.UP);
                break;
            case "down":
                charsOrganizer.setRoundingMethod(SubImgCharMatcher.RoundingMethod.DOWN);
                break;
            case "abs":
                charsOrganizer.setRoundingMethod(SubImgCharMatcher.RoundingMethod.ABS);
                break;
            default:
                throw new RoundException(ERROR_ROUNDING);
        }
    }

    /**
     * this method is being called in case the player wants to change the type of rendering
     * output
     *
     * @param parts - player input
     * @throws OutputException - exception is being throwed in case of invalid output type
     */
    private void handleOutputCommand(String[] parts) throws OutputException {
        if (parts.length != 2) {
            throw new OutputException(ERROR_OUTPUT);
        }

        String argument = parts[1];

        switch (argument) {
            case "html":
                outputDestination = "html";
                break;
            case "console":
                outputDestination = "console";
                break;
            default:
                throw new OutputException(ERROR_OUTPUT);
        }
    }

    /**
     * this function is being called when the user wants to create a new asciiArt from an image
     *
     * @param image          image to be make into ascii art
     * @param resolution     resolution of ascii art
     * @param charsOrganizer chars matcher with brightness
     * @param charsSet       chars to be used to create ascii art
     * @throws CharsetException throws an exception in case the charsSet is too small (below 2
     *                          chars)
     */
    private void handleAsciiArt(Image image, int resolution, SubImgCharMatcher charsOrganizer,
                                TreeSet<Character> charsSet) throws CharsetException {
        if (charsSet.size() < 2) {
            throw new CharsetException(ERROR_SMALL_CHARSET);
        }

        AsciiArtAlgorithm algorithm = new AsciiArtAlgorithm(image, resolution, charsOrganizer);
        char[][] result = algorithm.run();

        if (outputDestination.equals("console")) {
            ConsoleAsciiOutput consoleAsciiOutput = new ConsoleAsciiOutput();
            consoleAsciiOutput.out(result);
        }
        else if (outputDestination.equals("html")) {
            HtmlAsciiOutput htmlAsciiOutput = new HtmlAsciiOutput("out.html", "Courier New");
            htmlAsciiOutput.out(result);
        }
    }

    /**
     * checks if a given char is valid to get into the char system program
     * @param c - char to be examined
     * @return true in case char is valid, false otherwise
     */
    private static boolean isValidAscii(char c) {
        return c >= 32 && c <= 126;
    }

    public static void main(String[] args) {
        Shell shell = new Shell();
        String imageName = args[0];
        shell.run(imageName);
    }
}

