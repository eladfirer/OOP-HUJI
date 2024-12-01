package bricker.bricker_strageties;

import bricker.gameobjects.Ball;
import bricker.gameobjects.Puck;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.Random;

import static bricker.main.GameConstants.*;

public class PuckCollisionStrategy extends CollisionStrategy {

    public PuckCollisionStrategy(BrickerGameManager brickerGameManager) {
        super(brickerGameManager);
    }
    @Override
    public boolean onCollision(GameObject object1, GameObject object2) {
        brickerGameManager.addPuckBalls(object1);
        return super.onCollision(object1, object2);
    }
}
