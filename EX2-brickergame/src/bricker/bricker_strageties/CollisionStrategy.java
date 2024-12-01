package bricker.bricker_strageties;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import static bricker.main.GameConstants.*;

public class CollisionStrategy {

    protected BrickerGameManager brickerGameManager;

    public CollisionStrategy(BrickerGameManager brickerGameManager) {
        this.brickerGameManager = brickerGameManager;
    }

    public boolean onCollision(GameObject object1, GameObject object2){
        return brickerGameManager.destroyObject(object1, Layer.STATIC_OBJECTS);
    }
}
