package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class Ball extends GameObject {
    private Sound collisionSound;
    private int collisionCounter;
    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
        collisionCounter = 0;
    }

    public int getCollisionCounter() {
        return collisionCounter;
    }


    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if(other.shouldCollideWith(this)) {
            Vector2 newVelocity = getVelocity().flipped(collision.getNormal());
            setVelocity(newVelocity);
            collisionCounter++;
            collisionSound.play();
        }


    }

}
