package hva.core.exception;

import java.io.Serial;

public class TreeNotFoundException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String _id;

    public TreeNotFoundException(String id) {
        super("Tree with id " + id + " not found");
        _id = id;
    }

    public String id() {
        return _id;
    }

}
