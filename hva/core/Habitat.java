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
  private final Map<Species, Influence> _suitability = new HashMap<Species, Influence>();
  private final Map<String, CareTaker> _careTakers = new CaseInsensitiveHashMap<CareTaker>();
  private final Map<String, Tree> _trees = new CaseInsensitiveHashMap<Tree>();

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
    speciesAnimals.remove(animal);
    if (speciesAnimals.isEmpty()) {
      _animals.remove(animal.species());
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

  /**
   * Default method of comparison between two habitats.
   * 
   * <p>
   * This method compares two {@link Habitat}s by their identifier in a case-insensitive manner.
   * returning a negative integer, zero, or a positive integer as this object is less than, equal
   * to, or greater than the specified object.
   * 
   * @param habitat the habitat to be compared.
   * @return an integer value representing the comparison between the two animals.
   * @see String#compareToIgnoreCase(String)
   * @see Comparable#compareTo(Object)
   */
  @Override
  public int compareTo(Habitat habitat) {
    return _id.compareToIgnoreCase(habitat.id());
  }
}
