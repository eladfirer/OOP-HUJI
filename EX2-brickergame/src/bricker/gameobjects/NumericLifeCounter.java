package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 * NumericLifeCounter - this class represents heart life counter
 */
public class NumericLifeCounter extends LifeCounter {

    private Vector2 counterSize;
    private GameObject textObject;
    private TextRenderable textRenderable;

    /**
     * Constructs a new instance of NumericLifeCounter.
     *
     * @param brickerGameManager - game manager
     * @param position           - position for constructor (not real position)
     * @param maxLives           - max lives counter
     * @param currentLives       - current lives
     * @param counterSize        - counter size
     */
    public NumericLifeCounter(BrickerGameManager brickerGameManager, Vector2 position,
                              int maxLives, int currentLives, Vector2 counterSize) {
        super(brickerGameManager, position, maxLives, currentLives);
        this.counterSize = counterSize;
        this.textRenderable = new TextRenderable(String.valueOf(currentLives));
        updateColor(textRenderable);
        this.textObject = new GameObject(initialPosition, counterSize, textRenderable);
        this.brickerGameManager.addObject(textObject, Layer.UI);
    }

    /**
     * add live from counter
     */
    @Override
    public void addLive() {
        super.addLive();
        textRenderable.setString(String.valueOf(currentLives));
        updateColor(textRenderable);
        textObject.renderer().setRenderable(textRenderable);
    }

    /**
     * remove live from counter
     */
    @Override
    public void removeLive() {
        super.removeLive();
        textRenderable.setString(String.valueOf(currentLives));
        updateColor(textRenderable);
        textObject.renderer().setRenderable(textRenderable);
    }

    /**
     * this methids updates counter color
     *
     * @param textRenderable - text on counter
     */
    private void updateColor(TextRenderable textRenderable) {
        if (currentLives >= 3) {
            textRenderable.setColor(Color.GREEN);
        }
        else if (currentLives == 2) {
            textRenderable.setColor(Color.YELLOW);
        }
        else if (currentLives == 1) {
            textRenderable.setColor(Color.RED);
        }
    }
}
