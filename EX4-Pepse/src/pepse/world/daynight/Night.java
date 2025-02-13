package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.Constants;

import java.awt.*;

/**
 * class for creating night effect in game
 */
public class Night {


    /**
     * this method creates an object that simulates night and day simulation
     * @param windowDimensions window space
     * @param cycleLength time of a full day
     * @return object to be added to game as night and day generator
     */
    public static GameObject create(Vector2 windowDimensions, float cycleLength) {

        RectangleRenderable renderable = new RectangleRenderable(Color.BLACK);
        GameObject night = new GameObject(Vector2.ZERO, windowDimensions, renderable);
        night.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        night.setTag(Constants.NIGHT);

        // this transition set night and day according to cycle length
        new Transition<>(night,
                night.renderer()::setOpaqueness,
                Constants.DAYLIGHT_OPACITY,
                Constants.MIDNIGHT_OPACITY,
                Transition.CUBIC_INTERPOLATOR_FLOAT,
                cycleLength / 2,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null
        );

        return night;
    }
}
