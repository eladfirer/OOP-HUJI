package bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;
import java.util.Vector;

public class Paddle extends GameObject {
    private static final float MOVEMENT_SPEED = 300f;
    private UserInputListener inputListener;
    private Vector2 windowDimensions;

    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, UserInputListener inputListener, Vector2 windowDimensions) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movmentDir = Vector2.ZERO;
        if(inputListener.isKeyPressed(KeyEvent.VK_LEFT)){
            movmentDir = movmentDir.add(Vector2.LEFT);
        }
        if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT)){
            movmentDir = movmentDir.add(Vector2.RIGHT);
        }
        setVelocity(movmentDir.mult(MOVEMENT_SPEED));

        Vector2 topLeftCorner = getTopLeftCorner();
        float paddleWidth = getDimensions().x();

        if (topLeftCorner.x() < 10) {
            setTopLeftCorner(new Vector2(10, topLeftCorner.y()));
            setVelocity(Vector2.ZERO);
        }


        if (topLeftCorner.x() + paddleWidth > windowDimensions.x() - 10) {
            setTopLeftCorner(new Vector2(windowDimensions.x() - paddleWidth - 10, topLeftCorner.y()));
            setVelocity(Vector2.ZERO);
        }

    }

}
