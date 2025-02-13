package pepse.world.trees;

import danogl.GameObject;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.util.Constants;
import pepse.util.Triplet;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.BiConsumer;

/**
 * this class represents a tree object in game.
 */
public class Tree extends GameObject {
    private Triplet<ArrayList<Log>, ArrayList<Leaf>, ArrayList<Fruit>> treeParts;
    private static ArrayList<Fruit> activatedFruits = new ArrayList<>();

    /**
     * Tree Constructor. acts as an empty Game Object parent for its tree parts.
     *
     * @param topLeftCorner top left corner position for object
     * @param treeParts     a Triplet class containing all tree objects
     * @see Fruit
     * @see Log
     * @see Leaf
     * @see Triplet
     */
    public Tree(Vector2 topLeftCorner,
                Triplet<ArrayList<Log>, ArrayList<Leaf>, ArrayList<Fruit>> treeParts) {
        super(topLeftCorner, Vector2.ZERO, null);
        this.treeParts = treeParts;
    }

    /**
     * method gets a callback to remove/add tree parts from game.
     *
     * @param gameObjectCallback callback to apply on treeParts
     * @see Fruit
     * @see Log
     * @see Leaf
     * @see Triplet
     */
    public void controlTreePartsGame(BiConsumer<GameObject, Integer> gameObjectCallback) {
        for (Log log : treeParts.getFirst()) {
            gameObjectCallback.accept(log, Constants.LAYER_LOG);
        }
        for (Leaf leaf : treeParts.getSecond()) {
            gameObjectCallback.accept(leaf, Constants.LAYER_LEAVES);
        }
        for (Fruit fruit : treeParts.getThird()) {
            gameObjectCallback.accept(fruit, Constants.LAYER_AVATAR);
        }
    }

    /**
     * this method creating a tree in a specific location.
     *
     * @param x            x location for tree
     * @param groundHeight y location for tree
     * @return the required treeParts in the location
     * @see Fruit
     * @see Log
     * @see Leaf
     * @see Triplet
     */
    public static Tree createTree(int x, float groundHeight, Random random) {
        ArrayList<Log> logs = new ArrayList<>();
        ArrayList<Leaf> leaves = new ArrayList<>();
        ArrayList<Fruit> fruits = new ArrayList<>();
        RectangleRenderable rendererLog = new RectangleRenderable(ColorSupplier.approximateColor(
                Constants.LOG_COLOR));
        int logHeight = random.nextInt(4, 7);
        for (int i = 1; i <= logHeight; i++) {
            logs.add(new Log(new Vector2(x, groundHeight - Constants.BLOCK_SIZE * i),
                    rendererLog));
        }

        int leafWidth = 4;
        int leafHeight = 4;


        float logTopY = groundHeight - Constants.BLOCK_SIZE * logHeight;

        boolean fruitInPosition = false;
        for (int dx = -leafWidth; dx <= leafWidth; dx++) {
            for (int dy = 1; dy <= leafHeight; dy++) {
                float leafX = x + dx * Constants.BLOCK_SIZE;
                float leafY = logTopY - dy * Constants.BLOCK_SIZE;

                Vector2 topLeftCorner = new Vector2(leafX, leafY);
                for(Fruit fruit: activatedFruits){
                    if(fruit.getTopLeftCorner().equals(topLeftCorner)){
                        fruitInPosition = true;
                    }
                }
                // in case of fruit in position true, we know that we cant get into this condition
                if (random.nextDouble() < 0.7) {
                    RectangleRenderable rendererLeaf =
                            new RectangleRenderable(ColorSupplier.approximateColor(
                                    Constants.LEAF_COLOR));
                    leaves.add(new Leaf(
                            topLeftCorner,
                            new Vector2(Constants.BLOCK_SIZE, Constants.BLOCK_SIZE),
                            rendererLeaf, random
                    ));

                }
                else if (random.nextDouble() < 0.2) {
                    if(!fruitInPosition){
                        OvalRenderable rendererFruit =
                                new OvalRenderable(Constants.getRandomFruitColor(random));
                        Fruit fruit = new Fruit(
                                topLeftCorner,
                                new Vector2(Constants.BLOCK_SIZE, Constants.BLOCK_SIZE),
                                rendererFruit
                        );
                        fruits.add(fruit);
                    }
                    else{
                        // maintain the randomness of the sequence
                        Constants.getRandomFruitColor(random);
                    }
                }
                fruitInPosition = false;
            }
        }
        Triplet<ArrayList<Log>, ArrayList<Leaf>, ArrayList<Fruit>> treeParts = new Triplet<>(logs
                , leaves, fruits);
        return new Tree(new Vector2(x, groundHeight), treeParts);
    }


    /**
     * this method adds to a static array list an activated fruit in game (fruit that avatar
     * touched)
     * @param fruit to be added
     */
    public static void addActivatedFruit (Fruit fruit){
        activatedFruits.add(fruit);
    }

    /**
     * this method removes from the array list a given fruit in game (fruit that respawned in game)
     * @param fruit to be added
     */
    public static void removeActivatedFruit (Fruit fruit){
        activatedFruits.remove(fruit);
    }
}
