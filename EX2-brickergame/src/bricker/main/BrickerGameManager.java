package bricker.main;

import bricker.brick_strategies.CollisionStrategyFactory;
import bricker.brick_strategies.ExtraLiveCollisionStrategy;
import bricker.brick_strategies.PuckCollisionStrategy;
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

import java.util.Random;
import java.awt.event.KeyEvent;

import static bricker.main.GameConstants.*;

/**
 * BrickerGameManager - responsible for running and managing the game.
 */
public class BrickerGameManager extends GameManager {

    // fields regarding bricks
    private int numRows;
    private int numCols;
    private Counter bricksCounter = new Counter();

    private int livesRemaining = DEFAULT_LIVES;

    private Ball ball;
    private Paddle paddle;

    private Vector2 windowDimensions;
    private WindowController windowController;
    private ImageReader imageReader;
    private UserInputListener inputListener;
    private SoundReader soundReader;

    private LifeCounter heartLifeCounter;
    private LifeCounter numericLifeCounter;

    // fields regarding special abilities
    private int numPaddles;
    private boolean ballTurboMode;
    private int ballCollisionCounter;


    /**
     * Constructs a new instance of BrickerGameManager.
     *
     * @param windowTitle      - name of game
     * @param windowDimensions - window game dimensions
     * @param numRows          - number of bricks in row
     * @param numBricksInRow   - number of brick in col
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions, int numRows,
                              int numBricksInRow) {
        super(windowTitle, windowDimensions);
        this.numRows = numRows;
        this.numCols = numBricksInRow;
        this.numPaddles = 0;
        this.ballTurboMode = false;
        this.ballCollisionCounter = 0;
    }

    /**
     * this function is responsible for creating game elements and starting game
     *
     * @param imageReader      - image reader for assets
     * @param soundReader      - sound reader for assets
     * @param inputListener    - input reader for keyboard
     * @param windowController - window controller
     */
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
        createPaddle(new Vector2(windowDimensions.x() / 2, windowDimensions.y() - 30),
                INFINITY_HITS_PADDLE);

        // create bricks
        createBricks();

        //creating walls
        createWalls();

