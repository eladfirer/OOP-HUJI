package ex5.main;

/**
 * this is an enum that represents program output
 */
public enum ProgramOutput {
    /**
     * value for each program output -
     */
    /**
     * success
     */
    SUCCESS(0),
    /**
     * failure
     */
    FAILURE(1),
    /**
     * error
     */
    ERROR(2);

    private final int value;

    /**
     * Constructor of enum
     * @param value value of output
     */
    ProgramOutput(int value) {
        this.value = value;
    }

    /**
     * @return the value of the current output
     */
    public int getValue() {
        return value;
    }
}
