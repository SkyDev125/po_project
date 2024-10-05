package hva.core.exception;

import java.io.Serial;

public class HabitatNotFoundException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String _id;

    public HabitatNotFoundException(String id) {
        super("Habitat with id " + id + " not found");
        _id = id;
    }

    public String id() {
        return _id;
    }

}
