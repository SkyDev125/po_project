package hva.core;

import java.util.HashMap;

public class Vet extends Worker {
    private HashMap<String, Species> _responsibilities;

    // constructor
    public Vet(String id, String name, Hotel hotel) {
        super(id, name, hotel);
        _responsibilities = new HashMap<String, Species>();
    }

    // methods
    protected void addResponsibility(String id) {
        _responsibilities.put(id, hotel().speciesExists(id));
    }

    protected void removeResponsibility(String id) {
        _responsibilities.remove(id);
    }

    protected int satisfaction() {
        int satisfactionPerSpecies = 0;

        // calcular satisfacao

        return (20 - satisfactionPerSpecies);
    }

    public String toString() {
        String responsibilities = "";

        if (responsibilities != null) {

            responsibilities = "|";

            for (HashMap.Entry<String, Species> set : _responsibilities.entrySet()) {
                responsibilities += set.getKey() + ",";
            }
            responsibilities.substring(0, responsibilities.length() - 1);
        }
        return "VET|" + super.id() + "|" + super.name() + responsibilities;
    }
}
