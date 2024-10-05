package hva.core.exception;

public class VaccineNotFoundException extends Exception {

    private static final long serialVersionUID = 202407081733L;

    public VaccineNotFoundException(String id) {
        super("Vaccine with id " + id + " not found");
    }

}
