package image_char_matching;

import java.util.TreeMap;

/**
 * The SubImgCharMatcher class is responsible for matching a given ASCII character
 * to a sub-image based on its brightness.
 */
public class SubImgCharMatcher {
    private TreeMap<Character, Integer> charMapBrightKeys = new TreeMap<>();
    private TreeMap<Double, Character> charMapNorm = new TreeMap<>();
    /**
     * Constructor for SubImgCharMatcher.
     *
     * @param charset characters for ascii picture
     * @see CharConverter
     */
    public SubImgCharMatcher(char[] charset) {
        for (int i = 0; i < charset.length; i++) {
            char c = charset[i];
            int numOfBrightBlocks = checkBrightBlocks(c);
            charMapBrightKeys.put(c,numOfBrightBlocks);
        }
        initializeNorm();
    }

    /**
     * method to get the closest character to a given brightness
     * @param brightness - given brightness
     * @return the closest character to the given brightness
     */
    public char getCharByImageBrightness(double brightness) {
        Double floor = charMapNorm.floorKey(brightness);
        Double ceiling = charMapNorm.ceilingKey(brightness);
        Double nearestKey;
        if(floor == null){
            nearestKey = ceiling;
        }
        else if(ceiling == null){
            nearestKey = floor;
        }
        else{
            nearestKey = Math.abs(brightness - ceiling) < Math.abs(brightness - floor) ? ceiling : floor;
        }
        return charMapNorm.get(nearestKey);
    }

    /**
     * this function adds char to char set
     * @param c char to add
     */
    public void addChar(char c) {
        if(charMapBrightKeys.containsKey(c)){
            return;
        }
        int brightBlocks = checkBrightBlocks(c);
        charMapBrightKeys.put(c,brightBlocks);
        initializeNorm();
    }

    /**
     * this function removes a char from char set
     * @param c char to remove
     */
    public void removeChar(char c) {
        if(!charMapBrightKeys.containsKey(c)){
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
    private int checkBrightBlocks(char c){
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
    private void initializeNorm(){
        int numOfChars = charMapBrightKeys.size();
        double max = 1;
        double min = 0;
        double[] normalizedBrightness = new double[numOfChars];
        int i=0;

        for (char c: charMapBrightKeys.keySet()) {
            double charNorm = (double) charMapBrightKeys.get(c) / numOfChars;
            if(charNorm > max){
                max = charNorm;
            }
            else if(charNorm < min){
                min = charNorm;
            }
            normalizedBrightness[i] = charNorm;
            i++;
        }

        charMapNorm.clear();
        i = 0;

        for (char c: charMapBrightKeys.keySet()){
            normalizedBrightness[i] = (normalizedBrightness[i] - min)/(max-min);
            charMapNorm.put(normalizedBrightness[i], c);
            i++;
        }
    }
}
