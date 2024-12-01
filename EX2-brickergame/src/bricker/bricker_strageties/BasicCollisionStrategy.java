package bricker.bricker_strageties;

import bricker.main.BrickerGameManager;
import danogl.GameObject;

public class BasicCollisionStrategy extends CollisionStrategy {

    public BasicCollisionStrategy(BrickerGameManager brickerGameManager) {
        super(brickerGameManager);
    }

    @Override
    public boolean onCollision(GameObject object1, GameObject object2) {
        return super.onCollision(object1, object2);
    }


}
