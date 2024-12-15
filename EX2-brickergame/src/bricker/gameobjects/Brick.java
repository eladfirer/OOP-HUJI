package bricker.gameobjects;

import bricker.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * Brick - represents a brick in the game
 */
public class Brick extends GameObject {
    private boolean collided;
    CollisionStrategy collisionStrategy;
    private Counter bricksDown;

    /**
     * Constructs a new instance of Brick.
     *
     * @param topLeftCorner     - placement for brick
     * @param dimensions        - brick dimensions
     * @param renderable        - brick image
     * @param collisionStrategy - brick collision strategy
     * @param bricksDown        - counter for bricks down
     */
    public Brick(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 CollisionStrategy collisionStrategy, Counter bricksDown) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionStrategy = collisionStrategy;
        this.bricksDown = bricksDown;
        this.collided = false;
    }

    /**
     * responsible for handling wall collision behaviour
     *
     * @param other     - object that collided with brick
     * @param collision - type of collision
     */
    public void onCollisionEnter(GameObject other, Collision collision) {
        if (collided == false) {
            super.onCollisionEnter(other, collision);
            collisionStrategy.onCollision(this, other);
            bricksDown.increment();
            collided = true;
        }
    }
}
