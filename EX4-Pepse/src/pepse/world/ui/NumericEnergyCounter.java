package pepse.world.ui;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import java.util.function.Consumer;

/**
 * this class represents a game object fo the energy bar UI
 */
public class NumericEnergyCounter extends GameObject {
    /**
     * Numeric Energy Counter Constructor.
     * @param topLeftCorner top left corner position for object
     * @param dimensions object dimensions
     * @param renderable how to render object
     * @param callback callback to be called every update
     */
    public NumericEnergyCounter(Vector2 topLeftCorner,
                                Vector2 dimensions,
                                Renderable renderable,
                                Consumer<GameObject> callback) {
        super(topLeftCorner, dimensions, renderable);
        this.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        this.addComponent(deltaTime -> callback.accept(this));
    }

}
