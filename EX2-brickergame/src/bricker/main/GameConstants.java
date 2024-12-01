package bricker.main;

import danogl.util.Vector2;

import java.awt.*;

public final class GameConstants {

    private GameConstants() {};

    public static final float BALL_SPEED = 200;
    public static final float WALL_THICKNESS = 5;
    public static final float BALL_RADIUS = 50;
    public static final float PUCK_RADIUS = BALL_RADIUS * 3/4;
    public static final float BALL_SIZE = BALL_SPEED;
    public static final float PADDLE_HEIGHT = 15;
    public static final float PADDLE_WIDTH = 100;
    public static final float BRICK_HEIGHT = 15;
    public static final float BRICK_START_Y = 30;
    public static final int DEFAULT_LIVES = 3;
    public static final int MAX_LIVES = 4;
    public static final Color WALL_COLOR = Color.WHITE;
    public static final Vector2 WINDOW_SIZE = new Vector2(700, 500);
    public static final Vector2 HEART_SIZE = new Vector2(30, 30);
    public static final Vector2 HEART_INITIAL_POSITION = new Vector2(40, 460);
    public static final Vector2 NUMERIC_COUNTER_SIZE = new Vector2(50, 20);
    public static final Vector2 NUMERIC_INITIAL_POSITION = new Vector2(20, 460);

    public static final String BALL_IMAGE_PATH = "assets/ball.png";
    public static final String PUCK_IMAGE_PATH = "assets/mockBall.png";
    public static final String BALL_SOUND_PATH = "assets/blop.wav";
    public static final String BRICK_IMAGE_PATH = "assets/brick.png";
    public static final String PADDLE_IMAGE_PATH = "assets/paddle.png";
    public static final String HEART_IMAGE_PATH = "assets/heart.png";
    public static final String BACKGROUND_IMAGE_PATH = "assets/DARK_BG2_small.jpeg";

}
