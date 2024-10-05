package hva.core.exception;

import java.io.Serial;

public class AnimalNotFoundException extends Exception {

    @Serial
    private static final long serialVersionUID = 202407081733L;
    private final String _id;

    public AnimalNotFoundException(String id) {
        super("Animal with id " + id + " not found");
        _id = id;
    }

    public String id() {
        return _id;
    }

}
