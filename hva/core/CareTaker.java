package hva.core;

import java.util.HashMap;

public class CareTaker extends Worker {
    private HashMap<String, Habitat> _responsibilities;

    // constructor
    public CareTaker(String id, String name) {
        super(id, name);
        _responsibilities = new HashMap<String, Habitat>();
    }

    // methods
    protected void addResponsibility(String id) {
        _responsibilities.put(id, habitatExists(id));
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

            for (HashMap.Entry<String, Habitat> set : _responsibilities.entrySet()) {
                responsibilities += set.getKey() + ",";
            }
            responsibilities.substring(0, responsibilities.length() - 1);
        }
        return "VET|" + super.id() + "|" + super.name() + responsibilities;
    }
}
