package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import danogl.collisions.Layer;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.Random;

import static bricker.main.GameConstants.BALL_SPEED;
import static bricker.main.GameConstants.PUCK_RADIUS;

/**
 * this is a sub-class of Ball, representing a puck ball in game that can appear during ball and
 * bricks interaction.
 * @see Ball
 */
public class Puck extends Ball {

    /**
     * Constructs a new instance of Puck.
     * @param topLeftCorner - top left corner placement of puck
     * @param dimensions - puck dimensions
     * @param renderable - puck image
     * @param collisionSound - puck collision sound
     * @param brickerGameManager - game manager
     */
    public Puck(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                Sound collisionSound, BrickerGameManager brickerGameManager) {
        super(topLeftCorner, dimensions, renderable, collisionSound, brickerGameManager);
        Random rand = new Random();
        double angle = rand.nextFloat() * Math.PI;
        float velocityX = (float) Math.cos(angle) * BALL_SPEED;
        float velocityY = (float) Math.sin(angle) * BALL_SPEED;
        this.setVelocity(new Vector2(velocityX, velocityY));
    }

    /**
     * checks if a puck is out of window, if yes removes it from game
     * called once per frame during the game loop.
     *
     * @param deltaTime - time per update
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 topLeftCorner = getTopLeftCorner();
        if (topLeftCorner.y() + PUCK_RADIUS < 0) {
            brickerGameManager.destroyObject(this, Layer.DEFAULT);
        }
    }
}

