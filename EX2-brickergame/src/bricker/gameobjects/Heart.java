package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import static bricker.main.GameConstants.HEART_SIZE;
import static bricker.main.GameConstants.PUCK_RADIUS;

public class Heart extends GameObject {
    private BrickerGameManager brickerGameManager;

    public Heart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, BrickerGameManager brickerGameManager) {
        super(topLeftCorner, dimensions, renderable);
        this.brickerGameManager = brickerGameManager;
    }

    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 topLeftCorner = getTopLeftCorner();
        if(topLeftCorner.y() + HEART_SIZE.y() < 0){
            brickerGameManager.destroyObject(this, Layer.DEFAULT);
        }
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        if(shouldCollideWith(other)){
            brickerGameManager.activateHeart(other,this);
        }
    }

    @Override
    public boolean shouldCollideWith(GameObject other) {
        return brickerGameManager.checkIfPaddle(other);
    }
}
