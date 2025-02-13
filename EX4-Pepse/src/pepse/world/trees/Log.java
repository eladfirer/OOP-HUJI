package pepse.world.trees;


import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.Constants;
import pepse.world.Block;

/**
 * this class represents a log game object
 *
 * @see Block
 */
public class Log extends Block {

    /**
     * Log Constructor.
     *
     * @param topLeftCorner top left corner position for object
     * @param renderable    how to render object
     * @see Block
     */
    public Log(Vector2 topLeftCorner, Renderable renderable) {
        super(topLeftCorner, renderable);
        this.setTag(Constants.LOG);
    }

    /**
     * not active method for log
     *
     * @param depth parameter for Block Game object
     */
    @Override
    public void defineLayer(int depth) {
        return;
    }

    /**
     * return the layer of log
     *
     * @return layer of log
     */
    @Override
    public int returnLayer() {
        return Constants.LAYER_LOG;
    }
}
