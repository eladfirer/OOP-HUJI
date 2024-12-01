package bricker.bricker_strageties;

import bricker.gameobjects.Paddle;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.util.Vector2;

import static bricker.main.GameConstants.*;

public class ExtraPaddleCollisionStrategy extends CollisionStrategy {
    public ExtraPaddleCollisionStrategy(BrickerGameManager brickerGameManager) {
        super(brickerGameManager);
    }

    @Override
    public boolean onCollision(GameObject object1, GameObject object2) {
        brickerGameManager.createPaddle(new Vector2(WINDOW_SIZE.x()/2,WINDOW_SIZE.y()/2),MAX_HITS_EXTRA_PADDLE);
        return super.onCollision(object1, object2);
    }
}
