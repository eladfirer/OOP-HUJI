package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public abstract class LifeCounter extends GameObject {
    protected int maxLives;
    protected BrickerGameManager brickerGameManager;
    protected Vector2 initialPosition;
    protected int currentLives;

    public LifeCounter(BrickerGameManager brickerGameManager, Vector2 position, int maxLives,int currentLives) {
        super(position, Vector2.ZERO, null);
        this.brickerGameManager = brickerGameManager;
        this.currentLives = currentLives;
        this.maxLives = maxLives;
        this.initialPosition = position;
    }

    public void removeLive(){};
    public void addLive(){};
}

