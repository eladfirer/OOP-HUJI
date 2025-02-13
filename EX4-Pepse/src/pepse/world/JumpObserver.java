package pepse.world;

/**
 * this is an interface for classes who are observers of avatar jumps
 */
public interface JumpObserver {
    /**
     * this method is being called by avatar when he jumps for all his observers
     */
    public void updateJump();
}
