package hva.core;

import java.util.HashMap;


public class Habitat {
    private final String _id;
    private final String _name;
    private int _area;
    private HashMap<String, Animal> _animals;

    // constructor
    public Habitat(String id, String name, int area) {
        _id = id;
        _name = name;
        _area = area;
        _animals = new HashMap<String, Animal>();
    }

    // gets
    public String id() {
        return _id;
    }

    // method 
    protected void addAnimal(Animal animal) {
        _animals.put(animal.id(), animal);          // ordenar e tudo mais, nao sei
    }

    protected void removeAnimal(Animal animal) {
        _animals.remove(animal.id());
    }

    protected Collections animals() {
        
    }
}
