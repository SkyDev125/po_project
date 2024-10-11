package hva.core;

import java.io.Serial;
import java.io.Serializable;

import java.util.Map;

/**
 * Class representing an species of animals in the zoo hotel.
 * 
 * <p>
 * A species is defined by its id, name, and keeps record of its {@link Animal}s and {@link Vet}s.
 * The species can return its {@link #animalCount()} and {@link #vetCount()}.
 */
public class Species implements Serializable, Comparable<Species> {

  @Serial
  private static final long serialVersionUID = 1L;

  private final String _id;
  private final String _name;
  private final Map<String, Animal> _animals = new CaseInsensitiveHashMap<Animal>();
  private final Map<String, Vet> _vets = new CaseInsensitiveHashMap<Vet>();

  /*
   * <------------------------ Constructor ------------------------>
   */

  /**
   * Constructor which creates this species.
   * 
   * @param id the identifier of this species
   * @param name the identifier of this species
   */
  Species(String id, String name) {
    _id = id;
    _name = name;
  }

  /*
   * <------------------------ Gets ------------------------>
   */

  /**
   * Retrieves the identifier of this species.
   * 
   * <p>
   * The identifier of this species is an unique String by which this species is identified.
   *
   * @return the identifier of this species
   */
  String id() {
    return _id;
  }

  /**
   * Retrieves the name of this species.
   * 
   * <p>
   * The name of this species is a non unique String.
   *
   * @return the name of this species
   */
  String name() {
    return _name;
  }

  /*
   * <------------------------ Sets ------------------------>
   */

  /**
   * Adds an animal to this species.
   * 
   * @param animal The animal to be added.
   * 
   * @see Hotel#addAnimal(String, String, String, String)
   * @see Animal
   */
  void addAnimal(Animal animal) {
    _animals.put(animal.id(), animal);
  }

  /**
   * Adds a veterinarian to this species, becoming his responsibility.
   * 
   * @param vet The veterinarian to be added.
   * 
   * @see Vet#addResponsibility(String)
   * @see Vet
   */
  void addVet(Vet vet) {
    _vets.put(vet.id(), vet);
  }

  /**
   * Removes a veterinarian from this species, it will no longer be his responsibility.
   * 
   * @param vet The veterinarian to be removed.
   * 
   * @see Vet#removeResponsibility(String)
   * @see Vet
   */
  void removeVet(Vet vet) {
    _vets.remove(vet.id());
  }

  /*
   * <------------------------ Others ------------------------>
   */

  /**
   * Counts the number of animals of this species.
   * 
   * @return the number of animals of this species
   * 
   * @see Animal
   */
  int animalCount() {
    return _animals.size();
  }

  /**
   * Counts the number of veterinarians responsible for this species.
   * 
   * @return the number of veterinarians of this species
   * 
   * @see Vet
   */
  int vetCount() {
    return _vets.size();
  }

  /**
   * Default method of comparison between two species.
   * 
   * <p>
   * This method compares two species by their identifier in a case-insensitive manner. Returns a 
   * negative integer, zero, or a positive integer as this object is less than, equal to, or greater
   * than the specified object.
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

  /**
   * Returns a hash code value for this species, used for hash maps and case insensitive hash maps.
   * 
   * @return the hash code for this species
   * 
   * @see Object#hashCode()
   */
  @Override
  public int hashCode() {
    return _id.hashCode();
  }
}
