package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import danogl.collisions.Layer;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.Random;

import static bricker.main.GameConstants.BALL_SPEED;
import static bricker.main.GameConstants.PUCK_RADIUS;

public class Puck extends Ball {
    private BrickerGameManager brickerGameManager;

    public Puck(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound, BrickerGameManager brickerGameManager) {
        super(topLeftCorner, dimensions, renderable, collisionSound);
        this.brickerGameManager = brickerGameManager;
        Random rand = new Random();
        double angle = rand.nextFloat() * Math.PI;
        float velocityX = (float)Math.cos(angle)*BALL_SPEED;
        float velocityY = (float)Math.sin(angle)*BALL_SPEED;
        this.setVelocity(new Vector2(velocityX,velocityY));
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 topLeftCorner = getTopLeftCorner();
        if(topLeftCorner.y() + PUCK_RADIUS < 0){
            brickerGameManager.destroyObject(this, Layer.DEFAULT);
        }
    }
}

