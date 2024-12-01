package bricker.bricker_strageties;

import bricker.main.BrickerGameManager;
import danogl.GameObject;


/**
 * Turbo Mode Collision Strategy for brick. has a 1/10 chance of being the brick collision strategy
 *
 * @see CollisionStrategy
 * @see CollisionStrategyFactory
 */
public class TurboModeCollisionStrategy extends CollisionStrategy {

    /**
     * Constructs a new instance of TurboModeCollisionStrategy.
     *
     * @param brickerGameManager - game manager
     */
    public TurboModeCollisionStrategy(BrickerGameManager brickerGameManager) {
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
        brickerGameManager.turboMode(object2);
        super.onCollision(object1, object2);
    }
}
