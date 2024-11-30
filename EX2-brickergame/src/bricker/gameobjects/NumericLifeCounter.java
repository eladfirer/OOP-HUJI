package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

import java.awt.*;

public class NumericLifeCounter extends LifeCounter {

    private Vector2 counterSize;
    private GameObject textObject;

    public NumericLifeCounter(BrickerGameManager brickerGameManager, Vector2 position, int maxLives, int currentLives,Vector2 counterSize) {
        super(brickerGameManager, position, maxLives, currentLives);
        this.counterSize = counterSize;
        createTextObject();
    }

    private void createTextObject() {
        TextRenderable textRenderable = new TextRenderable(String.valueOf(currentLives));
        updateColor(textRenderable);
        this.textObject = new GameObject(initialPosition, counterSize, textRenderable);
        this.brickerGameManager.addObject(textObject, Layer.UI);
    }

    @Override
    public void addLive() {
        currentLives += 1;
        brickerGameManager.destroyObject(textObject, Layer.UI);
        createTextObject();
    }

    @Override
    public void removeLive() {
        currentLives -= 1;
        brickerGameManager.destroyObject(textObject, Layer.UI);
        createTextObject();
    }

    private void updateColor(TextRenderable textRenderable) {
        if (currentLives >= 3) {
            textRenderable.setColor(Color.GREEN);
        } else if (currentLives == 2) {
            textRenderable.setColor(Color.YELLOW);
        } else if (currentLives == 1) {
            textRenderable.setColor(Color.RED);
        }
    }


}
