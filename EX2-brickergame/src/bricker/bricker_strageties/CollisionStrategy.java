package bricker.bricker_strageties;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Layer;

import static bricker.main.GameConstants.*;


/**
 * Collision Strategy for brick. father class of all collision strategies.
 *
 * @see CollisionStrategyFactory
 */
public class CollisionStrategy {

    protected BrickerGameManager brickerGameManager;


    /**
     * Constructs a new instance of CollisionStrategy.
     *
     * @param brickerGameManager - game manager
     */
    public CollisionStrategy(BrickerGameManager brickerGameManager) {
        this.brickerGameManager = brickerGameManager;
    }

    /**
     * handles collision of brick and ball/puck outcome
     *
     * @param object1 - brick
     * @param object2 - ball/puck
     */
    public void onCollision(GameObject object1, GameObject object2) {
        brickerGameManager.destroyObject(object1, Layer.STATIC_OBJECTS);
    }
}
