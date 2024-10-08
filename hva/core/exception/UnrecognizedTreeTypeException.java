package hva.core.exception;

public class UnrecognizedTreeTypeException extends Exception {

  private static final long serialVersionUID = 1L;

  private final String _type;

  public UnrecognizedTreeTypeException(String type) {
    super("Unrecognized tree type: " + type);
    _type = type;
  }

  public String type() {
    return _type;
  }

}
