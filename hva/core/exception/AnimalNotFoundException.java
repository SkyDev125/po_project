package hva.core.exception;

import java.io.Serial;

public class AnimalNotFoundException extends Exception {

    @Serial
    private static final long serialVersionUID = 202407081733L;

    public AnimalNotFoundException(String id) {
        super("Animal with id " + id + " not found");
    }

}
