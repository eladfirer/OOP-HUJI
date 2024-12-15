package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.util.Vector2;

import static bricker.main.GameConstants.*;


/**
 * Extra Paddle Collision Strategy for brick. has a 1/10 chance of being the brick collision
 * strategy
 *
 * @see CollisionStrategy
 * @see CollisionStrategyFactory
 */
public class ExtraPaddleCollisionStrategy extends CollisionStrategy {

    /**
     * Constructs a new instance of ExtraPaddleCollisionStrategy.
     *
     * @param brickerGameManager - game manager
     */
    public ExtraPaddleCollisionStrategy(BrickerGameManager brickerGameManager) {
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
        brickerGameManager.createPaddle(new Vector2(WINDOW_SIZE.x() / 2, WINDOW_SIZE.y() / 2),
                MAX_HITS_EXTRA_PADDLE);
        super.onCollision(object1, object2);
    }
}
