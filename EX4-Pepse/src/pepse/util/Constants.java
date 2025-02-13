package pepse.util;

import danogl.collisions.Layer;
import danogl.util.Vector2;

import java.awt.*;
import java.util.List;
import java.util.Random;

/**
 * class for saving all Game Constants
 */
public final class Constants {


    private Constants() {
    }

    /**
     * LAYERS IN GAME.
     */
    public static final int LAYER_TREE = Layer.DEFAULT + 5; // LAYER ONLY FOR TREES!
    public static final Integer LAYER_LOG = Layer.DEFAULT;
    public static final int LAYER_AVATAR = Layer.DEFAULT;
    public static final int LAYER_CLOUD = Layer.BACKGROUND + 2;
    public static final int LAYER_UPPER_GROUND = Layer.STATIC_OBJECTS;
    public static final int LAYER_LOWER_GROUND = Layer.STATIC_OBJECTS - 1;
    public static final int LAYER_LEAVES = Layer.FOREGROUND - 1;
    public static final int LAYER_SUN_HALO = Layer.BACKGROUND + 2;
    public static final int LAYER_SUN = Layer.BACKGROUND + 1;
    public static final int[] LAYERS_TO_REMOVE_IN_GAME = {LAYER_TREE, LAYER_UPPER_GROUND,
            LAYER_LOWER_GROUND}; // Layers to be removed while updating game


    /**
     * day and night constants
     */
    public static final Float DAYLIGHT_OPACITY = 0.0f;
    public static final Float MIDNIGHT_OPACITY = 0.5f;
    public static final int CYCLE_LENGTH = 30;
    public static final String NIGHT = "night";


    /**
     * sun constants
     */
    public static final float SUN_RADIUS = 100;
    public static final String SUN = "sun";
    public static final Color COLOR_SUN_HALO = new Color(255, 255, 0, 20);
    public static final float SUN_HALO_RADIUS = SUN_RADIUS * 1.5F;
    public static final String SUN_HALO = "sunHalo";


    /**
     * window size for game
     */
    public static final Vector2 WINDOW_SIZE = new Vector2(1290, 720);

    /**
     * sky constants
     */
    public static final String SKY = "sky";
    public static final Color BASIC_SKY_COLOR = Color.decode("#80C6E5");


    /**
     * blocks and ground constants
     */
    public static final String GROUND = "ground";
    public static final int BLOCK_SIZE = 30;
    public static final Color BASE_GROUND_COLOR =
            new Color(212, 123,
                    74);
    public static final int TERRAIN_DEPTH = 30;
    public static final float NOISE_SCALE = 500.0f;
    public static final float NOISE_AMPLITUDE = 400.0f;
    public static final int BUFFER = 180;


    /**
     * avatar rendering constants
     */
    public static final String AVATAR_INITIAL_IMAGE = "assets/idle_0.png";
    public static final String[] IDLE_PATHS = {"assets/idle_0.png",
            "assets/idle_1.png",
            "assets/idle_2.png",
            "assets/idle_3.png"};

    public static final String[] JUMP_PATHS = {"assets/jump_0.png",
            "assets/jump_1.png",
            "assets/jump_2.png",
            "assets/jump_3.png"};

    public static final String[] RUN_PATHS = {"assets/run_0.png",
            "assets/run_1.png",
            "assets/run_2.png",
            "assets/run_3.png",
            "assets/run_4.png",
            "assets/run_5.png"};
    public static final double TIME_BETWEEN_CLIPS = 0.1;
    public static final Vector2 AVATAR_SIZE = new Vector2(50, 60);


    /**
     * avatar physics constants constants
     */
    public static final float VELOCITY_X = 400;
    public static final float VELOCITY_Y = -650;
    public static final float GRAVITY = 600f;

    /**
     * UI constants
     */
    public static final Vector2 COUNTER_SIZE = new Vector2(50, 20);


    /**
     * log constants
     */
    public static final String LOG = "log";
    public static final Color LOG_COLOR = new Color(100, 50, 20);
    public static final Color LEAF_COLOR = new Color(50, 200, 30);


    /**
     * fruit constants
     */
    public static Color getRandomFruitColor(Random random) {
        Color[] fruitColors = {Color.RED, Color.ORANGE, Color.YELLOW};
        return fruitColors[random.nextInt(fruitColors.length)];
    }

    public static final int WAIT_TIME_FOR_FRUIT = 30;


    /**
     * cloud constants
     */
    public static final Color BASE_CLOUD_COLOR =
            new Color(255, 255,
                    255);
    public static final java.util.List<java.util.List<Integer>> CLOUD_SHAPE = java.util.List.of(
            java.util.List.of(0, 1, 1, 0, 0, 0),
            java.util.List.of(1, 1, 1, 0, 1, 0),
            java.util.List.of(1, 1, 1, 1, 1, 1),
            java.util.List.of(1, 1, 1, 1, 1, 1),
            java.util.List.of(0, 1, 1, 1, 0, 0),
            List.of(0, 0, 0, 0, 0, 0)
    );
}


