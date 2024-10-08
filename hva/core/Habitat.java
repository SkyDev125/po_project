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

public class Habitat implements Serializable, Comparable<Habitat> {

  @Serial
  private static final long serialVersionUID = 1L;

  private final String _id;
  private final String _name;
  private int _area;
  private final Map<Species, ArrayList<Animal>> _animals =
      new HashMap<Species, ArrayList<Animal>>();
  private final HashMap<Species, Influence> _suitability = new HashMap<Species, Influence>();
  private final HashMap<String, CareTaker> _careTakers = new HashMap<String, CareTaker>();
  private final HashMap<String, Tree> _trees = new HashMap<String, Tree>();

  /*
   * <------------------------ Constructor ------------------------>
   */

  Habitat(String id, String name, int area) {
    _id = id;
    _name = name;
    _area = area;
  }

  /*
   * <------------------------ Gets ------------------------>
   */

  String id() {
    return _id;
  }

  int area() {
    return _area;
  }

  Influence suitability(Species species) {
    return _suitability.getOrDefault(species, Influence.NEU);
  }

  Collection<Animal> animals() {
    Collection<Animal> animals = new ArrayList<Animal>();
    for (List<Animal> speciesAnimals : _animals.values()) {
      animals.addAll(speciesAnimals);
    }
    return Collections.unmodifiableCollection(animals);
  }

  Collection<CareTaker> careTakers() {
    return Collections.unmodifiableCollection(_careTakers.values());
  }

  Collection<Tree> trees() {
    return Collections.unmodifiableCollection(_trees.values());
  }

  int sameSpeciesCount(Species species) {
    return _animals.getOrDefault(species, new ArrayList<Animal>()).size();
  }

  /*
   * <------------------------ Sets ------------------------>
   */

  void changeArea(int area) {
    _area = area;
  }

  /*
   * <------------------------ Others ------------------------>
   */

  void addAnimal(Animal animal) {
    _animals.computeIfAbsent(animal.species(), k -> new ArrayList<Animal>()).add(animal);
  }

  void removeAnimal(Animal animal) {
    List<Animal> speciesAnimals = _animals.get(animal.species());
    if (speciesAnimals != null) {
      speciesAnimals.remove(animal);
      if (speciesAnimals.isEmpty()) {
        _animals.remove(animal.species());
      }
    }
  }

  void changeSuitability(Species species, Influence influence) {
    _suitability.put(species, influence);
  }

  void addTree(Tree tree) {
    _trees.put(tree.id(), tree);
  }

  @Override
  public String toString() {
    return String.format("HABITAT|%s|%s|%d|%d", _id, _name, _area, _trees.size());
  }

  @Override
  public int compareTo(Habitat habitat) {
    return _id.compareTo(habitat.id());
  }
}
