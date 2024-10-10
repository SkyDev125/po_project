package hva.core;

import java.io.Serial;
import java.io.Serializable;

import java.util.HashMap;

/**
 * Class representing an species of animals in the zoo hotel.
 * 
 * <p>
 * A species is defined by its id, name, and keeps record of its {@link Animal}s and 
 * {@link Vet}s. The species can return its {@link #animalCount()} and {@link #vetCount()}.
 */
public class Species implements Serializable, Comparable<Species> {

  @Serial
  private static final long serialVersionUID = 1L;

  private final String _id;
  private final String _name;
  private final HashMap<String, Animal> _animals = new HashMap<String, Animal>();
  private final HashMap<String, Vet> _vets = new HashMap<String, Vet>();

  /*
   * <------------------------ Constructor ------------------------>
   */

  Species(String id, String name) {
    _id = id;
    _name = name;
  }

  /*
   * <------------------------ Gets ------------------------>
   */

  String id() {
    return _id;
  }

  String name() {
    return _name;
  }

  /*
   * <------------------------ Sets ------------------------>
   */

  void addAnimal(Animal animal) {
    _animals.put(animal.id(), animal);
  }

  void addVet(Vet vet) {
    _vets.put(vet.id(), vet);
  }

  void removeVet(Vet vet) {
    _vets.remove(vet.id());
  }

  /*
   * <------------------------ Others ------------------------>
   */

  int animalCount() {
    return _animals.size();
  }

  int vetCount() {
    return _vets.size();
  }

  @Override
  public int compareTo(Species species) {
    return _id.compareTo(species.id()) + _name.compareTo(species.name());
  }
}
