package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.function.BiConsumer;

/**
 * this class represents a game object of fruit in game
 */
public class Fruit extends GameObject {
    private static BiConsumer<Fruit, GameObject> collisionFruitHandler;

    /**
     * Fruit Constructor
     *
     * @param topLeftCorner top left corner position for object
     * @param dimensions    object dimensions
     * @param renderable    how to render object
     */
    public Fruit(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
        super(topLeftCorner, dimensions, renderable);
    }

    /**
     * sets a collision fruit handler for object to call in collision
     *
     * @param collisionFruitHandler callback to call
     */
    public static void setFruitCollisionHandler(BiConsumer<Fruit, GameObject> collisionFruitHandler) {
        Fruit.collisionFruitHandler = collisionFruitHandler;
    }

    /**
     * calls the callback in case of collision enter
     *
     * @param other     The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (collisionFruitHandler != null) {
            collisionFruitHandler.accept(this, other);
        }
    }

}
