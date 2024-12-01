package bricker.bricker_strageties;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.util.Counter;


/**
 * Double Behaviors Collision Strategy for brick. has a 1/10 chance of being the brick collision
 * strategy
 *
 * @see CollisionStrategy
 * @see CollisionStrategyFactory
 */
public class DoubleBehaviorsCollisionStrategy extends CollisionStrategy {
    private CollisionStrategy strategyTwo;
    private CollisionStrategy strategyOne;

    /**
     * Constructs a new instance of DoubleBehaviorsCollisionStrategy.
     *
     * @param brickerGameManager - game manager
     */
    public DoubleBehaviorsCollisionStrategy(BrickerGameManager brickerGameManager,
                                            Counter counter) {
        super(brickerGameManager);
        counter.increment();
        this.strategyOne = CollisionStrategyFactory.createStrategy(brickerGameManager, counter);
        this.strategyTwo = CollisionStrategyFactory.createStrategy(brickerGameManager, counter);
    }

    /**
     * handles collision of brick and ball/puck outcome
     *
     * @param object1 - brick
     * @param object2 - ball/puck
     */
    @Override
    public void onCollision(GameObject object1, GameObject object2) {
        strategyOne.onCollision(object1, object2);
        strategyTwo.onCollision(object1, object2);
        super.onCollision(object1, object2);
    }
}
