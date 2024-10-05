package hva.core.exception;

import java.io.Serial;

public class DuplicateSpeciesException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String _id;

    public DuplicateSpeciesException(String id) {
        super("Species with id " + id + " already exists");
        _id = id;
    }

    public String id() {
        return _id;
    }
}
