package pepse.world;

import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.Constants;

/**
 * Block object representing a single gameobject block in game
 */
public class Block extends GameObject {
    private int layer;

    /**
     * Block Constructor.
     *
     * @param topLeftCorner top left corner position for object
     * @param renderable    how to render object
     */
    public Block(Vector2 topLeftCorner, Renderable renderable) {
        super(topLeftCorner, Vector2.ONES.mult(Constants.BLOCK_SIZE), renderable);
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
    }

    /**
     * defines layer for block according to block depth
     *
     * @param depth
     */
    public void defineLayer(int depth) {
        if (depth < 2) {
            layer = Constants.LAYER_UPPER_GROUND;
        }
        else {
            layer = Constants.LAYER_LOWER_GROUND;
        }
    }

    /**
     * returns required layer for block
     *
     * @return
     */
    public int returnLayer() {
        return layer;
    }
}
