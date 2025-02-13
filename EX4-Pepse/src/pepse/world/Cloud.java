package pepse.world;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.Camera;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.util.Constants;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

/**
 * this class is responsible for simulating a cloud in game
 */
public class Cloud implements JumpObserver {

    private static List<GameObject> cloud;
    private static Random rand = new Random();
    private final Camera camera;
    private final Vector2 windowDimensions;
    private final Consumer<GameObject> removeGameObjectCallback;
    private final Consumer<GameObject> addGameObjectCallback;


    /**
     * constructor of cloud
     * @param camera                   camera of program
     * @param windowDimensions         window dimensions
     * @param removeGameObjectCallback callback to remove objects
     * @param addGameObjectCallback    callback to add objects
     */
    public Cloud(Camera camera,
                 Vector2 windowDimensions,
                 Consumer<GameObject> removeGameObjectCallback,
                 Consumer<GameObject> addGameObjectCallback) {
        this.camera = camera;
        this.windowDimensions = windowDimensions;
        this.removeGameObjectCallback = removeGameObjectCallback;
        this.addGameObjectCallback = addGameObjectCallback;

    }

    /**
     * this method creates the cloud
     *
     * @param screenWidth screen width of program
     * @return list of game objects to add to game
     */
    public static List<GameObject> createCloud(float screenWidth) {
        Vector2 startPosition = new Vector2(-210, screenWidth / 12);
        RectangleRenderable blockRenderable = new RectangleRenderable(
                ColorSupplier.approximateMonoColor(Constants.BASE_CLOUD_COLOR));

        cloud = new ArrayList<>();

        for (int row = 0; row < Constants.CLOUD_SHAPE.size(); row++) {
            for (int col = 0; col < Constants.CLOUD_SHAPE.get(row).size(); col++) {
                if (Constants.CLOUD_SHAPE.get(row).get(col) == 1) {
                    Vector2 blockPosition = startPosition.add(new Vector2(
                            col * Constants.BLOCK_SIZE, row * Constants.BLOCK_SIZE));
                    GameObject cloudBlock = new GameObject(blockPosition,
                            new Vector2(Constants.BLOCK_SIZE, Constants.BLOCK_SIZE),
                            blockRenderable);
                    cloudBlock.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
                    new Transition<>(
                            cloudBlock,
                            cloudBlock::setCenter,
                            cloudBlock.getCenter(),
                            cloudBlock.getCenter().add(new Vector2(screenWidth * 2, 0)),
                            Transition.LINEAR_INTERPOLATOR_VECTOR,
                            10,
                            Transition.TransitionType.TRANSITION_LOOP,
                            null
                    );
                    cloud.add(cloudBlock);
                }
            }
        }
        return cloud;
    }

    /**
     * this is an interface method of JumpObserver, when the avatar jumps cloud is being notified
     */
    @Override
    public void updateJump() {
        makeItRain();
    }


    private void makeItRain() {
        OvalRenderable rainDropRenderable = new OvalRenderable(new Color(0, 255, 255, 255));
        for (GameObject o : cloud) {
            if (rand.nextDouble() < 0.2) {
                Vector2 cameraCenter = camera.getCenter();
                Vector2 center =
                        new Vector2(cameraCenter.x() - (windowDimensions.x() / 2) + o.getCenter().x(),
                                cameraCenter.y() - (windowDimensions.y() / 2) + o.getCenter().y());
                RainDrop rainDrop = new RainDrop(center,
                        Vector2.ONES.mult((float) Constants.BLOCK_SIZE / 3),
                        rainDropRenderable,
                        removeGameObjectCallback);
                addGameObjectCallback.accept(rainDrop);
            }
        }
    }

}
