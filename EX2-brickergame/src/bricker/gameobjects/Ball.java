package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import static bricker.main.GameConstants.ANGLE_ADJUSTMENT;

/**
 * Ball - this class represents the ball game object
 */
public class Ball extends GameObject {

    private Sound collisionSound;
    protected BrickerGameManager brickerGameManager;
    private int collisionCounter;

    /**
     * Constructs a new instance of Ball.
     *
     * @param topLeftCorner      - top left corner placement of ball
     * @param dimensions         - ball dimensions
     * @param renderable         - ball image
     * @param collisionSound     - ball collision sound
     * @param brickerGameManager - game manager
     */
    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                Sound collisionSound, BrickerGameManager brickerGameManager) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
        this.brickerGameManager = brickerGameManager;
        collisionCounter = 0;
    }

    public int getCollisionCounter() {
        return collisionCounter;
    }

    /**
     * responsible for handling ball movement and collision behaviour
     *
     * @param other     - object that collided with ball
     * @param collision - type of collision
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);

        if (other.shouldCollideWith(this)) {
            Vector2 newVelocity = getVelocity().flipped(collision.getNormal());

            newVelocity = adjustVelocity(newVelocity);

            setVelocity(newVelocity);

            collisionCounter++;
            collisionSound.play();
        }
        // checks if ball is in Turbo Mode
        brickerGameManager.checkRedBall();
    }


    /**
     * adjusts ball movement for stopping it from moving in a straight line.
     *
     * @param velocity - ball current velocity
     * @return new velocity adjusted
     */
    private Vector2 adjustVelocity(Vector2 velocity) {
        float adjustedX = velocity.x() + Math.signum(velocity.x()) * ANGLE_ADJUSTMENT;
        float adjustedY = velocity.y() + Math.signum(velocity.y()) * ANGLE_ADJUSTMENT;

        Vector2 adjustedVelocity =
                new Vector2(adjustedX, adjustedY).normalized().mult(velocity.magnitude());
        return adjustedVelocity;
    }

}
