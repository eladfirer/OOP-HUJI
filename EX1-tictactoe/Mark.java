/**
 * Mark. this enum represents the possible marks that can appear on a game board.<br>
 * {@link #BLANK}: Represents an empty block on the board.<br>
 * {@link #X}: Represents a block marked with an "X".<br>
 * {@link #O}: Represents a block marked with an "O".<br>
 */
public enum Mark {
    BLANK,X,O;

    /**
     * @return a string that represents the mark state.
     */
    public String toString(){
        return switch (this) {
            case X -> "X";
            case O -> "O";
            case BLANK -> null;
        };
    }
}
