package hva.core.exception;

public class SpeciesNotFoundException extends Exception {

    private static final long serialVersionUID = 202407081733L;

    public SpeciesNotFoundException(String species) {
        super("Species with id " + species + " not found");
    }
    
}
