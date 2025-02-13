package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.ScheduledTask;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;
import pepse.util.Constants;

import pepse.world.*;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;
import pepse.world.daynight.SunHalo;
import pepse.world.trees.*;
import pepse.world.ui.NumericEnergyCounter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Game Manager of Pepse, responsible for running the program.
 * used danogl library to createTree game.
 *
 * @author eladfirer
 * @see danogl
 */
public class PepseGameManager extends GameManager {

    private Avatar avatar;
    private int lastMinX, lastMaxX;
    private Terrain terrain;
    private WindowController windowController;
    private Random rand;
    private Flora flora;
    private Vector2 windowDimensions;
    private ImageReader imageReader;
    private UserInputListener inputListener;

    /**
     * Game Manager Constructor
     *
     * @param windowTitle      window title for game
     * @param windowDimensions window dimensions for game
     */
    public PepseGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
        this.rand = new Random();
    }

    /**
     * This method initializes the game.
     *
     * @param imageReader      Contains a single method: readImage, which reads an image from disk.
     *                         See its documentation for help.
     * @param soundReader      Contains a single method: readSound, which reads a wav file from
     *                         disk. See its documentation for help.
     * @param inputListener    Contains a single method: isKeyPressed, which returns whether
     *                         a given key is currently pressed by the user or not. See its
     *                         documentation.
     * @param windowController Contains an array of helpful, self explanatory methods
     *                         concerning the window.
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.windowController = windowController;
        this.windowDimensions = windowController.getWindowDimensions();
        this.imageReader = imageReader;
        this.inputListener = inputListener;
        createWorld(); // creates sky, sun, night, blocks and trees.
        createAvatar(); // creates avatar
        createCloud(); // creates cloud
    }

    private void createWorld() {
        GameObject sky = Sky.create(windowDimensions);
        gameObjects().addGameObject(sky, Layer.BACKGROUND);


        GameObject night =
                Night.create(windowDimensions,
                        Constants.CYCLE_LENGTH
                );
        gameObjects().addGameObject(night, Layer.FOREGROUND);

        GameObject sun = Sun.create(windowDimensions,
                Constants.CYCLE_LENGTH);
        gameObjects().addGameObject(sun, Constants.LAYER_SUN);

        GameObject sunHalo = SunHalo.create(sun);
        gameObjects().addGameObject(sunHalo, Constants.LAYER_SUN_HALO);


        int seed = new Random().nextInt();
        terrain = new Terrain(windowDimensions, seed);
        flora = new Flora(terrain::groundHeightAt);

        int startX = 0;
        int endX = (int) windowDimensions.x();
        generateTerrainAndTrees(startX - Constants.BUFFER, endX + Constants.BUFFER);
        lastMinX = startX - Constants.BUFFER;
        lastMaxX = endX + Constants.BUFFER;
    }

    private void createAvatar() {
        Vector2 leftBottomCorner = new Vector2(windowDimensions.x() / 2,
                terrain.groundHeightAt(windowDimensions.x() / 2));
        avatar = new Avatar(leftBottomCorner, inputListener, imageReader);
        gameObjects().addGameObject(avatar, Constants.LAYER_AVATAR);
        setCamera(new Camera(avatar, Vector2.ZERO,
                windowDimensions,
                windowDimensions));

        TextRenderable textRenderable = new TextRenderable("100%");
        NumericEnergyCounter numericEnergyCounter = new NumericEnergyCounter(Vector2.ZERO,
                Constants.COUNTER_SIZE,
                textRenderable,
                gameObject ->
                        gameObject.renderer().setRenderable(new TextRenderable(
                                String.valueOf((int) avatar.getEnergy()) + "%"))
        );
        gameObjects().addGameObject(numericEnergyCounter, Layer.UI);


        Fruit.setFruitCollisionHandler(this::fruitCollisionHandler);
    }

    private void fruitCollisionHandler(Fruit fruit, GameObject gameObject) {
        if (gameObject == avatar) {
            avatar.addEnergy(10);
            gameObjects().removeGameObject(fruit);
            new ScheduledTask(gameObject,
                    Constants.WAIT_TIME_FOR_FRUIT,
                    false,
                    () ->
                    {
                        gameObjects().addGameObject(fruit, Constants.LAYER_AVATAR);
                        Tree.removeActivatedFruit(fruit);
                    }
            );
            Tree.addActivatedFruit(fruit);
        }
    }


    private void createCloud() {
        Cloud cloud = new Cloud(camera(),
                windowController.getWindowDimensions(),
                (gameObject -> gameObjects().removeGameObject(gameObject,
                        Constants.LAYER_CLOUD)),
                (gameObject -> gameObjects().addGameObject(gameObject, Constants.LAYER_CLOUD)));
        List<GameObject> cloudBlocks = Cloud.createCloud(windowDimensions.y());
        for (GameObject go : cloudBlocks) {
            gameObjects().addGameObject(go, Constants.LAYER_CLOUD);
        }
        avatar.registerObserverToJump(cloud);
    }



    /**
     * update method responsible for updating all objects in game
     *
     * @param deltaTime The time, in seconds, that passed since the last invocation
     *                  of this method (i.e., since the last frame). This is useful
     *                  for either accumulating the total time that passed since some
     *                  event, or for physics integration (i.e., multiply this by
     *                  the acceleration to get an estimate of the added velocity or
     *                  by the velocity to get an estimate of the difference in position).
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 cameraPosition = this.camera().getCenter();
        updateWorld(cameraPosition, windowController.getWindowDimensions()); // this method is
        // responsible for making the world infinite
    }


    private void updateWorld(Vector2 cameraPosition, Vector2 windowDimensions) {
        int currentMinX =
                (int) cameraPosition.x() - ((int) windowDimensions.x() / 2) - Constants.BUFFER;
        int currentMaxX =
                (int) cameraPosition.x() + ((int) windowDimensions.x() / 2) + Constants.BUFFER;

        if (currentMinX + Constants.BLOCK_SIZE < lastMinX) {
            generateTerrainAndTrees(currentMinX, lastMinX);
            lastMinX = currentMinX;
            lastMaxX = currentMaxX;
            lastMinX =
                    (int) Math.floor((double) lastMinX / Constants.BLOCK_SIZE) * Constants.BLOCK_SIZE;
            lastMaxX =
                    (int) Math.floor((double) lastMaxX / Constants.BLOCK_SIZE) * Constants.BLOCK_SIZE;
            removeOffScreenObjects(lastMinX, lastMaxX);
        }
        else if (currentMaxX - Constants.BLOCK_SIZE > lastMaxX) {
            generateTerrainAndTrees(lastMaxX, currentMaxX);
            lastMaxX = currentMaxX;
            lastMinX = currentMinX;
            lastMinX =
                    (int) Math.floor((double) lastMinX / Constants.BLOCK_SIZE) * Constants.BLOCK_SIZE;
            lastMaxX =
                    (int) Math.floor((double) lastMaxX / Constants.BLOCK_SIZE) * Constants.BLOCK_SIZE;
            removeOffScreenObjects(lastMinX, lastMaxX);
        }


    }

    private void generateTerrainAndTrees(int minX, int maxX) {


        List<Block> blocks = terrain.createInRange(minX, maxX);
        for (Block block : blocks) {
            gameObjects().addGameObject(block, block.returnLayer());
        }

        ArrayList<Tree> trees =
                flora.createInRange(minX, maxX);

        for (Tree tree : trees) {
            gameObjects().addGameObject(tree, Constants.LAYER_TREE);
            tree.controlTreePartsGame((gameObject, integer)
                    -> gameObjects().addGameObject(gameObject, integer));
        }
    }

    private void removeOffScreenObjects(int currentMinX, int currentMaxX) {
        for (int layerId : Constants.LAYERS_TO_REMOVE_IN_GAME) {
            List<GameObject> objectsToRemove = new ArrayList<>();

            for (GameObject gameObject : gameObjects().objectsInLayer(layerId)) {
                if (gameObject.getTopLeftCorner().x() < currentMinX
                        || gameObject.getTopLeftCorner().x() > currentMaxX) {
                    objectsToRemove.add(gameObject);
                }
            }

            for (GameObject gameObject : objectsToRemove) {
                if (layerId == Constants.LAYER_TREE) {
                    Tree tree = (Tree) gameObject;
                    tree.controlTreePartsGame((gameObject1, integer)
                            -> gameObjects().removeGameObject(gameObject1, integer));
                }
                gameObjects().removeGameObject(gameObject, layerId);
            }
        }
    }

    /**
     * main method for running program
     *
     * @param args arguments to run program (not needed in our program)
     */
    public static void main(String[] args) {
        PepseGameManager gameManager = new PepseGameManager("Pepse!!!", Constants.WINDOW_SIZE);
        gameManager.run();
    }
}
