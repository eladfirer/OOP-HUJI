package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.Layer;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

import static bricker.main.GameConstants.PADDLE_MOVEMENT_SPEED;

/**
 * Represents a paddle in the game.
 * the paddle can move left and right based on user input and collides with other objects, such
 * as balls.
 * it also has a maximum number of hits it can sustain before being destroyed.
 */
public class Paddle extends GameObject {
    private UserInputListener inputListener;
    private Vector2 windowDimensions;
    private int maxHits;
    private BrickerGameManager brickerGameManager;


    /**
     * Constructs a Paddle instance.
     *
     * @param topLeftCorner      - top left location of paddle.
     * @param dimensions         - the width and height of the paddle.
     * @param renderable         - the visual representation of the paddle.
     * @param inputListener      - listens for user input to control the paddle's movement.
     * @param windowDimensions   - the dimensions of the game window, used to constrain paddle
     *                           movement.
     * @param maxHits            - the maximum number of collisions the paddle can sustain before
     *                           being destroyed.
     * @param brickerGameManager - game manager
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                  UserInputListener inputListener, Vector2 windowDimensions, int maxHits,
                  BrickerGameManager brickerGameManager) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
        this.maxHits = maxHits;
        this.brickerGameManager = brickerGameManager;
    }


    /**
     * updates the paddle's position based on user input and ensures it stays within the game
     * window boundaries.
     * called once per frame during the game loop.
     *
     * @param deltaTime - time per update
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movmentDir = Vector2.ZERO;
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            movmentDir = movmentDir.add(Vector2.LEFT);
        }
        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            movmentDir = movmentDir.add(Vector2.RIGHT);
        }
        setVelocity(movmentDir.mult(PADDLE_MOVEMENT_SPEED));

        Vector2 topLeftCorner = getTopLeftCorner();
        float paddleWidth = getDimensions().x();

        if (topLeftCorner.x() < 10) {
            setTopLeftCorner(new Vector2(10, topLeftCorner.y()));
            setVelocity(Vector2.ZERO);
        }


        if (topLeftCorner.x() + paddleWidth > windowDimensions.x() - 10) {
            setTopLeftCorner(new Vector2(windowDimensions.x() - paddleWidth - 10,
                    topLeftCorner.y()));
            setVelocity(Vector2.ZERO);
        }
    }

    /**
     * handles collision events between the paddle and other objects.
     * if the paddle collides with a ball or puck, it reduces the paddle's hit counter.
     * the paddle is destroyed when the hit counter reaches zero.
     *
     * @param other     - the object that the paddle collides with.
     * @param collision - the collision information, such as the collision point and normal.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (brickerGameManager.checkIfBallOrPuck(other)) {
            if (maxHits > 0) {
                maxHits--;
            }
            if (maxHits == 0) {
                brickerGameManager.destroyPaddle(this, Layer.DEFAULT);
            }
        }
    }

}
