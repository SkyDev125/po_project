package hva.core;

import java.io.Serial;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import hva.core.enumerator.VaccineDamage;
import hva.core.exception.SpeciesNotFoundException;
import hva.core.exception.WorkerNotAuthorizedException;

/**
 * Class representing a vet in the zoo hotel.
 * 
 * <p>
 * A vet, or simply vet, is a type of worker and therefore defined by its id, name and hotel. On top
 * of that, he keeps record of its responsabilities, which are species to care for, and vaccine
 * registries of the vaccines he has applied.
 * 
 * <p>
 * The vet, as a worker, can calculate its {@link #satisfaction()}. He can also
 * {@link #vaccinate(Animal, Vaccine)} and {@link #calculateVaccineDamage(Animal, Vaccine)}.
 * 
 * @see Worker
 */
public class Vet extends Worker {

  @Serial
  private static final long serialVersionUID = 1L;

  private VetSatisfactionFormula _vetSatisfactionFormula;
  private final Map<String, Species> _responsibilities = new CaseInsensitiveHashMap<Species>();
  private final ArrayList<VaccineRegistry> _vaccineRegistry = new ArrayList<VaccineRegistry>();

  /*
   * <------------------------ Constructor ------------------------>
   */

  /**
   * The constructor of this vet.
   * 
   * @param id the identifier of this vet
   * @param name the name of this vet
   * @param hotel the hotel of this vet
   * 
   * @see Worker#Worker(String, String, Hotel)
   * @see Hotel
   */
  Vet(String id, String name, Hotel hotel) {
    super(id, name, hotel);
    _vetSatisfactionFormula = new VetSatisfactionDefaultFormula();
  }

  /*
   * <------------------------ Gets ------------------------>
   */

  /**
   * Retrieves all the vaccine registries of this vet.
   * 
   * <p>
   * This method provides a way to access the collection of vaccine registries without allowing
   * modifications to the underlying collection. The returned collection is a read-only view, and
   * any attempts to modify it will result in an {@code UnsupportedOperationException}.
   * 
   * @return an unmodifiable collecion of the vaccine registries of this vet
   * 
   * @see VaccineRegistry
   */
  List<VaccineRegistry> vaccineRegistry() {
    return Collections.unmodifiableList(_vaccineRegistry);
  }

  /**
   * Retrieves all the species this vet has as a responsibity.
   * 
   * <p>
   * This method provides a way to access the collection of species without allowing modifications
   * to the underlying collection. The returned collection is a read-only view, and any attempts to
   * modify it will result in an {@code UnsupportedOperationException}.
   * 
   * @return An unmodifiable collecion of the species this vet has as a responsibility.
   * 
   * @see Species
   */
  Collection<Species> responsibilities() {
    return Collections.unmodifiableCollection(_responsibilities.values());
  }

  /*
   * <------------------------ Sets ------------------------>
   */

  /**
   * This method adds a species responsibility to this vet.
   * 
   * @param id the identifier of the species
   * 
   * @throws SpeciesNotFoundException If the given species does not exist.
   * 
   * @see Worker#addResponsibility(String)
   * @see Species
   */
  void addResponsibility(String id) throws SpeciesNotFoundException {
    // No need to check if it's already there, as the species object is always the
    // same.
    _responsibilities.put(id, hotel().speciesExistsWithException(id));
  }

  /**
   * This method removes a species as a responsibility to this vet.
   * 
   * @param id the identifier of the species
   * 
   * @throws SpeciesNotFoundException If the given species does not exist.
   * 
   * @see Worker#removeResponsibility(String)
   * @see Species
   */
  void removeResponsibility(String id) throws SpeciesNotFoundException {
    if (_responsibilities.remove(id) == null) {
      throw new SpeciesNotFoundException(id);
    }
  }

  /**
   * This method adds a vaccine register to this vet.
   * 
   * @param vaccineReg The vaccine registry to be added.
   * 
   * @see #vaccinate(Animal, Vaccine)
   * @see VaccineRegistry
   */
  private void addVaccineRegistry(VaccineRegistry vaccineRegistry) {
    _vaccineRegistry.add(vaccineRegistry);
    vaccineRegistry.animal().addVaccineRegistration(vaccineRegistry);
  }

  /*
   * <------------------------ Others ------------------------>
   */

  /**
   * Calculates the satisfaction of this vet using its formula.
   * 
   * @return the satisfaction of this vet
   * 
   * @see Worker#satisfaction()
   */
  float satisfaction() {
    return _vetSatisfactionFormula.satisfaction(this);
  }

