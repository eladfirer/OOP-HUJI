package bricker.bricker_strageties;

import bricker.gameobjects.Ball;
import bricker.gameobjects.Puck;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.Random;

import static bricker.main.GameConstants.*;

/**
 * Extra Puck Collision Strategy for brick. has a 1/10 chance of being the brick collision strategy
 *
 * @see CollisionStrategy
 * @see CollisionStrategyFactory
 */
public class PuckCollisionStrategy extends CollisionStrategy {


    /**
     * Constructs a new instance of PuckCollisionStrategy.
     *
     * @param brickerGameManager - game manager
     */
    public PuckCollisionStrategy(BrickerGameManager brickerGameManager) {
        super(brickerGameManager);
    }

    /**
     * handles collision of brick and ball/puck outcome
     *
     * @param object1 - brick
     * @param object2 - ball/puck
     */
    @Override
    public void onCollision(GameObject object1, GameObject object2) {
        brickerGameManager.addPuckBalls(object1);
        super.onCollision(object1, object2);
    }
}
