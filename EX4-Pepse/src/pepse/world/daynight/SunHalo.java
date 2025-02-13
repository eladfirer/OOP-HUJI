package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;
import pepse.util.Constants;

/**
 * class responsible for creating sun halo effect in game
 */
public class SunHalo {

    /**
     * this method creates an object that simulates sun halo
     * @param sun sun to make sun halo around
     * @return
     */
    public static GameObject create(GameObject sun){
        OvalRenderable renderable = new OvalRenderable(Constants.COLOR_SUN_HALO);
        GameObject sunHalo = new GameObject(sun.getTopLeftCorner(),
                new Vector2(Constants.SUN_HALO_RADIUS, Constants.SUN_HALO_RADIUS),
                renderable);
        sunHalo.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sunHalo.setTag(Constants.SUN_HALO);

        sunHalo.addComponent((deltaTime -> sunHalo.setCenter(sun.getCenter())));
        return sunHalo;
    }
}
