package hva.core;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import hva.core.enumf.Influence;

import java.io.Serial;
import java.io.Serializable;

public class Habitat implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;

    private final String _id;
    private final String _name;
    private int _area;
    private HashMap<String, Animal> _animals;
    private HashMap<Species, Influence> _suitability;
    private HashMap<String, CareTaker> _careTakers;
    private HashMap<String, Tree> _trees;

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

    public int area() {
        return _area;
    }

    public Collection<Animal> animals() {
        return Collections.unmodifiableCollection(_animals.values());
    }

    public Collection<CareTaker> careTakers() {
        return Collections.unmodifiableCollection(_careTakers.values());
    }

    public Collection<Tree> trees() {
        return Collections.unmodifiableCollection(_trees.values());
    }

    // sets

    // others
    protected void addAnimal(Animal animal) {
        _animals.put(animal.id(), animal);
    }

    protected void removeAnimal(Animal animal) {
        _animals.remove(animal.id());
    }
}
