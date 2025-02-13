package image_char_matching;

import java.util.TreeMap;


/**
 * The SubImgCharMatcher class is responsible for matching a given ASCII character
 * to a sub-image based on its brightness.
 */
public class SubImgCharMatcher {
    /**
     * number of blocks when converting a char into bright blocks and black blocks in Char Converter
     * @see CharConverter
     */
    public static final int NUMBER_OF_BLOCKS = 256;
    private TreeMap<Character, Double> charMapBrightNorm = new TreeMap<>();
    private TreeMap<Double, Character> charMapNorm = new TreeMap<>();
    private RoundingMethod roundingMethod = RoundingMethod.ABS;
    private double maxBrightNorm = 0.0;
    private double minBrightNorm = 1.0;

    /**
     * Constructor for SubImgCharMatcher.
     *
     * @param charset characters for ascii picture
     * @see CharConverter
     */
    public SubImgCharMatcher(char[] charset) {
        for (char c : charset) {
            double brightBlocksNorm = checkBrightBlocksNorm(c);
            charMapBrightNorm.put(c, brightBlocksNorm);
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
        if (charMapBrightNorm.containsKey(c)) {
            return;
        }
        double brightBlocksNorm = checkBrightBlocksNorm(c);
        charMapBrightNorm.put(c, brightBlocksNorm);
        if (brightBlocksNorm > maxBrightNorm) {
            maxBrightNorm = brightBlocksNorm;
            updateFinalNorm();
        }
        if (brightBlocksNorm < minBrightNorm) {
            minBrightNorm = brightBlocksNorm;
            updateFinalNorm();
        }
        double finalCNorm = getFinalNorm(brightBlocksNorm);
        charMapNorm.put(finalCNorm, c);
    }


    /**
     * this function removes a char from char set
     *
     * @param c char to remove
     */
    public void removeChar(char c) {
        if (!charMapBrightNorm.containsKey(c)) {
            return;
        }
        double brightBlocksNorm = charMapBrightNorm.get(c);
        charMapBrightNorm.remove(c);
        if (brightBlocksNorm == maxBrightNorm || brightBlocksNorm == minBrightNorm) {
            checkMinAndMaxBrightNorm();
            updateFinalNorm();
        }
        else {
            double finalCNorm = getFinalNorm(brightBlocksNorm);
            charMapNorm.remove(finalCNorm);
        }
    }

    /**
     * this function checks the norm of a given char.
     *
     * @return norm value of char.
     * @see CharConverter
     */
    private double checkBrightBlocksNorm(char c) {
        boolean[][] boolArray = CharConverter.convertToBoolArray(c);
        int numBrightBlocks = 0;

        for (boolean[] booleans : boolArray) {
            for (boolean aBoolean : booleans) {
                if (aBoolean) {
                    numBrightBlocks++;
                }
            }
        }
        return (double) numBrightBlocks / NUMBER_OF_BLOCKS;
    }

    /**
     * this function is responsible for initializing the data structures in class
     */
    private void initializeNorm() {
        checkMinAndMaxBrightNorm();
        updateFinalNorm();
    }

    /**
     * this function updates the final norm of all chars
     */
    private void updateFinalNorm() {
        charMapNorm.clear();
        for (char c : charMapBrightNorm.keySet()) {
            double finalNorm = getFinalNorm(charMapBrightNorm.get(c));
            charMapNorm.put(finalNorm, c);
        }
    }

    /**
     * this function checks who are the max BrightNorm and the min BrightNorm
     */
    private void checkMinAndMaxBrightNorm() {
        maxBrightNorm = 0.0;
        minBrightNorm = 1.0;
        for (char c : charMapBrightNorm.keySet()) {
            double charNorm = charMapBrightNorm.get(c);
            if (charNorm > maxBrightNorm) {
                maxBrightNorm = charNorm;
            }
            if (charNorm < minBrightNorm) {
                minBrightNorm = charNorm;
            }
        }
    }

    /**
     * this fucntions checks the final norm of a given number according to min and max norm
     *
     * @param brightNorm the bright norm of a char
     * @return the Final Norm of char. in case of only one char in class (maxBrightNorm ==
     * minBrightNorm) returns 0
     */
    private double getFinalNorm(double brightNorm) {
        if (maxBrightNorm != minBrightNorm) {
            return (brightNorm - minBrightNorm) / (maxBrightNorm - minBrightNorm);
        }
        return 0;
    }
}
