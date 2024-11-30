package bricker.bricker_strageties;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Layer;

public class BasicCollisionStrategy implements CollisionStrategy {
    private BrickerGameManager brickerGameManager;

    public BasicCollisionStrategy(BrickerGameManager brickerGameManager) {
        this.brickerGameManager = brickerGameManager;
    }

    @Override
    public void onCollision(GameObject object1, GameObject object2) {
        System.out.println("collision with brick detected");
        brickerGameManager.destroyObject(object1, Layer.STATIC_OBJECTS);
    }


}
