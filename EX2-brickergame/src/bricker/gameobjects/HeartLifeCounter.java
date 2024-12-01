package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * HeartLifeCounter - this class represents heart life counter
 *
 * @see LifeCounter
 */
public class HeartLifeCounter extends LifeCounter {
    private Vector2 heartSize;
    private Renderable heartImage;
    private GameObject[] heartObjects;

    /**
     * Constructs a new instance of HeartLifeCounter.
     *
     * @param brickerGameManager - game manager
     * @param position           - position for constructor (not real position)
     * @param maxLives           - max lives counter
     * @param currentLives       - current lives
     * @param heartImage         - heart image for counter
     * @param heartSize          - heart size for counter
     * @see Heart
     */
    public HeartLifeCounter(BrickerGameManager brickerGameManager, Vector2 position, int maxLives
            , int currentLives, Renderable heartImage, Vector2 heartSize) {
        super(brickerGameManager, position, maxLives, currentLives);
        this.heartImage = heartImage;
        this.heartSize = heartSize;
        this.heartObjects = new Heart[maxLives];
        createHearts();
    }

    /**
     * create hearts for counter
     */
    private void createHearts() {
        for (int i = 0; i < currentLives; i++) {
            createHeart(i);
        }
    }

    /**
     * remove live from counter
     */
    @Override
    public void removeLive() {
        super.removeLive();
        brickerGameManager.destroyObject(heartObjects[currentLives], Layer.UI);
    }

    /**
     * add live from counter
     */
    @Override
    public void addLive() {
        super.addLive();
        createHeart(currentLives - 1);
    }

    /**
     * create live in counter
     *
     * @param heartNumber - heart number in counter
     * @see Heart
     */
    private void createHeart(int heartNumber) {
        if (exceededMaxLives == false) {
            Vector2 heartPosition = initialPosition.add(new Vector2(heartNumber * heartSize.x(),
                    0));
            Heart heart = new Heart(heartPosition, heartSize, heartImage, brickerGameManager);
            heartObjects[heartNumber] = heart;
            brickerGameManager.addObject(heart, Layer.UI);
        }
    }
}

