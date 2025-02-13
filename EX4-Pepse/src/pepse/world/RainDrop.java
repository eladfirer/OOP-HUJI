package pepse.world;

import danogl.GameObject;
import danogl.components.RendererComponent;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.Constants;

import java.awt.*;
import java.util.function.Consumer;

/**
 * this class represents a rain drop game object in game
 */
public class RainDrop extends GameObject {
    private final Consumer<GameObject> removeObjectCallback;
    private int alpha;

    /**
     * Rain Drop Constructor.
     *
     * @param topLeftCorner        top left corner position for object
     * @param dimensions           object dimensions
     * @param renderable           how to render object
     * @param removeObjectCallback callback to remove object from game
     */
    public RainDrop(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                    Consumer<GameObject> removeObjectCallback) {
        super(topLeftCorner, dimensions, renderable);
        this.removeObjectCallback = removeObjectCallback;

        this.transform().setAccelerationY(Constants.GRAVITY / 2);
        RendererComponent renderer = this.renderer();

        new Transition<>(this,
                this::setAlpha,
                255f,
                0f,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                2,
                Transition.TransitionType.TRANSITION_ONCE,
                this::run
        );
    }

    private void setAlpha(float alpha) {
        this.alpha = (int) alpha;
        OvalRenderable rainDropRenderable = new OvalRenderable(new Color(0, 255, 255, this.alpha));
        renderer().setRenderable(rainDropRenderable);
    }

    private void run() {
        removeObjectCallback.accept(this);
    }
}
