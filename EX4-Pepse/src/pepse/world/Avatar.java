package pepse.world;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.AnimationRenderable;
import danogl.util.Vector2;
import pepse.util.Constants;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * class represents game obejct of avatar
 */
public class Avatar extends GameObject {


    private Runnable playerJumpedCallback;

    private enum AvatarState {
        Idle,
        Run,
        Jump
    }

    private final UserInputListener inputListener;
    private float energy;
    private AvatarState state;
    private AnimationRenderable idleAnimation;
    private AnimationRenderable runAnimation;
    private AnimationRenderable jumpAnimation;

    private final ArrayList<JumpObserver> jumpObservers = new ArrayList<>();

    /**
     * @param topLeftCorner <b>bottom left corner</b> position for object
     * @param inputListener input listener to move avatar form keyboard
     * @param imageReader   image reader to read avatar assets
     * @see AvatarState
     */
    public Avatar(Vector2 topLeftCorner, UserInputListener inputListener,
                  ImageReader imageReader) {
        super(topLeftCorner.add(new Vector2(0, -Constants.AVATAR_SIZE.y())),
                Constants.AVATAR_SIZE,
                imageReader.readImage(Constants.AVATAR_INITIAL_IMAGE, true));
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        transform().setAccelerationY(Constants.GRAVITY);
        this.inputListener = inputListener;
        energy = 100f;
        this.state = AvatarState.Idle;
        idleAnimation = new AnimationRenderable(Constants.IDLE_PATHS, imageReader, true,
                Constants.TIME_BETWEEN_CLIPS);
        runAnimation = new AnimationRenderable(Constants.RUN_PATHS, imageReader, true,
                Constants.TIME_BETWEEN_CLIPS);
        jumpAnimation = new AnimationRenderable(Constants.JUMP_PATHS, imageReader, true,
                Constants.TIME_BETWEEN_CLIPS);

    }

    /**
     * update function to set avatar animation and check avatar movement.
     *
     * @param deltaTime The time elapsed, in seconds, since the last frame. Can
     *                  be used to determine a new position/velocity by multiplying
     *                  this delta with the velocity/acceleration respectively
     *                  and adding to the position/velocity:
     *                  velocity += deltaTime*acceleration
     *                  pos += deltaTime*velocity
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        checkAvatarMovement();
        setAvatarAnimaton();
    }

    private void setAvatarAnimaton() {
        if (state == AvatarState.Idle) {
            renderer().setRenderable(idleAnimation);
        }
        if (state == AvatarState.Run) {
            renderer().setRenderable(runAnimation);
        }
        if (state == AvatarState.Jump) {
            renderer().setRenderable(jumpAnimation);
        }
    }

    private void checkAvatarMovement() {

        float xVel = 0;
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            xVel -= Constants.VELOCITY_X;
        }
        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            xVel += Constants.VELOCITY_X;
        }
        if (energy >= 0.5f && xVel != 0) {
            transform().setVelocityX(xVel);
            energy -= 0.5f;
            if (getVelocity().y() == 0) {
                state = AvatarState.Run;
            }
            if (xVel < 0) {
                renderer().setIsFlippedHorizontally(true);
            }
            else {
                renderer().setIsFlippedHorizontally(false);
            }
        }
        else {
            transform().setVelocityX(0);
        }


        if (inputListener.isKeyPressed(KeyEvent.VK_SPACE) && getVelocity().y() == 0 && energy >= 10) {
            energy -= 10f;
            transform().setVelocityY(Constants.VELOCITY_Y);
            state = AvatarState.Jump;
            for(JumpObserver jumpObserver : jumpObservers) {
                jumpObserver.updateJump();
            }
        }

        if (getVelocity().y() == 0 && xVel == 0) {
            energy = Math.min(energy + 1f, 100f);
            state = AvatarState.Idle;
        }
    }


    /**
     * getter for energy of avatar
     *
     * @return avatar energy
     */
    public float getEnergy() {
        return energy;
    }

    /**
     * adds energy to avatar. avatar energy max is 100f.
     *
     * @param energy a positive number
     */
    public void addEnergy(float energy) {
        if (energy < 0) {
            return;
        }
        this.energy = Math.min(this.energy + energy, 100f);
    }

    /**
     * register a jump observer into list of observers
     * @param jumpObserver jump observer to be registered
     */
    public void registerObserverToJump(JumpObserver jumpObserver) {
        this.jumpObservers.add(jumpObserver);
    }
}
