package image_char_matching;

/**
 * this enum is for defining in which state the program should round the brightness.
 */
public enum RoundingMethod {
    /**
     * Rounds the brightness value up to the nearest available value.
     */
    UP,

    /**
     * Rounds the brightness value down to the nearest available value.
     */
    DOWN,

    /**
     * Rounds the brightness value to the nearest available value,
     * using the absolute difference.
     */
    ABS
}
