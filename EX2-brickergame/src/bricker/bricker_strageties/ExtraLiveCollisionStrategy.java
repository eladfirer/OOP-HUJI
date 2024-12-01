package bricker.bricker_strageties;

import bricker.main.BrickerGameManager;
import danogl.GameObject;

public class ExtraLiveCollisionStrategy extends CollisionStrategy {
    public ExtraLiveCollisionStrategy(BrickerGameManager brickerGameManager) {
        super(brickerGameManager);
    }

    @Override
    public boolean onCollision(GameObject object1, GameObject object2) {
        brickerGameManager.createExtraLive(object1);
        return super.onCollision(object1, object2);
    }
}
