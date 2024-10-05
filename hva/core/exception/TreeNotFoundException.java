package hva.core.exception;

public class TreeNotFoundException extends Exception {

    private static final long serialVersionUID = 202407081733L;

    public TreeNotFoundException(String id) {
        super("Tree with id " + id + " not found");
    }

}
