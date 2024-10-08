package hva.core.exception;

import java.io.Serial;

public class WorkerNotFoundException extends Exception {

  @Serial
  private static final long serialVersionUID = 1L;

  private final String _id;

  public WorkerNotFoundException(String id) {
    super("Worker with id " + id + " not found");
    _id = id;
  }

  public String id() {
    return _id;
  }

}
