package hva.core;

import java.util.*;
import java.util.stream.Collectors;

import java.io.*;

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
  String id() {
    return _id;
  }

  String name() {
    return _name;
  }

  Species species() {
    return _species;
  }

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
    float sameSpecies = _habitat.sameSpeciesCount(_species);
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
