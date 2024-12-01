package bricker.main;

import danogl.util.Vector2;

import java.awt.*;

/**
 * class for Game Constants
 */
public final class GameConstants {

    private GameConstants() {
    }

    ;

    // ball constants
    public static final float BALL_SPEED = 200;
    public static final float BALL_RADIUS = 30;
    public static final int HITS_RED_BALL = 6;
    public static final float TURBE_MODE_SPEED_MULT = 1.4f;
    public static final int NUM_PUCKS_CREATE_COLLISION = 2;
    public static final float PUCK_RADIUS = BALL_RADIUS * 3 / 4;
    public static final String BALL_IMAGE_PATH = "assets/ball.png";
    public static final String BALL_SOUND_PATH = "assets/blop.wav";
    public static final String PUCK_IMAGE_PATH = "assets/mockBall.png";
    public static final String RED_BALL_IMAGE_PATH = "assets/redball.png";
    public static float ANGLE_ADJUSTMENT = 100f;
    // wall constants
    public static final float WALL_THICKNESS = 20;
    public static final Color WALL_COLOR = Color.WHITE;

    // paddle constants
    public static final float PADDLE_HEIGHT = 15;
    public static final float PADDLE_WIDTH = 100;
    public static final int INFINITY_HITS_PADDLE = -1;
    public static final int MAX_HITS_EXTRA_PADDLE = 4;
    public static final float PADDLE_MOVEMENT_SPEED = 300f;
    public static final String PADDLE_IMAGE_PATH = "assets/paddle.png";

    //brick constants
    public static final float BRICK_HEIGHT = 15;
    public static final float BRICK_START_Y = 30;
    public static final float SEPRATOR_BRICKS_ROW = 2;
    public static final int FIXED_ROWS = 7;
    public static final int FIXED_COLS = 8;
    public static final String BRICK_IMAGE_PATH = "assets/brick.png";

    // life constants
    public static final int DEFAULT_LIVES = 3;
    public static final int MAX_LIVES = 4;
    public static final Vector2 HEART_SIZE = new Vector2(30, 30);
    public static final Vector2 HEART_INITIAL_POSITION = new Vector2(40, 460);
    public static final Vector2 NUMERIC_COUNTER_SIZE = new Vector2(50, 20);
    public static final Vector2 NUMERIC_INITIAL_POSITION = new Vector2(20, 460);
    public static final String HEART_IMAGE_PATH = "assets/heart.png";

    // window constants
    public static final Vector2 WINDOW_SIZE = new Vector2(700, 500);
    public static final String BACKGROUND_IMAGE_PATH = "assets/DARK_BG2_small.jpeg";


}
