package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import static bricker.main.GameConstants.HEART_SIZE;
import static bricker.main.GameConstants.PUCK_RADIUS;

/**
 * Heart - this class represents the heart object
 */
public class Heart extends GameObject {

    private BrickerGameManager brickerGameManager;

    /**
     * Constructs a new instance of Heart.
     *
     * @param topLeftCorner      - placement for heart
     * @param dimensions         - heart dimensions
     * @param renderable         - heart image
     * @param brickerGameManager - game manager
     */
    public Heart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 BrickerGameManager brickerGameManager) {
        super(topLeftCorner, dimensions, renderable);
        this.brickerGameManager = brickerGameManager;
    }

    /**
     * checks if a heart is out of window, if yes removes it from game
     * called once per frame during the game loop.
     *
     * @param deltaTime - time per update
     */
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 topLeftCorner = getTopLeftCorner();
        if (topLeftCorner.y() + HEART_SIZE.y() < 0) {
            brickerGameManager.destroyObject(this, Layer.DEFAULT);
        }
    }

    /**
     * responsible for handling heart collision behaviour
     *
     * @param other     - object that collided with heart
     * @param collision - type of collision
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        if (shouldCollideWith(other)) {
            brickerGameManager.activateHeart(this);
        }
    }

    /**
     * determines if this object should collide with the specified object
     *
     * @param other the object to check collision against.
     * @return true if the  object is a paddle, false otherwise.
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return brickerGameManager.checkIfPaddle(other);
    }
}
