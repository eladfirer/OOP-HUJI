package bricker.main;

import bricker.bricker_strageties.BasicCollisionStrategy;
import bricker.bricker_strageties.PuckCollisionStrategy;
import bricker.gameobjects.*;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import danogl.util.Counter;

import java.awt.*;
import java.util.Random;
import java.awt.event.KeyEvent;

import static bricker.main.GameConstants.*;


public class BrickerGameManager extends GameManager {

    private int numRows = 7;
    private int numBricksInRow = 8;
    private int livesRemaining = DEFAULT_LIVES;
    private Counter bricksCounter = new Counter();

    private GameObject ball;

    private Vector2 windowDimensions;
    private WindowController windowController;

    private HeartLifeCounter heartLifeCounter;
    private NumericLifeCounter numericLifeCounter;

    private ImageReader imageReader;
    private UserInputListener inputListener;
    private SoundReader soundReader;


    public BrickerGameManager(String windowTitle, Vector2 windowDimensions, int numRows, int numBricksInRow) {
        super(windowTitle, windowDimensions);
        this.numRows = numRows;
        this.numBricksInRow = numBricksInRow;
    }

    @Override
    public void initializeGame(ImageReader imageReader,
                               SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.windowController = windowController;
        this.windowDimensions = windowController.getWindowDimensions();
        this.imageReader = imageReader;
        this.inputListener = inputListener;
        this.soundReader = soundReader;
        //creating hearts
        createCounters();
        // creating ball
        createBall();

        // creating user paddle
        createPaddle();

        // create bricks
        createBricks();

        //creating walls
        createWalls();

        //creating background
        createBackground();
    }

    private void createCounters(){
        Renderable heartImage = imageReader.readImage(HEART_IMAGE_PATH, true);
        this.heartLifeCounter = new HeartLifeCounter(this,
                HEART_INITIAL_POSITION,
                MAX_LIVES,
                DEFAULT_LIVES,
                heartImage,
                HEART_SIZE
        );
        gameObjects().addGameObject(heartLifeCounter);
        this.numericLifeCounter = new NumericLifeCounter(
                this,
                NUMERIC_INITIAL_POSITION,
                MAX_LIVES,
                DEFAULT_LIVES,
                NUMERIC_COUNTER_SIZE
        );
        gameObjects().addGameObject(numericLifeCounter);

    }

    private void createPaddle() {
        Renderable paddleImage = imageReader.readImage(PADDLE_IMAGE_PATH, true);
        Paddle paddle = new Paddle(Vector2.ZERO, new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT), paddleImage, inputListener, windowDimensions);
        paddle.setCenter(new Vector2(windowDimensions.x() / 2, windowDimensions.y() - 30));
        gameObjects().addGameObject(paddle);
    }


    private void createBricks() {
        Renderable brickImage = imageReader.readImage(BRICK_IMAGE_PATH, true);
        float availableWidth = windowDimensions.x() - 2 * WALL_THICKNESS;
        float brickWidth = availableWidth / numBricksInRow;

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numBricksInRow; col++) {
                float x = WALL_THICKNESS + col * brickWidth;
                float y = BRICK_START_Y + row * BRICK_HEIGHT;
                Brick brick = new Brick(new Vector2(x, y), new Vector2(brickWidth - 2, BRICK_HEIGHT),
                        brickImage, new PuckCollisionStrategy(this),this.bricksCounter);
                gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
            }
        }
    }

    private void createBall() {
        Renderable ballImage = imageReader.readImage(BALL_IMAGE_PATH, true);
        Sound collisionSound = soundReader.readSound(BALL_SOUND_PATH);
        this.ball = new Ball(Vector2.ZERO, new Vector2(BALL_RADIUS, BALL_RADIUS), ballImage, collisionSound);
        resetBall();
        gameObjects().addGameObject(ball);
    }

    private void resetBall() {
        Random rand = new Random();
        float ballVelX = BALL_SPEED * (rand.nextBoolean() ? 1 : -1);
        float ballVelY = BALL_SPEED * (rand.nextBoolean() ? 1 : -1);
        ball.setVelocity(new Vector2(ballVelX, ballVelY));
        ball.setCenter(windowDimensions.mult(0.5f));
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        checkForGameEnd();
    }

    private void checkForGameEnd(){
        float ballHeight = ball.getCenter().y();
        String prompt = "";
        if(bricksCounter.value() == numBricksInRow*numRows || inputListener.isKeyPressed(KeyEvent.VK_W)){
            prompt = "You win!";
        }
        if(ballHeight > windowDimensions.y()) {
            livesRemaining--;
            heartLifeCounter.removeLive();
            numericLifeCounter.removeLive();
            if (livesRemaining > 0) {
                resetBall();
            }
            else{
            prompt = "You Lose!";
            }
        }

        if(prompt.isEmpty() == false){
            prompt += " Play again?";
            if(windowController.openYesNoDialog(prompt)){
                livesRemaining = DEFAULT_LIVES;
                bricksCounter = new Counter();
                windowController.resetGame();
            }
            else {
                windowController.closeWindow();
            }
        }
    }

    private void createWalls(){
        createWall(
                new Vector2(0, 0),
                new Vector2(WALL_THICKNESS, windowDimensions.y())
        );
        createWall(
                new Vector2(0, 0),
                new Vector2(windowDimensions.x(), WALL_THICKNESS)
        );
        createWall(
                new Vector2(windowDimensions.x() - WALL_THICKNESS, 0),
                new Vector2(WALL_THICKNESS, windowDimensions.y())
        );
    }

    private void createWall(Vector2 position, Vector2 size) {
        Renderable wallColor = new RectangleRenderable(WALL_COLOR);
        GameObject wall = new GameObject(position, size, wallColor);
        gameObjects().addGameObject(wall);
    }

    private void createBackground(){
        Renderable backgroundImage = imageReader.readImage(BACKGROUND_IMAGE_PATH, false);
        GameObject background = new GameObject(Vector2.ZERO, windowDimensions, backgroundImage);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(background, Layer.BACKGROUND);
    }

    public boolean destroyObject(GameObject object, int layer) {
        return gameObjects().removeGameObject(object, layer);
    }

    public void addObject(GameObject object, int layer) {
        gameObjects().addGameObject(object, layer);
    }

    public Renderable readImage(String imagePath, boolean isTopPixelTransparency) {
        return imageReader.readImage(imagePath, isTopPixelTransparency);
    }

    public Sound readSound(String soundPath) {
        return soundReader.readSound(soundPath);
    }

    public static void main(String[] args) {
        int numRows = 7;
        int numBricksInRow = 8;

        if (args.length == 2) {
            numBricksInRow = Integer.parseInt(args[0]);
            numRows = Integer.parseInt(args[1]);
        }

        BrickerGameManager game = new BrickerGameManager("Bricker",
                WINDOW_SIZE,
                numRows,
                numBricksInRow);
        game.run();
    }
}
