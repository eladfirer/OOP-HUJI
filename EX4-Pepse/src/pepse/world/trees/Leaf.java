package pepse.world.trees;

import danogl.GameObject;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.Random;

/**
 * class game object represents leaf in game
 */
public class Leaf extends GameObject {
    private Random random;

    /**
     * Leaf Constructor.
     *
     * @param topLeftCorner top left corner position for object
     * @param dimensions    object dimensions
     * @param renderable    how to render object
     */
    public Leaf(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Random random) {
        super(topLeftCorner, dimensions, renderable);
        this.random = random;
        setTransitions();
    }


    private void setTransitions() {

        new ScheduledTask(this,
                random.nextFloat(),
                false,
                this::setWidthTransition
        );

        new ScheduledTask(this,
                random.nextFloat(),
                false,
                this::setAngleTransition
        );
    }

    private void setWidthTransition() {
        float initialValue = 5f;
        if (random.nextBoolean()) {
            initialValue = -5f;
        }
        new Transition<>(this,
                this::setDimensions,
                new Vector2(this.getDimensions().x() - initialValue, this.getDimensions().y()),
                new Vector2(this.getDimensions().x() + initialValue, this.getDimensions().y()),
                Transition.LINEAR_INTERPOLATOR_VECTOR,
                5,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null);
    }

    private void setAngleTransition() {
        float initialValue = 10f;
        if (random.nextBoolean()) {
            initialValue = -10f;
        }
        new Transition<>(this,
                (Float angle) -> this.renderer().setRenderableAngle(angle),
                initialValue,
                -initialValue,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                5,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null);
    }

}
