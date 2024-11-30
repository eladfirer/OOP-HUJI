package bricker.bricker_strageties;

import danogl.GameObject;

public interface CollisionStrategy {
    public void onCollision(GameObject object1, GameObject object2);
}
