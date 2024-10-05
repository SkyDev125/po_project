package hva.core.exception;

import java.io.Serial;

public class DuplicateAnimalException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String _id;

    public DuplicateAnimalException(String id) {
        super("Animal with id " + id + " already exists");
        _id = id;
    }

    public String id() {
        return _id;
    }

}
