package ascii_art;

import image.Image;
import image.ImageConverter;
import image_char_matching.SubImgCharMatcher;

/**
 * this class is responsible for a single run of the ascii art algorithm
 */
public class AsciiArtAlgorithm {

    private final Image image;
    private final int pixelsPerRow;
    private final SubImgCharMatcher charSet;

    /**
     * Constructor of AsciiArtAlgorithm
     * @param image image to make ascii
     * @param pixelPerRow resolution of ascii image
     * @param charSet chars to create ascii image
     * @see Image
     * @see SubImgCharMatcher
     */
    public AsciiArtAlgorithm(Image image, int pixelPerRow, SubImgCharMatcher charSet) {
        this.image = image;
        this.pixelsPerRow = pixelPerRow;
        this.charSet = charSet;
    }


    /**
     * this function uses Image Converter to get the brightness of sub images of
     * image and then converts them into chars.
     * @return the char table representing the characters in the ascii art image.
     * @see ImageConverter
     * @see SubImgCharMatcher
     */
    public char [][] run(){
        ImageConverter imageConverter = ImageConverter.getInstance();
        imageConverter.insertImage(image);
        double[][] brightnessSubImages = imageConverter.getImageBrightness(pixelsPerRow);
        char[][] charsValue = new char[brightnessSubImages.length][brightnessSubImages[0].length];
        for (int i = 0; i < brightnessSubImages.length; i++) {
            for (int j = 0; j < brightnessSubImages[0].length; j++) {
                charsValue[i][j] = charSet.getCharByImageBrightness(brightnessSubImages[i][j]);
            }
        }
        return charsValue;
    }


}
