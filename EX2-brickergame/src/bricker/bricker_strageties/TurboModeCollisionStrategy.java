package bricker.bricker_strageties;

import bricker.main.BrickerGameManager;
import danogl.GameObject;

public class TurboModeCollisionStrategy extends CollisionStrategy {
    public TurboModeCollisionStrategy(BrickerGameManager brickerGameManager) {
        super(brickerGameManager);
    }

    @Override
    public boolean onCollision(GameObject object1, GameObject object2) {
        brickerGameManager.turboMode(object2);
        return super.onCollision(object1, object2);
    }
}
