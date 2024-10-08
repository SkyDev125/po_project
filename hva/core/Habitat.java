package hva.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import hva.core.enumerator.Influence;
import java.io.Serial;
import java.io.Serializable;

public class Habitat implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String _id;
    private final String _name;
    private int _area;
    private Map<Species, ArrayList<Animal>> _animals = new HashMap<Species, ArrayList<Animal>>();
    private HashMap<Species, Influence> _suitability = new HashMap<Species, Influence>();
    private HashMap<String, CareTaker> _careTakers = new HashMap<String, CareTaker>();
    private HashMap<String, Tree> _trees = new HashMap<String, Tree>();

    // constructor
    public Habitat(String id, String name, int area) {
        _id = id;
        _name = name;
        _area = area;
    }

    // gets
    public String id() {
        return _id;
    }

    public int area() {
        return _area;
    }

    public Collection<Animal> animals() {
        Collection<Animal> animals = new ArrayList<Animal>();
        for (List<Animal> speciesAnimals : _animals.values()) {
            animals.addAll(speciesAnimals);
        }
        return Collections.unmodifiableCollection(animals);
    }

    public Collection<CareTaker> careTakers() {
        return Collections.unmodifiableCollection(_careTakers.values());
    }

    public Collection<Tree> trees() {
        return Collections.unmodifiableCollection(_trees.values());
    }

    // sets

    public void changeArea(int area) {
        _area = area;
    }

    // others
    protected void addAnimal(Animal animal) {
        _animals.computeIfAbsent(animal.species(), k -> new ArrayList<Animal>()).add(animal);
    }

    protected void removeAnimal(Animal animal) {
        List<Animal> speciesAnimals = _animals.get(animal.species());
        if (speciesAnimals != null) {
            speciesAnimals.remove(animal);
            if (speciesAnimals.isEmpty()) {
                _animals.remove(animal.species());
            }
        }
    }

    public void changeSuitability(Species species, Influence influence) {
        // TODO Implement Command
    }

    public void addTree(Tree tree) {
        _trees.put(tree.id(), tree);
    }

    @Override
    public String toString() {
        return String.format("HABITAT|%s|%s|%d|%d", _id, _name, _area, _trees.size());
    }
}
