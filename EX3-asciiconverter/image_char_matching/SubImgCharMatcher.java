package image_char_matching;

import java.util.TreeMap;
import java.util.TreeSet;

/**
 * The SubImgCharMatcher class is responsible for matching a given ASCII character
 * to a sub-image based on its brightness.
 */
public class SubImgCharMatcher {
    public enum RoundingMethod {UP, DOWN, ABS}

    private TreeMap<Character, Integer> charMapBrightKeys = new TreeMap<>();
    private TreeMap<Double, Character> charMapNorm = new TreeMap<>();
    private RoundingMethod roundingMethod = RoundingMethod.ABS;

    /**
     * Constructor for SubImgCharMatcher.
     *
     * @param charset characters for ascii picture
     * @see CharConverter
     */
    public SubImgCharMatcher(TreeSet<Character> charset) {
        for (char c : charset) {
            int numOfBrightBlocks = checkBrightBlocks(c);
            charMapBrightKeys.put(c, numOfBrightBlocks);
        }
        initializeNorm();
    }

    /**
     * Sets the rounding method for brightness calculation.
     *
     * @param method RoundingMethod (UP, DOWN, ABS)
     */
    public void setRoundingMethod(RoundingMethod method) {
        this.roundingMethod = method;
    }

    /**
     * method to get the closest character to a given brightness
     *
     * @param brightness - given brightness
     * @return the closest character to the given brightness
     */
    public char getCharByImageBrightness(double brightness) {
        Double key;

        switch (roundingMethod) {
            case UP:
                key = charMapNorm.ceilingKey(brightness);
                break;
            case DOWN:
                key = charMapNorm.floorKey(brightness);
                break;
            case ABS:
            default:
                Double floor = charMapNorm.floorKey(brightness);
                Double ceiling = charMapNorm.ceilingKey(brightness);

                if (floor == null) {
                    key = ceiling;
                }
                else if (ceiling == null) {
                    key = floor;
                }
                else {
                    key = Math.abs(brightness - ceiling) < Math.abs(brightness - floor) ?
                            ceiling : floor;
                }
                break;
        }
        return charMapNorm.get(key);
    }

    /**
     * this function adds char to char set
     *
     * @param c char to add
     */
    public void addChar(char c) {
        if (charMapBrightKeys.containsKey(c)) {
            return;
        }
        int brightBlocks = checkBrightBlocks(c);
        charMapBrightKeys.put(c, brightBlocks);
        initializeNorm();
    }

    /**
     * this function removes a char from char set
     *
     * @param c char to remove
     */
    public void removeChar(char c) {
        if (!charMapBrightKeys.containsKey(c)) {
            return;
        }
        charMapBrightKeys.remove(c);
        initializeNorm();
    }

    /**
     * this function checks the norm of a given char.
     *
     * @return norm value of char.
     * @see CharConverter
     */
    private int checkBrightBlocks(char c) {
        boolean[][] boolArray = CharConverter.convertToBoolArray(c);
        int numBrightBlocks = 0;

        for (boolean[] booleans : boolArray) {
            for (boolean aBoolean : booleans) {
                if (aBoolean) {
                    numBrightBlocks++;
                }
            }
        }
        return numBrightBlocks;
    }

    /**
     * this function is responsible for checking the characters norm
     * and updating charMapNorm accordingly
     */
    private void initializeNorm() {
        int numOfChars = charMapBrightKeys.size();
        double max = 0;
        double min = 1;
        double[] normalizedBrightness = new double[numOfChars];
        int i = 0;

        for (char c : charMapBrightKeys.keySet()) {
            double charNorm = (double) charMapBrightKeys.get(c) / 256;
            if (charNorm > max) {
                max = charNorm;
            }
            else if (charNorm < min) {
                min = charNorm;
            }
            normalizedBrightness[i] = charNorm;
            i++;
        }

        charMapNorm.clear();
        i = 0;

        for (char c : charMapBrightKeys.keySet()) {
            normalizedBrightness[i] = (normalizedBrightness[i] - min) / (max - min);
            charMapNorm.put(normalizedBrightness[i], c);
            i++;
        }
    }
}
