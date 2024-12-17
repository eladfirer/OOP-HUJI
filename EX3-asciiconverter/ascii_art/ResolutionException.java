package ascii_art;

/**
 * Resolution exception - problem with resolution
 */
public class ResolutionException extends RuntimeException {

  /**
   * Constructor for exception
   * @param message - message to read
   */
  public ResolutionException(String message) {
    super(message);
  }
}
