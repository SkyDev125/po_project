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

/**
 * Class representing an habitat in the zoo hotel.
 * 
 * <p>
 * An habitat is defined by its id, name, area, and keeps record of its {@link Species} and its
 * {@link Influence} in each of them, its {@link Animal}s, {@link CareTaker}s and {@link Tree}s.
 * 
 * <p>
 * The habitat can return its {@link #sameSpeciesCount(Species)}.
 */
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

  /**
   * The constructor of this habitat.
   * 
   * @param id the identifier of this habitat
   * @param name the name of this habitat
   * @param area the area of this habitat
   */
  Habitat(String id, String name, int area) {
    _id = id;
    _name = name;
    _area = area;
  }

  /*
   * <------------------------ Gets ------------------------>
   */

  /**
   * Retrieves the identifier of this habitat.
   * 
   * <p>
   * The identifier of this habitat is an unique String by which this habitat is identified.
   *
   * @return the identifier of this habitat
   */
  public String id() {
    return _id;
  }

  /**
   * Retrieves the area of this habitat.
   * 
   * @return the area of this habitat
   */
  int area() {
    return _area;
  }

  /**
   * Retrives the suitability of a given species in this habitat.
   * 
   * <p>
   * The suitability of the species is a result of the influence (<code>enumeration</code>) of this
   * habitat in the species.
   * 
   * @param species The species whose suitability is to be returned.
   * 
   * @return the inlfuence of this habitat in the species
   * 
   * @see Influence
   */
  Influence suitability(Species species) {
    return _suitability.getOrDefault(species, Influence.NEU);
  }

  /**
   * Retrieves all the animals in this habitat.
   * 
   * <p>
   * This method provides a way to access the collection of animals without allowing modifications
   * to the underlying collection. The returned collection is a read-only view, and any attempts to
   * modify it will result in an {@code UnsupportedOperationException}.
   * 
   * @return an unmodifiable collection of the animals
   * 
   * @see Collections#unmodifiableCollection(Collection)
   * @see Animal
   */
  Collection<Animal> animals() {
    Collection<Animal> animals = new ArrayList<Animal>();
    for (List<Animal> speciesAnimals : _animals.values()) {
      animals.addAll(speciesAnimals);
    }
    return Collections.unmodifiableCollection(animals);
  }

  /**
   * Retrieves all the caretakers responsible for this habitat.
   * 
   * <p>
   * This method provides a way to access the collection of caretakers without allowing
   * modifications to the underlying collection. The returned collection is a read-only view, and
   * any attempts to modify it will result in an {@code UnsupportedOperationException}.
   * 
   * @return an unmodifiable collection of the caretakers
   * 
   * @see Collections#unmodifiableCollection(Collection)
   * @see CareTaker
   */
  Collection<CareTaker> careTakers() {
    return Collections.unmodifiableCollection(_careTakers.values());
  }

  /**
   * Retrieves all the trees in this habitat.
   * 
   * <p>
   * This method provides a way to access the collection of trees without allowing modifications
   * to the underlying collection. The returned collection is a read-only view, and any attempts to
   * modify it will result in an {@code UnsupportedOperationException}.
   * 
   * @return an unmodifiable collection of the trees
   * 
   * @see Collections#unmodifiableCollection(Collection)
   * @see Tree
   */
  Collection<Tree> trees() {
    return Collections.unmodifiableCollection(_trees.values());
  }
  
  /*
  * <------------------------ Sets ------------------------>
  */

  /**
   * Changes the area of this habitat to the given value.
   * 
   * @param area the new area of this habitat
   */
  void changeArea(int area) {
    _area = area;
  }
  
  /**
   * Changes the suitability of a given species to this habitat.
   * 
   * <p>
   * The suitability of the species is a result of the influence (<code>enumeration</code>) of this
   * habitat in the species.
   *  
   * @param species The species whose suitability is to be changed
   * @param influence the new influence of this habitat in the species
   * 
   * @see Influence
   * @see Species
   */
  void changeSuitability(Species species, Influence influence) {
    _suitability.put(species, influence);
  }
  
  /**
   * Adds an animal to this habitat.
   * 
   * @param animal The animal to be added.
   * 
   * @see Hotel#addAnimal(String, String, String, String)
   * @see Animal
   */
  void addAnimal(Animal animal) {
    _animals.computeIfAbsent(animal.species(), k -> new ArrayList<Animal>()).add(animal);
  }
  
  /**
   * Removes an animal from this habitat.
   * 
   * @param animal The animal to be removed.
   * 
   * @see Hotel#transferAnimal(String, String)
   * @see Animal
   */
  void removeAnimal(Animal animal) {
    List<Animal> speciesAnimals = _animals.get(animal.species());
    speciesAnimals.remove(animal);
    if (speciesAnimals.isEmpty()) {
      _animals.remove(animal.species());
    }
  }

  /**
   * Adds a tree to this habitat.
   * 
   * @param tree The tree to be added.
   * 
   * @see Hotel#addTreeToHabitat(String, String, String, int, int, String)
   * @see Tree
   */
  void addTree(Tree tree) {
    _trees.put(tree.id(), tree);
  }
  
  /*
  * <------------------------ Others ------------------------>
  */

  /**
   * Counts the number of animals of a given species in this habitat.
   * 
   * @param species The species to be counted.
   * 
   * @return the number of animals of the species in this habitat
   * 
   * @see Species
   */
  int sameSpeciesCount(Species species) {
    return _animals.getOrDefault(species, new ArrayList<Animal>()).size();
  }

  /**
   * Returns a String representation of this habitat.
   * 
   * <p>
   * This method follows the format:
   * <p>
   * HABITAT|idHabitat|nome|area|numeroArvores
   * 
   * @return the String representation of this habitat
   * 
   * @see Object#toString()
   * @see Tree
   */
  @Override
  public String toString() {
    return String.format("HABITAT|%s|%s|%d|%d", _id, _name, _area, _trees.size());
  }
  
  /**
   * Default method of comparison between two habitats.
   * 
   * <p>
   * This method compares two habitats by their identifier in a case-insensitive manner. Returns a
   * negative integer, zero, or a positive integer as this object is less than, equal to, or greater
   * than the specified object.
   * 
   * @param habitat The habitat to be compared.
   * 
   * @return An integer value representing the comparison between the two animals.
   * 
   * @see String#compareToIgnoreCase(String)
   * @see Comparable#compareTo(Object)
   */
  @Override
  public int compareTo(Habitat habitat) {
    return _id.compareToIgnoreCase(habitat.id());
  }
}
