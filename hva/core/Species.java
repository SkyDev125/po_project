package hva.core;

import java.io.Serial;
import java.io.Serializable;

import java.util.Map;

public class Species implements Serializable, Comparable<Species> {

  @Serial
  private static final long serialVersionUID = 1L;

  private final String _id;
  private final String _name;
  private final Map<String, Animal> _animals = new CaseInsensitiveHashMap<Animal>();
  private final Map<String, Vet> _vets = new CaseInsensitiveHashMap<Vet>();

  // constructor
  Species(String id, String name) {
    _id = id;
    _name = name;
  }

  // gets
  String id() {
    return _id;
  }

  String name() {
    return _name;
  }

  // methods
  void addAnimal(Animal animal) {
    _animals.put(animal.id(), animal);
  }

  void addVet(Vet vet) {
    _vets.put(vet.id(), vet);
  }

  void removeVet(Vet vet) {
    _vets.remove(vet.id());
  }

  int animalCount() {
    return _animals.size();
  }

  int vetCount() {
    return _vets.size();
  }

  /**
   * Default method of comparison between two species.
   * 
   * <p>
   * This method compares two {@link Species} by their identifier in a case-insensitive manner.
   * returning a negative integer, zero, or a positive integer as this object is less than, equal
   * to, or greater than the specified object.
   * 
   * @param species the species to be compared.
   * @return an integer value representing the comparison between the two species.
   * @see String#compareToIgnoreCase(String)
   * @see Comparable#compareTo(Object)
   */
  @Override
  public int compareTo(Species species) {
    return _id.compareToIgnoreCase(species.id()) + _name.compareToIgnoreCase(species.name());
  }

  @Override
  public int hashCode() {
    return _id.hashCode();
  }
}
