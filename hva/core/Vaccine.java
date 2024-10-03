package hva.core;

import java.util.HashMap;
import java.util.List;

public class Vaccine {
    private final String _id;
    private final String _name;
    private int _applyCount;
    private HashMap<String, Species> _species;

    // constructor
    public Vaccine(String id, String name, List<Species> species) {
        _id = id;
        _name = name;
        _species = new HashMap<String, Species>();

        for (Species currentSpecies : species) {
            _species.put(currentSpecies.id(), currentSpecies);
        }
    }

    // others
    public void apply() {
        _applyCount++;
    }

    public String toString() {
        String species = "";

        // TODO

        return "VACINA|" + _id + "|" + _name + "|" + Integer.toString(_applyCount) + "|" + species;
    }

    public boolean equals() {
        
        // TODO

        return true;
    }
}
