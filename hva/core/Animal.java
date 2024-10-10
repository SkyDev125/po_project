package hva.core;

import java.io.Serial;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import java.util.stream.Collectors;

/**
 * Class representing an animal in the zoo hotel.
 * 
 * <p>
 * An animal is defined by its id, name, {@link Species}, {@link Habitat} and keeps record of its
 * {@link VaccineRegistry}. The animal can be transfered to another habitat through
 * {@link #transferAnimal(Habitat)}, and it can calculate its {@link #satisfaction()}.
 */
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
   * <------------------------ Sets ------------------------>
   */
  
  /**
   * This method as a vaccine register to the animal.
   * 
   * @param vaccineReg the vaccine registry to be added.
   */
  void addVaccineRegistration(VaccineRegistry vaccineReg) {
    _vaccineRegistry.add(vaccineReg);
  }

  /*
   * <------------------------ Others ------------------------>
   */

  /**
   * Transfer the animal from the current habitat to a given one.
   *  
   * @param habitat to tranfer the animal to.
   */
  void transferAnimal(Habitat habitat) {
    _habitat.removeAnimal(this);
    habitat.addAnimal(this);
    _habitat = habitat;
  }

  /**
   * Calculates the satisfaction of the animal.
   * 
   * This method calculates the satisfaction of the animal based on its habitat and the other
   * animals present there.
   * 
   * @return the satisfaction of the animal.
   */
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

  @Override
  public int compareTo(Animal animal) {
    return _id.compareTo(animal.id());
  }
}
