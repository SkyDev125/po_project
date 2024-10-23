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
 * {@link VaccineRegistry}.
 * 
 * <p>
 * The animal can be transfered to another habitat through {@link #transferAnimal(Habitat)}, and it
 * can calculate its {@link #satisfaction()}.
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

  /**
   * Constructor which creates this animal.
   *
   * @param id the String identifier of this animal
   * @param name the String name of this animal
   * @param species the Species of this animal
   * @param habitat the Habitat of this animal
   *
   * @see Species
   * @see Habitat
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
   * Retrieves the identifier of this animal.
   * 
   * <p>
   * The identifier of this animal is an unique String by which this animal is identified.
   *
   * @return the identifier of this animal
   */
  String id() {
    return _id;
  }

  /**
   * Retrieves the name of this animal.
   * 
   * <p>
   * The name of this animal is a non unique String.
   *
   * @return the name of this animal
   */
  String name() {
    return _name;
  }

  /**
   * Retrieves the species of this animal.
   * 
   * <p>
   * This method provides a way to access the {@link Species} of this animal.
   *
   * @return the species of this animal
   */
  Species species() {
    return _species;
  }

  /**
   * Retrieves all the vaccine registries of this animal.
   * 
   * <p>
   * This method provides a way to access the collection of vaccine registries without allowing
   * modifications to the underlying collection. The returned collection is a read-only view, and
   * any attempts to modify it will result in an {@code UnsupportedOperationException}.
   * 
   * @return an unmodifiable collecion of the vaccine registries of this animal
   * 
   * @see VaccineRegistry
   */
  List<VaccineRegistry> vaccineRegistry() {
    return Collections.unmodifiableList(_vaccineRegistry);
  }

  /*
   * <------------------------ Sets ------------------------>
   */

  /**
   * This method adds a vaccine register to this animal.
   * 
   * @param vaccineReg The vaccine registry to be added.
   * 
   * @see Vet#vaccinate(Animal, Vaccine)
   * @see VaccineRegistry
   */
  void addVaccineRegistration(VaccineRegistry vaccineReg) {
    _vaccineRegistry.add(vaccineReg);
  }

  /*
   * <------------------------ Others ------------------------>
   */

  /**
   * Transfers this animal from the current <code>habitat</code> to a given one.
   * 
   * @param habitat The habitat to tranfer the animal to.
   * 
   * @see Habitat
   */
  void transferAnimal(Habitat habitat) {
    _habitat.removeAnimal(this);
    habitat.addAnimal(this);
    _habitat = habitat;
  }

  /**
   * Calculates the satisfaction of this animal.
   * 
   * <p>
   * This method calculates the satisfaction of this animal based on its <code>habitat</code> and
   * the animals present there. It follows the formula:
   * <p>
   * satisfaction = 20 + 3*sameSpecies - 2*differentSpecies + area/population + suitability
   * 
   * @return the satisfaction of this animal
   * 
   * @see Habitat
   */
  float satisfaction() {
    int sameSpecies = _habitat.sameSpeciesCount(_species);
    int population = _habitat.animals().size();

    return (20 + (3 * sameSpecies) - (2 * (population - sameSpecies))
        + (_habitat.area() / population) + _habitat.suitability(_species).value());
  }

  /**
   * Returns a String representation of this animal.
   * 
   * <p>
   * This method follows the format:
   * <p>
   * ANIMAL|idAnimal|nomeAnimal|idEspecie|historialDeSaude|idHabitat
   * <p>
   * If the animal was never vaccinated, the format is:
   * <p>
   * ANIMAL|idAnimal|nomeAnimal|idEspecie|VOID|idHabitat
   * 
   * @return the String representation of this animal
   * 
   * @see Object#toString()
   * @see Species
   * @see VaccineRegistry
   * @see Habitat
   */
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
   * This method compares two animals by their identifier in a case-insensitive manner. Returns a
   * negative integer, zero, or a positive integer as this object is less than, equal to, or greater
   * than the specified object.
   * 
   * @param animal The animal to be compared.
   * 
   * @return An integer value representing the comparison between the two animals.
   * 
   * @see String#compareToIgnoreCase(String)
   * @see Comparable#compareTo(Object)
   */
  @Override
  public int compareTo(Animal animal) {
    return _id.compareToIgnoreCase(animal.id());
  }
}
