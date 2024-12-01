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
        Renderable puckImage = brickerGameManager.readImage(PUCK_IMAGE_PATH,true);
        Sound puckSound = brickerGameManager.readSound(BALL_SOUND_PATH);
        makePuckBall(object1,puckImage,puckSound);
        makePuckBall(object1, puckImage,puckSound);
        return super.onCollision(object1, object2);
    }

    private void makePuckBall(GameObject object1, Renderable puckImage, Sound puckSound) {
        Puck puck = new Puck(object1.getTopLeftCorner(),new Vector2(PUCK_RADIUS,PUCK_RADIUS),puckImage,puckSound,brickerGameManager);
        brickerGameManager.addObject(puck, Layer.DEFAULT);
    }
}
