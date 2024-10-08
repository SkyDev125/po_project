package hva.core.exception;

public class UnrecognizedWorkerTypeException extends Exception {

  private static final long serialVersionUID = 1L;

  private final String _type;

  public UnrecognizedWorkerTypeException(String type) {
    super("Unrecognized worker type: " + type);
    _type = type;
  }

  public String type() {
    return _type;
  }

}
