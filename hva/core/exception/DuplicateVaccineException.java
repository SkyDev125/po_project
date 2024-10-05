package hva.core.exception;

import java.io.Serial;

public class DuplicateVaccineException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String _id;

    public DuplicateVaccineException(String id) {
        super("Vaccine with id " + id + " already exists");
        _id = id;
    }

    public String id() {
        return _id;
    }

}
