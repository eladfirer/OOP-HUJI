package image;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

/**
 * The ImageConverter class is responsible for converting a given image.
 * a singleton class.
 */
public class ImageConverter {

    private static ImageConverter imageConverterObject;
    private Image image;
    private boolean sameImage;
    private int pixelsPerSubImage;
    private double[][] brightnessArray;

    /**
     * private constructor declaration for singleton
     */
    private ImageConverter() {
        sameImage = false;
        image = null;
        pixelsPerSubImage = -1;
    }

    /**
     * method for singleton
     *
     * @return static object
     */
    public static ImageConverter getInstance() {
        if (imageConverterObject == null) {
            imageConverterObject = new ImageConverter();
        }
        return imageConverterObject;
    }


    /**
     * this function is responsible for inserting an image into ImageConverter
     *
     * @param image image to be inserted
     * @see Image
     */
    public void insertImage(Image image) {
        int height = image.getHeight();
        int width = image.getWidth();
        int newHeight = closestHigherPowerOfTwo(height);
        int newWidth = closestHigherPowerOfTwo(width);
        Color[][] pixelArray = new Color[newHeight][newWidth];


        if (sameImage && (newHeight != this.image.getHeight()
                || newWidth != this.image.getWidth())) {
            sameImage = false;
        }
        int widthPadding = (newWidth - width) / 2;
        int heightPadding = (newHeight - height) / 2;
        for (int i = 0; i < newHeight; i++) {
            for (int j = 0; j < newWidth; j++) {
                if (i < heightPadding) {
                    pixelArray[i][j] = Color.WHITE;
                }
                else if (j < widthPadding) {
                    pixelArray[i][j] = Color.WHITE;
                }
                else if (i >= heightPadding + height) {
                    pixelArray[i][j] = Color.WHITE;
                }
                else if (j >= widthPadding + width) {
                    pixelArray[i][j] = Color.WHITE;
                }
                else {
                    pixelArray[i][j] = image.getPixel(i - heightPadding, j - widthPadding);
                }
                if (sameImage && pixelArray[i][j] != this.image.getPixel(i, j)) {
                    sameImage = false;
                }
            }
        }
        this.image = new Image(pixelArray, newWidth, newHeight);
    }


    /**
     * this class is responsible for converting the image into sub images and
     * calculating each sub image gray pixel.
     *
     * @param pixelsPerRow how many pixels should be in a row in the fixed image.
     * @return double array representing gray scale values of image
     */
    public double[][] getImageBrightness(int pixelsPerRow) {
        // in case of same resolution & same image (program can be expanded to change images
        // while running) as previous run, return same brightness array
        if (sameImage && image.getWidth() / pixelsPerRow == this.pixelsPerSubImage) {
            return brightnessArray;
        }

        this.pixelsPerSubImage = image.getWidth() / pixelsPerRow;

        int reScaledHeight = image.getHeight() / pixelsPerSubImage;
        int reScaledWidth = pixelsPerRow;
        brightnessArray = new double[reScaledHeight][reScaledWidth];
        for (int i = 0; i < reScaledHeight; i++) {
            for (int j = 0; j < reScaledWidth; j++) {

                double greyPixel = 0;

                for (int k = 0; k < pixelsPerSubImage; k++) {
                    for (int l = 0; l < pixelsPerSubImage; l++) {
                        int x = i * pixelsPerSubImage + k;
                        int y = j * pixelsPerSubImage + l;
                        Color pixel = image.getPixel(x, y);

                        greyPixel += pixel.getRed() * 0.2126 + pixel.getGreen() * 0.7152
                                + pixel.getBlue() * 0.0722;
                    }
                }

                brightnessArray[i][j] = greyPixel / (pixelsPerSubImage * pixelsPerSubImage * 255.0);
            }
        }
        return brightnessArray;
    }


    /**
     * checks what's the closest ceiling int that is power of 2
     * for a given int
     *
     * @param n the int to be checked
     * @return the closest ceiling int to n.
     * in the case that n is power of 2 returns n.
     */
    public static int closestHigherPowerOfTwo(int n) {
        if (n <= 0) {
            return 1;
        }
        n--;
        n |= n >> 1;
        n |= n >> 2;
        n |= n >> 4;
        n |= n >> 8;
        n |= n >> 16;
        return n + 1;
    }
}
