
package pepse.world;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.Constants;


/**
 * class responsible for creating sky effect in game
 */
public class Sky {


    /**
     * this method creates an game object simulating the sky
     *
     * @param windowDimensions window dimensions of program
     * @return the game object to add to game as sky
     */
    public static GameObject create(Vector2 windowDimensions) {
        GameObject sky = new GameObject(
                Vector2.ZERO, windowDimensions,
                new RectangleRenderable(Constants.BASIC_SKY_COLOR));
        sky.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sky.setTag(Constants.SKY);
        return sky;
    }
}
