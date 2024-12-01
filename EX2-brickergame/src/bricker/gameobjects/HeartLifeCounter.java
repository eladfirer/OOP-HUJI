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
        super.removeLive();
        brickerGameManager.destroyObject(heartObjects[currentLives], Layer.UI);
    }

    @Override
    public void addLive(){
        super.addLive();
        createHeart(currentLives - 1);
    }

    private void createHeart(int heartNumber){
        if(exceededMaxLives == false){
            Vector2 heartPosition = initialPosition.add(new Vector2(heartNumber * heartSize.x(), 0));
            Heart heart = new Heart(heartPosition, heartSize, heartImage,brickerGameManager);
            heartObjects[heartNumber] = heart;
            brickerGameManager.addObject(heart, Layer.UI);
        }
    }
}

