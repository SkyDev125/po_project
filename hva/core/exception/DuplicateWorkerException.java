package hva.core.exception;

import java.io.Serial;

public class DuplicateWorkerException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String _id;

    public DuplicateWorkerException(String id) {

        super("Worker with id " + id + " already exists");
        _id = id;
    }

    public String id() {
        return _id;
    }

}
