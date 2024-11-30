package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class HeartLifeCounter extends LifeCounter {
    private Vector2 heartSize;
    private Renderable heartImage;
    private GameObject[] heartObjects;

    public HeartLifeCounter(BrickerGameManager brickerGameManager, Vector2 position, int maxLives, int currentLives, Renderable heartImage, Vector2 heartSize) {
        super(brickerGameManager, position, maxLives, currentLives);
        this.heartImage = heartImage;
        this.heartSize = heartSize;
        this.heartObjects = new Heart[maxLives];
        createHearts();
    }

    private void createHearts() {
        for (int i = 0; i < currentLives; i++) {
            createHeart(i);
        }
    }
    @Override
    public void removeLive(){
        brickerGameManager.destroyObject(heartObjects[currentLives-1], Layer.UI);
        currentLives--;
    }

    @Override
    public void addLive(){
        createHeart(currentLives - 1);
        currentLives++;
    }

    private void createHeart(int heartNumber){
        Vector2 heartPosition = initialPosition.add(new Vector2(heartNumber * heartSize.x(), 0));
        Heart heart = new Heart(heartPosition, heartSize, heartImage);
        heartObjects[heartNumber] = heart;
        brickerGameManager.addObject(heart, Layer.UI);
    }
}