        //creating background
        createBackground();
    }

    /**
     * this function is responsible for creating counters for game
     *
     * @see LifeCounter
     * @see HeartLifeCounter
     * @see NumericLifeCounter
     */
    private void createCounters() {
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

    /**
     * this function is for creating a paddle, there are maximum two paddles in game.
     *
     * @param center  - placement for paddle
     * @param maxHits - field for paddle with max hit life
     * @see Paddle
     */
    public void createPaddle(Vector2 center, int maxHits) {
        if (numPaddles < 2) {
            Renderable paddleImage = imageReader.readImage(PADDLE_IMAGE_PATH, true);
            Paddle paddle = new Paddle(Vector2.ZERO, new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT),
                    paddleImage, inputListener, windowDimensions, maxHits, this);
            paddle.setCenter(center);
            gameObjects().addGameObject(paddle);
            if (numPaddles == 0) {
                this.paddle = paddle;
            }
            numPaddles++;
        }
    }

    /**
     * this function is responsible for creating bricks for game
     *
     * @see Brick
     */
    private void createBricks() {
        Renderable brickImage = imageReader.readImage(BRICK_IMAGE_PATH, true);
        float availableWidth = windowDimensions.x() - 2 * WALL_THICKNESS;
        float brickWidth = availableWidth / numCols;

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                float x = WALL_THICKNESS + col * brickWidth;
                float y = BRICK_START_Y + row * BRICK_HEIGHT + SEPRATOR_BRICKS_ROW * row;
                Brick brick = new Brick(new Vector2(x, y), new Vector2(brickWidth - 2,
                        BRICK_HEIGHT),
                        brickImage, CollisionStrategyFactory.createStrategy(this,
                        new Counter()),
                        this.bricksCounter);
                gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
            }
        }
    }

    /**
     * this function is responsible for creating the main ball in game
     *
     * @see Ball
     */
    private void createBall() {
        Renderable ballImage = imageReader.readImage(BALL_IMAGE_PATH, true);
        Sound collisionSound = soundReader.readSound(BALL_SOUND_PATH);
        this.ball = new Ball(Vector2.ZERO, new Vector2(BALL_RADIUS, BALL_RADIUS), ballImage,
                collisionSound, this);
        resetBall();
        gameObjects().addGameObject(ball);
    }

    private void resetBall(){
        Random rand = new Random();
        float ballVelX = BALL_SPEED * (rand.nextBoolean() ? 1 : -1);
        float ballVelY = BALL_SPEED * (rand.nextBoolean() ? 1 : -1);
        ball.setVelocity(new Vector2(ballVelX, ballVelY));
        ball.setCenter(windowDimensions.mult(0.5f));
    }


    /**
     * responsible for updating all objects in game
     * called once per frame during the game loop.
     *
     * @param deltaTime - time for running function
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        checkForGameEnd();
    }

    /**
     * this function checks if the game ended and updates the user.
     */
    private void checkForGameEnd() {
        float ballHeight = ball.getCenter().y();
        String prompt = "";
        if (bricksCounter.value() == numCols * numRows || inputListener.isKeyPressed(KeyEvent.VK_W)) {
            prompt = "You win!";
        }
        else if (ballHeight > windowDimensions.y()) {
            heartLifeCounter.removeLive();
            numericLifeCounter.removeLive();
            livesRemaining = numericLifeCounter.getCurrentLives();
            if (livesRemaining > 0) {
                resetBall();
            }
            else {
                prompt = "You Lose!";
            }
        }

        if (prompt.isEmpty() == false) {
            prompt += " Play again?";
            if (windowController.openYesNoDialog(prompt)) {
                resetGame();
            }
            else {
                windowController.closeWindow();
            }
        }
    }

    /**
     * this function responsible for running a new game if requested.
     */
    private void resetGame() {
        numPaddles = 0;
        ballTurboMode = false;
        ballCollisionCounter = 0;
        livesRemaining = DEFAULT_LIVES;
        bricksCounter = new Counter();
        windowController.resetGame();
    }

    /**
     * this function is responsible for creating the game walls
     */
    private void createWalls() {
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

    /**
     * this function is responsible for creating a single wall
     *
     * @param position - position of wall
     * @param size     - size of wall
     */
    private void createWall(Vector2 position, Vector2 size) {
        Renderable wallColor = new RectangleRenderable(WALL_COLOR);
        GameObject wall = new GameObject(position, size, wallColor);
        gameObjects().addGameObject(wall);
    }

    /**
     * this function is responsible for creating game background
     */
    private void createBackground() {
        Renderable backgroundImage = imageReader.readImage(BACKGROUND_IMAGE_PATH, false);
        GameObject background = new GameObject(Vector2.ZERO, windowDimensions, backgroundImage);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(background, Layer.BACKGROUND);
    }

    /**
     * this function can be called during an event where an object needs to be removed.
     *
     * @param object - object to be removed
     * @param layer  - layer of object
     * @return true in case the object was really removed from the game. false otherwise
     * @see GameObject
     * @see danogl.collisions.GameObjectCollection
     */
    public boolean destroyObject(GameObject object, int layer) {
        return gameObjects().removeGameObject(object, layer);
    }

    /**
     * this function is responsible for removing extra paddle from game
     *
     * @param paddle - paddle to be removed
     * @param layer  - layer of paddle
     * @see Paddle
     * @see bricker.brick_strategies.ExtraPaddleCollisionStrategy
     */
    public void destroyPaddle(Paddle paddle, int layer) {
        numPaddles--;
        destroyObject(paddle, layer);
    }

    /**
     * this function is responsible for adding an object to game
     *
     * @param object - object to be added
     * @param layer  - layer to add object
     * @see GameObject
     * @see danogl.collisions.GameObjectCollection
     */
    public void addObject(GameObject object, int layer) {
        gameObjects().addGameObject(object, layer);
    }

    /**
     * add puck balls to game
     *
     * @param brick - brick with PuckCollisionStrategy
     * @see PuckCollisionStrategy
     * @see Brick
     */
    public void addPuckBalls(GameObject brick) {
        Renderable puckImage = imageReader.readImage(PUCK_IMAGE_PATH, true);
        Sound puckSound = soundReader.readSound(BALL_SOUND_PATH);
        for (int i = 0; i < NUM_PUCKS_CREATE_COLLISION; i++) {
            Puck puck = new Puck(brick.getCenter(),
                    new Vector2(PUCK_RADIUS, PUCK_RADIUS),
                    puckImage, puckSound,
                    this);
            addObject(puck, Layer.DEFAULT);
        }
    }

    /**
     * this fucntion responsible for activating TurboModeCollisionStrategy
     *
     * @param object - object that collided with brick
     * @see bricker.brick_strategies.TurboModeCollisionStrategy
     * @see Ball
     */
    public void turboMode(GameObject object) {
        if (object == ball && ballTurboMode == false) {
            ballTurboMode = true;
            Vector2 currentVelocity = ball.getVelocity();
            Vector2 newVelocity = currentVelocity.mult(TURBE_MODE_SPEED_MULT);
            ball.setVelocity(newVelocity);
            Renderable redBallImage = imageReader.readImage(RED_BALL_IMAGE_PATH, true);
            ball.renderer().setRenderable(redBallImage);
            ballCollisionCounter = ball.getCollisionCounter();
        }
    }

    /**
     * disables ball from TurboMode
     */
    private void disableTurboMode() {
        ballTurboMode = false;
        Vector2 currentVelocity = ball.getVelocity();
        Vector2 newVelocity = currentVelocity.mult(1 / TURBE_MODE_SPEED_MULT);
        ball.setVelocity(newVelocity);
        Renderable ballImage = imageReader.readImage(BALL_IMAGE_PATH, true);
        ball.renderer().setRenderable(ballImage);
    }

    /**
     * checks whether the ball needs to deactivate Turbo Mode.
     */
    public void checkRedBall() {
        if (ballTurboMode && ball.getCollisionCounter() >= ballCollisionCounter + HITS_RED_BALL) {
            disableTurboMode();
        }
    }

    /**
     * creates an extra live falling from brick who has been activated
     *
     * @param brick - brick that was activated
     * @see ExtraLiveCollisionStrategy
     * @see Brick
     * @see Heart
     */
    public void createExtraLive(GameObject brick) {
        Renderable heartImage = imageReader.readImage(HEART_IMAGE_PATH, true);
        Heart heart = new Heart(brick.getCenter(), HEART_SIZE, heartImage, this);
        heart.setVelocity(Vector2.DOWN.mult(100));
        addObject(heart, Layer.DEFAULT);
    }

    /**
     * checks if the object is the main paddle of game
     *
     * @param object - object to be checked
     * @return true in case of main paddle, false otherwise
     */
    public boolean checkIfPaddle(GameObject object) {
        return object == paddle;
    }

    /**
     * activate a heart the player collected
     *
     * @param object - object to be removed from game after updating counters.
     * @see LifeCounter
     * @see Heart
     * @see NumericLifeCounter
     * @see HeartLifeCounter
     */
    public void activateHeart(GameObject object) {
        numericLifeCounter.addLive();
        heartLifeCounter.addLive();
        livesRemaining = numericLifeCounter.getCurrentLives();
        destroyObject(object, Layer.DEFAULT);
    }

    /**
     * checks if the object is a Ball or a Puck
     *
     * @param object - object to be checked
     * @return true in case of being a Ball or Puck. false otherwise
     * @see Ball
     * @see Puck
     * @see Paddle
     */
    public boolean checkIfBallOrPuck(GameObject object) {
        return object instanceof Ball;
    }


    public static void main(String[] args) {
        int numRows = FIXED_ROWS;
        int numCols = FIXED_COLS;

        if (args.length == 2) {
            numCols = Integer.parseInt(args[0]);
            numRows = Integer.parseInt(args[1]);
        }

        BrickerGameManager game = new BrickerGameManager("Bricker",
                WINDOW_SIZE,
                numRows,
                numCols);
        game.run();
    }
}
