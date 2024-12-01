package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * LifeCounter - represent a counter in game
 */
public class LifeCounter extends GameObject {
    protected int maxLives;
    protected BrickerGameManager brickerGameManager;
    protected Vector2 initialPosition;
    protected int currentLives;
    protected boolean exceededMaxLives;

    /**
     * Constructs a new instance of LifeCounter.
     *
     * @param brickerGameManager - game manager
     * @param position           - position for constructor
     * @param maxLives           - max lives counter
     * @param currentLives       - current lives
     */
    public LifeCounter(BrickerGameManager brickerGameManager, Vector2 position, int maxLives,
                       int currentLives) {
        super(position, Vector2.ZERO, null);
        this.brickerGameManager = brickerGameManager;
        this.currentLives = currentLives;
        this.maxLives = maxLives;
        this.initialPosition = position;
        this.exceededMaxLives = false;
    }

    /**
     * remove live from counter
     */
    public void removeLive() {
        currentLives--;
    }

    ;

    /**
     * add live from counter
     */
    public void addLive() {
        if (currentLives < maxLives) {
            currentLives++;
            exceededMaxLives = false;
        }
        else {
            exceededMaxLives = true;
        }
    }

    ;


    public int getCurrentLives() {
        return currentLives;
    }
}

