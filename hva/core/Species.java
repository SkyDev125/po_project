package hva.core;

import java.util.HashMap;

public class Species {
    private final String _id;
    private final String _name;
    private HashMap<String,Animal> _animals;
    private HashMap<String,Vet> _vets;

    // constructor 
    public Species(String id, String name) {
        _id = id;
        _name = name;
    }

    // gets 
    public String id() {
        return _id;
    }

    public String name() {
        return _name;
    }

    // methods
    protected void addAnimal(Animal animal) {
        _animals.put(animal.id(), animal);
    }

    protected void addVet(Vet vet) {
        _vets.put(vet.id(), vet);
    }

    protected void removeVet(Vet vet) {
        _vets.remove(vet.id());
    }

    public int animalCount() {
        return _animals.size();
    }

    public int vetCount() {
        return _vets.size();
    }
}
