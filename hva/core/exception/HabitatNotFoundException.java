package hva.core.exception;

public class HabitatNotFoundException extends Exception {

    private static final long serialVersionUID = 202407081733L;

    public HabitatNotFoundException(String id) {
        super("Habitat with id " + id + " not found");
    }

}
