package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;

import danogl.util.Vector2;
import pepse.util.Constants;

import java.awt.*;

/**
 * class responsible for creating sun effect in game
 */
public class Sun {

    /**
     * this method creates an object that simulates sun
     * @param windowDimensions window space
     * @param cycleLength time of a full day
     * @return object to be added to game as the sun
     */
    public static GameObject create(Vector2 windowDimensions,
                                    float cycleLength) {
        OvalRenderable renderable = new OvalRenderable(Color.YELLOW);

        Vector2 initialSunCenter = new Vector2(
                windowDimensions.x() / 2,
                windowDimensions.y() * 1/3
        );

        Vector2 cycleCenter = new Vector2(
                windowDimensions.x() / 2,
                windowDimensions.y() * 2 / 3
        );

        Vector2 sunTopLeftCorner = new Vector2(
                initialSunCenter.x() - (Constants.SUN_RADIUS / 2),
                initialSunCenter.y() - (Constants.SUN_RADIUS / 2));

        GameObject sun = new GameObject(sunTopLeftCorner,
                new Vector2(Constants.SUN_RADIUS, Constants.SUN_RADIUS),
                renderable);

        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sun.setTag(Constants.SUN);

        new Transition<>(sun,
                (Float angle) -> sun.setCenter
                        (initialSunCenter.subtract(cycleCenter)
                                .rotated(angle)
                                .add(cycleCenter)),
                0f,
                360f,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                cycleLength,
                Transition.TransitionType.TRANSITION_LOOP,
                null);


        return sun;

    }
}
