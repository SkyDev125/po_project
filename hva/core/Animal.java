package hva.core;

import java.io.Serial;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import java.util.stream.Collectors;

public class Animal implements Serializable, Comparable<Animal> {

  @Serial
  private static final long serialVersionUID = 1L;

  private final String _id;
  private final String _name;
  private final Species _species;
  private Habitat _habitat;
  private final ArrayList<VaccineRegistry> _vaccineRegistry = new ArrayList<VaccineRegistry>();

  /*
   * <------------------------ Constructor ------------------------>
   */
  Animal(String id, String name, Species species, Habitat habitat) {
    _id = id;
    _name = name;
    _species = species;
    _habitat = habitat;
  }

  /*
   * <------------------------ Gets ------------------------>
   */

  /**
   * Retrieves the identifier of the animal.
   * 
   * <p>
   * The identifier of the animal is an unique String by which the animal is identified.
   *
   * @return the identifier of the animal.
   */
  String id() {
    return _id;
  }

  /**
   * Retrieves the name of the animal.
   * 
   * <p>
   * The name of the animal is a non unique String.
   *
   * @return the name of the animal.
   */
  String name() {
    return _name;
  }

  /**
   * Retrieves the species of the animal.
   * 
   * <p>
   * This method provides a way to access the {@link Species} of the animal.
   *
   * @return the species of the animal.
   */
  Species species() {
    return _species;
  }

  /**
   * Retrieves all the vaccine registries in the hotel.
   * 
   * <p>
   * This method provides a way to access the collection of {@link VaccineRegistry} without allowing
   * modifications to the underlying collection. The returned collection is a read-only view, and
   * any attempts to modify it will result in an {@code UnsupportedOperationException}.
   * 
   * @return an unmodifiable {@link Collection} of the vaccine registries of the animal.
   */

  List<VaccineRegistry> vaccineRegistry() {
    return Collections.unmodifiableList(_vaccineRegistry);
  }

  /*
   * <------------------------ Others ------------------------>
   */
  void transferAnimal(Habitat habitat) {
    _habitat.removeAnimal(this);
    habitat.addAnimal(this);
    _habitat = habitat;
  }

  void addVaccineRegistration(VaccineRegistry vaccineReg) {
    _vaccineRegistry.add(vaccineReg);
  }

  float satisfaction() {
    int sameSpecies = _habitat.sameSpeciesCount(_species);
    int population = _habitat.animals().size();

    return (20 + (3 * sameSpecies) - (2 * (population - sameSpecies))
        + (_habitat.area() / population) + _habitat.suitability(_species).getValue());
  }

  @Override
  public String toString() {
    String health = "VOID";

    if (!_vaccineRegistry.isEmpty()) {
      health = _vaccineRegistry.stream().map(vaccineReg -> vaccineReg.vaccineDamage().toString())
          .collect(Collectors.joining(","));
    }

    return String.format("ANIMAL|%s|%s|%s|%s|%s", _id, _name, _species.id(), health, _habitat.id());
  }


  /**
   * Default method of comparison between two animals.
   * 
   * <p>
   * This method compares two {@link Animal} by their identifier in a case-insensitive manner.
   * returning a negative integer, zero, or a positive integer as this object is less than, equal
   * to, or greater than the specified object.
   * 
   * @param animal the animal to be compared.
   * @return an integer value representing the comparison between the two animals.
   * @see String#compareToIgnoreCase(String)
   * @see Comparable#compareTo(Object)
   */
  @Override
  public int compareTo(Animal animal) {
    return _id.compareToIgnoreCase(animal.id());
  }
}
