package hva.core.exception;

public class DuplicateTreeException extends Exception {

  private static final long serialVersionUID = 1L;

  private final String _id;

  public DuplicateTreeException(String id) {
    super("Tree with id " + id + " already exists");
    _id = id;
  }

  public String id() {
    return _id;
  }

}
