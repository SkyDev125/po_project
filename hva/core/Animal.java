package hva.core;

import java.util.*;
import java.util.stream.Collectors;

import hva.core.enumerator.Influence;

import java.io.*;

public class Animal implements Serializable {

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
  public Animal(String id, String name, Species species, Habitat habitat) {
    _id = id;
    _name = name;
    _species = species;
    _habitat = habitat;
  }

  /*
   * <------------------------ Gets ------------------------>
   */
  public String id() {
    return _id;
  }

  public String name() {
    return _name;
  }

  public Species species() {
    return _species;
  }

  public List<VaccineRegistry> vaccineRegistry() {
    return Collections.unmodifiableList(_vaccineRegistry);
  }

  /*
   * <------------------------ Others ------------------------>
   */
  protected void transferAnimal(Habitat habitat) {
    _habitat.removeAnimal(this);
    habitat.addAnimal(this);
    _habitat = habitat;
  }

  protected void addVaccineRegistration(VaccineRegistry vaccineReg) {
    _vaccineRegistry.add(vaccineReg);
  }

  protected float satisfaction() {
    int sameSpecies, population, suitability;
    Influence influence;

    sameSpecies =
        (int) _habitat.animals().stream().filter(animal -> animal.species() == _species).count();

    population = _habitat.animals().size();

    influence = _habitat.suitability(_species);
    switch (influence) {
      case POS:
        suitability = 20;
      case NEU:
        suitability = 0;
      default:
        suitability = -20;
    }

    return (20 + (3 * sameSpecies) - (2 * (population - sameSpecies))
        + (_habitat.area() / population) + suitability);
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
}