  /**
   * Registers the vaccination of a given animal with a given vaccine by this vet.
   * 
   * @param animal The animal that was vaccinated.
   * @param vaccine The vaccine that was applied.
   * 
   * @return the vaccine registry
   * 
   * @throws WorkerNotAuthorizedException If the worker does not have the species of the animal as a
   *         responsibility
   * 
   * @see #calculateVaccineDamage(Animal, Vaccine)
   * @see #addVaccineRegistry(VaccineRegistry)
   * @see Animal
   * @see Vaccine
   * @see VaccineRegistry
   */
  VaccineRegistry vaccinate(Animal animal, Vaccine vaccine) throws WorkerNotAuthorizedException {
    if (!_responsibilities.containsValue(animal.species())) {
      throw new WorkerNotAuthorizedException(id(), animal.species().id());
    }

    VaccineDamage vaccineDamage = calculateVaccineDamage(animal, vaccine);
    VaccineRegistry vaccineRegistry = new VaccineRegistry(vaccine, this, animal, vaccineDamage);
    addVaccineRegistry(vaccineRegistry);
    return vaccineRegistry;
  }

  /**
   * Counts the number of characters in common.
   * 
   * <p>
   * This method counts the number of characters in common between the name of the species and the
   * name of the species to which the vaccine is safe to be applied.
   * 
   * @param animalSpeciesName A hashmap of the name of a species to which it is safe to apply the
   *        vaccine
   * @param speciesName An array of the name of the animal to which the vaccine was applied.
   * 
   * @return the count of characters in common
   * 
   * @see #vaccinate(Animal, Vaccine)
   */
  private int countSameChars(HashMap<Character, Integer> animalSpeciesName, char[] speciesName) {
    int count = 0;

    for (char speciesChar : speciesName) {
      if (animalSpeciesName.containsKey(speciesChar) && animalSpeciesName.get(speciesChar) > 0) {
        count++;
        animalSpeciesName.put(speciesChar, animalSpeciesName.get(speciesChar) - 1);
      }
    }

    return count;
  }

  /**
   * Calculates the vaccine damage dealt by this vet to the given animal by the given vaccine.
   * 
   * @param animal The animal that was vaccinated.
   * @param vaccine The vaccine that was applied.
   * 
   * @return the vaccine damage dealt
   * 
   * @see #vaccinate(Animal, Vaccine)
   * @see Animal
   * @see Vaccine
   * @see VaccineDamage
   */
  private VaccineDamage calculateVaccineDamage(Animal animal, Vaccine vaccine) {

    // Early Check for correct Vaccines
    if (vaccine.species().contains(animal.species())) {
      return VaccineDamage.NORMAL;
    }

    int damage = 0;
    char[] animalSpeciesName = animal.species().name().toCharArray();
    HashMap<Character, Integer> animalSpeciesNameCharCount = new HashMap<Character, Integer>();

    // Count the number of each character in the animal species name
    for (char speciesChar : animalSpeciesName) {
      animalSpeciesNameCharCount.put(speciesChar,
          animalSpeciesNameCharCount.getOrDefault(speciesChar, 0) + 1);
    }

    // Calculate the max damage
    for (Species species : vaccine.species()) {
      char[] vaccineSpeciesName = species.name().toCharArray();
      int tempDamage = Math.max(vaccineSpeciesName.length, animalSpeciesName.length)
          - countSameChars(animalSpeciesNameCharCount, vaccineSpeciesName);
      damage = Math.max(tempDamage, damage);
    }

    // Convert damage to Enum
    switch (damage) {
      case 0:
        return VaccineDamage.CONFUSION;
      case 1:
      case 2:
      case 3:
      case 4:
        return VaccineDamage.ACCIDENT;
      default:
        return VaccineDamage.ERROR;
    }
  }

  /**
   * Returns a String representation of this vet.
   * 
   * <p>
   * This method follows the format:
   * <p>
   * VET|id|nome|idSpecies
   * <p>
   * If the vet does not have any species as a responsibility, the format is:
   * <p>
   * VET|id|nome
   * 
   * @return the String representation of this vet
   * 
   * @see Object#toString()
   * @see Worker#toString()
   * @see Species
   */
  @Override
  public String toString() {
    String responsibilities = "";

    if (!_responsibilities.isEmpty()) {
      responsibilities = "|" + String.join(",",
          _responsibilities.values().stream().map(Species::id).sorted().toList());
    }

    return String.format("VET|%s|%s%s", id(), name(), responsibilities.toString());
  }
}
