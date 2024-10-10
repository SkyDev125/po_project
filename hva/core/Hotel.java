package hva.core;

import hva.core.enumerator.Influence;
import hva.core.enumerator.SeasonType;
import hva.core.enumerator.VaccineDamage;

import hva.core.exception.AnimalNotFoundException;
import hva.core.exception.DuplicateAnimalException;
import hva.core.exception.DuplicateHabitatException;
import hva.core.exception.DuplicateSpeciesException;
import hva.core.exception.DuplicateTreeException;
import hva.core.exception.DuplicateVaccineException;
import hva.core.exception.DuplicateWorkerException;
import hva.core.exception.HabitatNotFoundException;
import hva.core.exception.ResponsibilityNotFoundException;
import hva.core.exception.SpeciesNotFoundException;
import hva.core.exception.TreeNotFoundException;
import hva.core.exception.UnrecognizedEntryException;
import hva.core.exception.UnrecognizedTreeTypeException;
import hva.core.exception.UnrecognizedWorkerTypeException;
import hva.core.exception.VaccineNotFoundException;
import hva.core.exception.WorkerNotAuthorizedException;
import hva.core.exception.WorkerNotFoundException;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Class representing the zoo hotel of this application.
 */
public class Hotel implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  private SeasonType _season = SeasonType.SPRING;
  private final Map<String, Species> _species = new CaseInsensitiveHashMap<Species>();
  private final Map<String, Vaccine> _vaccines = new CaseInsensitiveHashMap<Vaccine>();
  private final ArrayList<VaccineRegistry> _vaccineRegistry = new ArrayList<VaccineRegistry>();
  private final Map<String, Animal> _animals = new CaseInsensitiveHashMap<Animal>();
  private final Map<String, Habitat> _habitats = new CaseInsensitiveHashMap<Habitat>();
  private final Map<String, Tree> _trees = new CaseInsensitiveHashMap<Tree>();
  private final Map<String, Worker> _workers = new CaseInsensitiveHashMap<Worker>();

  /*
   * <------------------------ Gets ------------------------>
   */

  /**
   * Retrieves all the animals in the hotel.
   * 
   * <p>
   * This method provides a way to access the collection of {@link Animals} without allowing
   * modifications to the underlying collection. The returned collection is a read-only view, and
   * any attempts to modify it will result in an {@code UnsupportedOperationException}.
   * 
   * @return an unmodifiable {@link Collection} of the animals.
   */
  public Collection<Animal> animals() {
    return Collections.unmodifiableCollection(_animals.values());
  }

  /**
   * Retrieves all the species in the hotel.
   * 
   * <p>
   * This method provides a way to access the collection of {@link Species} without allowing
   * modifications to the underlying collection. The returned collection is a read-only view, and
   * any attempts to modify it will result in an {@code UnsupportedOperationException}.
   * 
   * @return an unmodifiable {@link Collection} of the species.
   */
  public Collection<Worker> workers() {
    return Collections.unmodifiableCollection(_workers.values());
  }

  /**
   * Retrieves all the habitats in the hotel.
   * 
   * <p>
   * This method provides a way to access the collection of {@link Habitat} without allowing
   * modifications to the underlying collection. The returned collection is a read-only view, and
   * any attempts to modify it will result in an {@code UnsupportedOperationException}.
   * 
   * @return an unmodifiable {@link Collection} of the habitats.
   */
  public Collection<Habitat> habitats() {
    return Collections.unmodifiableCollection(_habitats.values());
  }

  /**
   * Retrieves all the vaccines in the hotel.
   * 
   * <p>
   * This method provides a way to access the collection of {@link Vaccine} without allowing
   * modifications to the underlying collection. The returned collection is a read-only view, and
   * any attempts to modify it will result in an {@code UnsupportedOperationException}.
   * 
   * @return an unmodifiable {@link Collection} of the vaccines.
   */
  public Collection<Vaccine> vaccines() {
    return Collections.unmodifiableCollection(_vaccines.values());
  }

  /**
   * Retrieves all the vaccine registries in the hotel.
   * 
   * <p>
   * This method provides a way to access the collection of {@link VaccineRegistry} without allowing
   * modifications to the underlying collection. The returned collection is a read-only view, and
   * any attempts to modify it will result in an {@code UnsupportedOperationException}.
   * 
   * @return an unmodifiable {@link Collection} of the vaccine registries.
   */
  public List<VaccineRegistry> vaccineRegistry() {
    return Collections.unmodifiableList(_vaccineRegistry);
  }

  /**
   * Returns the current season of the hotel.
   * 
   * <p>
   * The season of the hotel is a value of the {@link SeasonType} enumeration. Used mainly for the
   * growth of {@link Tree}.
   * 
   * @return the current season of the hotel.
   */
  public SeasonType season() {
    return _season;
  }

  /*
   * <------------------------ Sets ------------------------>
   */

  /**
   * Adds a new animal to the hotel.
   * 
   * <p>
   * This method creates a new {@link Animal} with the given parameters and adds it to the hotel.
   * The animal is also added to the corresponding {@link Species} and {@link Habitat}.
   * 
   * @param idAnimal the identifier of the animal.
   * @param name the name of the animal.
   * @param idSpecies the identifier of the species of the animal.
   * @param idHabitat the identifier of the habitat of the animal.
   * 
   * @return the created animal.
   * 
   * @throws DuplicateAnimalException if an animal with the same identifier already exists.
   * @throws SpeciesNotFoundException if the species with the given identifier does not exist.
   * @throws HabitatNotFoundException if the habitat with the given identifier does not exist.
   */
  public Animal addAnimal(String idAnimal, String name, String idSpecies, String idHabitat)
      throws DuplicateAnimalException, SpeciesNotFoundException, HabitatNotFoundException {

    // Exception Checks
    if (animalExists(idAnimal) != null) {
      throw new DuplicateAnimalException(idAnimal);
    }
    Species species = speciesExistsWithException(idSpecies);
    Habitat habitat = habitatExistsWithException(idHabitat);

    // Create and Add Animal
    Animal animal = new Animal(idAnimal, name, species, habitat);
    _animals.put(idAnimal, animal);
    species.addAnimal(animal);
    habitat.addAnimal(animal);
    return animal;
  }

  /**
   * Adds a new species to the hotel.
   * 
   * <p>
   * This method creates a new {@link Species} with the given parameters and adds it to the hotel.
   * 
   * @param idSpecies the identifier of the species.
   * @param name the name of the species.
   * 
   * @return the created species.
   * 
   * @throws DuplicateSpeciesException if a species with the same identifier already exists.
   */
  public Species addSpecies(String idSpecies, String name) throws DuplicateSpeciesException {

    // Exception Checks
    if (speciesExists(idSpecies) != null
        || _species.values().stream().anyMatch(species -> species.name().equalsIgnoreCase(name))) {
      throw new DuplicateSpeciesException(idSpecies);
    }

    // Create and Add Species
    Species species = new Species(idSpecies, name);
    _species.put(idSpecies, species);
    return species;
  }

  /**
   * Adds a new worker to the hotel.
   * 
   * <p>
   * This method creates a new {@link Worker} with the given parameters and adds it to the hotel.
   * 
   * @param idWorker the identifier of the worker.
   * @param name the name of the worker.
   * @param type the type of the worker.
   * 
   * @return the created worker.
   * 
   * @throws DuplicateWorkerException if a worker with the same identifier already exists.
   * @throws UnrecognizedWorkerTypeException if the worker type is not recognized.
   */
  public Worker addWorker(String idWorker, String name, String type)
      throws DuplicateWorkerException, UnrecognizedWorkerTypeException {

    // Exception Checks
    if (workerExists(idWorker) != null) {
      throw new DuplicateWorkerException(idWorker);
    }

    // Create and Add Worker
    Worker worker;
    switch (type) {
      case "VET":
        worker = new Vet(idWorker, name, this);
        break;
      case "TRT":
        worker = new CareTaker(idWorker, name, this);
        break;
      default:
        throw new UnrecognizedWorkerTypeException(type);
    }
    _workers.put(idWorker, worker);
    return worker;
  }

  /**
   * Adds a new habitat to the hotel.
   * 
   * <p>
   * This method creates a new {@link Habitat} with the given parameters and adds it to the hotel.
   * 
   * @param idHabitat the identifier of the habitat.
   * @param name the name of the habitat.
   * @param area the area of the habitat.
   * 
   * @return the created habitat.
   * 
   * @throws DuplicateHabitatException if a habitat with the same identifier already exists.
   */
  public Habitat addHabitat(String idHabitat, String name, int area)
      throws DuplicateHabitatException {

    // Exception Checks
    if (habitatExists(idHabitat) != null) {
      throw new DuplicateHabitatException(idHabitat);
    }

    // Create and Add Habitat
    Habitat habitat = new Habitat(idHabitat, name, area);
    _habitats.put(idHabitat, habitat);
    return habitat;
  }

  /**
   * Adds a new tree to the habitat.
   * 
   * <p>
   * This method creates a new {@link Tree} with the given parameters and adds it to the
   * {@link Habitat} with the given identifier.
   * 
   * @param idHabitat the identifier of the habitat.
   * @param idTree the identifier of the tree.
   * @param name the name of the tree.
   * @param age the age of the tree.
   * @param cleanDiff the cleaning difficulty of the tree.
   * @param Type the type of the tree. "CADUCA" for {@link Deciduos} or "PERENE" {@link Evergreen}.
   * 
   * @return the created tree.
   * 
   * @throws HabitatNotFoundException if the habitat with the given identifier does not exist.
   * @throws UnrecognizedTreeTypeException if the tree type is not recognized.
   * @throws DuplicateTreeException if a tree with the same identifier already exists.
   */
  public Tree addTreeToHabitat(String idHabitat, String idTree, String name, int age, int cleanDiff,
      String Type)
      throws HabitatNotFoundException, UnrecognizedTreeTypeException, DuplicateTreeException {

    // Exception Checks
    Habitat habitat = habitatExistsWithException(idHabitat);

    Tree tree = addTree(idTree, name, age, cleanDiff, Type);
    habitat.addTree(tree);
    return tree;
  }

  /**
   * Adds a new tree to the hotel.
   * 
   * <p>
   * This method creates a new {@link Tree} with the given parameters and adds it to the hotel
   * directly.
   * 
   * @param idTree the identifier of the tree.
   * @param name the name of the tree.
   * @param age the age of the tree.
   * @param cleanDiff the cleaning difficulty of the tree.
   * @param Type the type of the tree. "CADUCA" for {@link Deciduos} or "PERENE" {@link Evergreen}.
   * 
   * @return the created tree.
   * 
   * @throws DuplicateTreeException if a tree with the same identifier already exists.
   * @throws UnrecognizedTreeTypeException if the tree type is not recognized.
   */
  public Tree addTree(String idTree, String name, int age, int cleanDiff, String Type)
      throws DuplicateTreeException, UnrecognizedTreeTypeException {

    // Exception Checks
    if (treeExists(idTree) != null) {
      throw new DuplicateTreeException(idTree);
    }

    Tree tree;
    switch (Type) {
      case "PERENE":
        tree = new Deciduos(idTree, name, age, cleanDiff, this);
        break;
      case "CADUCA":
        tree = new Evergreen(idTree, name, age, cleanDiff, this);
        break;
      default:
        throw new UnrecognizedTreeTypeException(Type);
    }

    _trees.put(idTree, tree);
    return tree;
  }

  /**
   * Adds a new vaccine to the hotel.
   * 
   * <p>
   * This method creates a new {@link Vaccine} with the given parameters and adds it to the hotel.
   * This vaccine will support the {@link Species} with the given identifiers.
   * 
   * @param idVaccine the identifier of the vaccine.
   * @param name the name of the vaccine.
   * @param idSpecies the identifiers of the species supported by the vaccine.
   * 
   * @return the created vaccine.
   * 
   * @throws DuplicateVaccineException if a vaccine with the same identifier already exists.
   * @throws SpeciesNotFoundException if a species with the given identifier does not exist.
   */
  public Vaccine addVaccine(String idVaccine, String name, String idSpecies)
      throws DuplicateVaccineException, SpeciesNotFoundException {

    // Exception Checks
    if (vaccineExists(idVaccine) != null) {
      throw new DuplicateVaccineException(idVaccine);
    }

    String[] idsSpecies = idSpecies.split("\\s*,\\s*");
    ArrayList<Species> allSpecies = new ArrayList<Species>();

    // Add Check due to parser allowing for vaccines with no species.
    if (!idSpecies.isBlank()) {
      for (String id : idsSpecies) {
        Species species = speciesExistsWithException(id);
        allSpecies.add(species);
      }
    }

    // Create and Add Vaccine
    Vaccine vaccine = new Vaccine(idVaccine, name, allSpecies);
    _vaccines.put(idVaccine, vaccine);
    return vaccine;
  }

  /*
   * <------------------------ Others ------------------------>
   */

  /**
   * Progress the season of the hotel.
   * 
   * <p>
   * This method progresses the {@link SeasonType} of the hotel to the next one. It also triggers
   * the {@link Tree}'s in the hotel to grow if possible.
   * 
   * @return the new season of the hotel.
   */
  public SeasonType progressSeason() {
    _season = _season.next();
    for (Tree tree : _trees.values()) {
      tree.grow();
    }
    return _season;
  }

  /**
   * Calculates the satisfaction of the hotel.
   * 
   * <p>
   * This method calculates the satisfaction of the hotel based on the satisfaction of all
   * {@link Animal}'s and {@link Worker}'s in the hotel.
   * 
   * @return the satisfaction of the hotel.
   */
  public float satisfaction() {
    Collection<Animal> animals = _animals.values();
    Collection<Worker> workers = _workers.values();
    float totalSatisfaction = 0;

    for (Animal animal : animals) {
      totalSatisfaction += animal.satisfaction();
    }

    for (Worker worker : workers) {
      totalSatisfaction += worker.satisfaction();
    }

    return totalSatisfaction;
  }

  /**
   * Transfers an animal to a different Habitat.
   * 
   * <p>
   * This method transfers the {@link Animal} with the given identifier to the {@link Habitat} with
   * the given identifier.
   * 
   * @param idAnimal the identifier of the animal.
   * @param idHabitat the identifier of the habitat.
   * 
   * @throws AnimalNotFoundException if an animal with the given identifier does not exist.
   * @throws HabitatNotFoundException if a habitat with the given identifier does not exist.
   */
  public void transferAnimal(String idAnimal, String idHabitat)
      throws AnimalNotFoundException, HabitatNotFoundException {
    Animal animal = animalExistsWithException(idAnimal);
    Habitat habitat = habitatExistsWithException(idHabitat);
    animal.transferAnimal(habitat);
  }

  /**
   * Calculates the satisfaction of an animal.
   * 
   * <p>
   * This method calculates the satisfaction of the {@link Animal} with the given identifier.
   * 
   * @param id the identifier of the animal.
   * 
   * @return the satisfaction of the animal.
   * 
   * @throws AnimalNotFoundException if an animal with the given identifier does not exist.
   */
  public float animalSatisfaction(String id) throws AnimalNotFoundException {
    return animalExistsWithException(id).satisfaction();
  }

  /**
   * Adds a responsibility to a worker.
   * 
   * <p>
   * This method adds a responsibility with the given identifier to the {@link Worker} with the
   * given identifier.
   * 
   * @param idWorker the identifier of the worker.
   * @param idResponsibility the identifier of the responsibility.
   * 
   * @throws WorkerNotFoundException if a worker with the given identifier does not exist.
   * @throws ResponsibilityNotFoundException if a responsibility with the given identifier matching
   *         the worker type does not exist.
   */
  public void addResponsibilityToWorker(String idWorker, String idResponsibility)
      throws WorkerNotFoundException, ResponsibilityNotFoundException {
    Worker worker = workerExistsWithException(idWorker);
    try {
      worker.addResponsibility(idResponsibility);
    } catch (HabitatNotFoundException | SpeciesNotFoundException e) {
      throw new ResponsibilityNotFoundException(idWorker, idResponsibility);
    }
  }

  /**
   * Removes a responsibility from a worker.
   * 
   * <p>
   * This method removes a responsibility with the given identifier from the {@link Worker} with the
   * given identifier.
   * 
   * @param idWorker the identifier of the worker.
   * @param idResponsibility the identifier of the responsibility.
   * 
   * @throws WorkerNotFoundException if a worker with the given identifier does not exist.
   * @throws ResponsibilityNotFoundException if a responsibility with the given identifier does not
   *         exist in the worker's current responsibilities.
   */
  public void removeResponsibilityFromWorker(String idWorker, String idResponsibility)
      throws WorkerNotFoundException, ResponsibilityNotFoundException {
    Worker worker = workerExistsWithException(idWorker);
    try {
      worker.removeResponsibility(idResponsibility);
    } catch (HabitatNotFoundException | SpeciesNotFoundException e) {
      throw new ResponsibilityNotFoundException(idWorker, idResponsibility);
    }
  }

  /**
   * Calculates the satisfaction of a worker.
   * 
   * <p>
   * This method calculates the satisfaction of the {@link Worker} with the given identifier.
   * 
   * @param id the identifier of the worker.
   * 
   * @return the satisfaction of the worker.
   * 
   * @throws WorkerNotFoundException if a worker with the given identifier does not exist.
   */
  public float workerSatisfaction(String id) throws WorkerNotFoundException {
    return workerExistsWithException(id).satisfaction();
  }

  /**
   * Changes the area of a habitat.
   * 
   * <p>
   * This method changes the area of the {@link Habitat} with the given identifier.
   * 
   * @param idHabitat the identifier of the habitat.
   * @param area the new area of the habitat.
   * 
   * @throws HabitatNotFoundException if a habitat with the given identifier does not exist.
   */
  public void changeHabitatArea(String idHabitat, int area) throws HabitatNotFoundException {
    habitatExistsWithException(idHabitat).changeArea(area);
  }


  /**
   * Changes the suitability of a species for a habitat.
   * 
   * <p>
   * This method changes the suitability of the {@link Species} with the given identifier for the
   * {@link Habitat} with the given identifier.
   * 
   * @param idHabitat the identifier of the habitat.
   * @param idSpecies the identifier of the species.
   * @param influence the new suitability of the species for the habitat.
   * 
   * @throws HabitatNotFoundException if a habitat with the given identifier does not exist.
   * @throws SpeciesNotFoundException if a species with the given identifier does not exist.
   */
  public void changeHabitatSuitability(String idHabitat, String idSpecies, Influence influence)
      throws HabitatNotFoundException, SpeciesNotFoundException {
    Habitat habitat = habitatExistsWithException(idHabitat);
    Species species = speciesExistsWithException(idSpecies);

    // Return early cause theres no need to save neutral influences
    if (influence == Influence.NEU) {
      return;
    }

    habitat.changeSuitability(species, influence);
  }

  /**
   * Returns the trees of a habitat.
   * 
   * <p>
   * This method returns an unmodifiable view of the collection of {@link Tree}'s of the
   * {@link Habitat} with the given identifier.
   * 
   * @param idHabitat the identifier of the habitat.
   * 
   * @return an unmodifiable view of the collection of trees of the habitat.
   * 
   * @throws HabitatNotFoundException if a habitat with the given identifier does not exist.
   */
  public Collection<Tree> habitatTrees(String idHabitat) throws HabitatNotFoundException {
    return habitatExistsWithException(idHabitat).trees();
  }

  /**
   * Vaccinates an animal.
   * 
   * <p>
   * This method vaccinates the {@link Animal} with the given identifier with the {@link Vaccine}
   * with the given identifier by the {@link Vet} with the given identifier.
   * 
   * @param idAnimal the identifier of the animal.
   * @param idVaccine the identifier of the vaccine.
   * @param idVet the identifier of the vet.
   * 
   * @return the created vaccine registry.
   * 
   * @throws AnimalNotFoundException if an animal with the given identifier does not exist.
   * @throws VaccineNotFoundException if a vaccine with the given identifier does not exist.
   * @throws WorkerNotFoundException if a worker with the given identifier does not exist and isn't
   *         a vet.
   * @throws WorkerNotAuthorizedException if the worker with the given identifier is not a
   *         {@link Vet}.
   */
  public VaccineRegistry vaccinateAnimal(String idAnimal, String idVaccine, String idVet)
      throws AnimalNotFoundException, VaccineNotFoundException, WorkerNotFoundException,
      WorkerNotAuthorizedException {

    // Exception Checks
    Animal animal = animalExistsWithException(idAnimal);
    Vaccine vaccine = vaccineExistsWithException(idVaccine);
    Worker worker = workerExistsWithException(idVet);
    if (!(worker instanceof Vet)) {
      throw new WorkerNotFoundException(idVet);
    }

    // Vaccinate Animal
    VaccineRegistry vaccineRegistry = ((Vet) worker).vaccinate(animal, vaccine);
    _vaccineRegistry.add(vaccineRegistry);
    return vaccineRegistry;
  }

  /**
   * Returns the animals of a habitat.
   * 
   * <p>
   * This method returns an unmodifiable view of the collection of {@link Animal}'s of the
   * {@link Habitat} with the given identifier.
   * 
   * @param idHabitat the identifier of the habitat.
   * 
   * @return an unmodifiable view of the collection of animals of the habitat.
   * 
   * @throws HabitatNotFoundException if a habitat with the given identifier does not exist.
   */
  public Collection<Animal> habitatAnimals(String idHabitat) throws HabitatNotFoundException {
    return habitatExistsWithException(idHabitat).animals();
  }

  /**
   * Returns the Vaccination Registries of an animal.
   * 
   * <p>
   * This method returns an unmodifiable view of the collection of {@link VaccineRegistry}'s of the
   * {@link Animal} with the given identifier.
   * 
   * @param idAnimal the identifier of the animal.
   * 
   * @return an unmodifiable view of the collection of vaccination registries of the animal.
   * 
   * @throws AnimalNotFoundException if an animal with the given identifier does not exist.
   */
  public List<VaccineRegistry> animalVaccinations(String idAnimal) throws AnimalNotFoundException {
    return animalExistsWithException(idAnimal).vaccineRegistry();
  }

  /**
   * Returns the Vaccination Registries of a vet.
   * 
   * <p>
   * This method returns an unmodifiable view of the collection of {@link VaccineRegistry}'s of the
   * {@link Vet} with the given identifier.
   * 
   * @param idVet the identifier of the vet.
   * 
   * @return an unmodifiable view of the collection of vaccination registries of the vet.
   * 
   * @throws WorkerNotFoundException if a worker with the given identifier does not exist and isnt a
   *         vet.
   */
  public List<VaccineRegistry> vetVaccinations(String idVet) throws WorkerNotFoundException {
    Worker worker = workerExistsWithException(idVet);
    if (!(worker instanceof Vet)) {
      throw new WorkerNotFoundException(idVet);
    }
    return ((Vet) worker).vaccineRegistry();
  }

  /**
   * Filters the wrong vaccinations.
   * 
   * <p>
   * This method filters the {@link VaccineRegistry}'s of the hotel and returns an unmodifiable view
   * of the collection of wrong vaccinations.
   * 
   * @return an unmodifiable view of the collection of wrong vaccinations.
   */
  public List<VaccineRegistry> filterWrongVaccinations() {
    ArrayList<VaccineRegistry> wrongVaccinations = new ArrayList<VaccineRegistry>();

    for (VaccineRegistry vaccineRegistry : _vaccineRegistry) {
      if (vaccineRegistry.vaccineDamage() != VaccineDamage.NORMAL) {
        wrongVaccinations.add(vaccineRegistry);
      }
    }

    return Collections.unmodifiableList(wrongVaccinations);
  }

  /*
   * <------------------------ Finds ------------------------>
   */

  /**
   * Finds an animal with the given identifier.
   * 
   * <p>
   * This method returns the {@link Animal} with the given identifier or {@code null} if it does not
   * exist.
   * 
   * @param id the identifier of the animal.
   * 
   * @return the animal with the given identifier or {@code null} if it does not exist.
   */
  Animal animalExists(String id) {
    return _animals.get(id);
  }

  /**
   * Finds an animal with the given identifier and throws an exception if it does not exist.
   * 
   * <p>
   * This method returns the {@link Animal} with the given identifier or throws an exception if it
   * does not exist.
   * 
   * @param id the identifier of the animal.
   * 
   * @return the animal with the given identifier.
   * 
   * @throws AnimalNotFoundException if an animal with the given identifier does not exist.
   */
  Animal animalExistsWithException(String id) throws AnimalNotFoundException {
    Animal animal = animalExists(id);
    if (animal == null) {
      throw new AnimalNotFoundException(id);
    }
    return animal;
  }

  /**
   * Finds a species with the given identifier.
   * 
   * <p>
   * This method returns the {@link Species} with the given identifier or {@code null} if it does
   * not exist.
   * 
   * @param id the identifier of the species.
   * 
   * @return the species with the given identifier or {@code null} if it does not exist.
   */
  Species speciesExists(String id) {
    return _species.get(id);
  }

  /**
   * Finds a species with the given identifier and throws an exception if it does not exist.
   * 
   * <p>
   * This method returns the {@link Species} with the given identifier or throws an exception if it
   * does not exist.
   * 
   * @param id the identifier of the species.
   * 
   * @return the species with the given identifier.
   * 
   * @throws SpeciesNotFoundException if a species with the given identifier does not exist.
   */
  Species speciesExistsWithException(String id) throws SpeciesNotFoundException {
    Species species = speciesExists(id);
    if (species == null) {
      throw new SpeciesNotFoundException(id);
    }
    return species;
  }

  /**
   * Finds a worker with the given identifier.
   * 
   * <p>
   * This method returns the {@link Worker} with the given identifier or {@code null} if it does not
   * exist.
   * 
   * @param id the identifier of the worker.
   * 
   * @return the worker with the given identifier or {@code null} if it does not exist.
   * 
   * @throws WorkerNotFoundException if a worker with the given identifier does not exist.
   */
  Worker workerExists(String id) {
    return _workers.get(id);
  }

  /**
   * Finds a worker with the given identifier and throws an exception if it does not exist.
   * 
   * <p>
   * This method returns the {@link Worker} with the given identifier or throws an exception if it
   * does not exist.
   * 
   * @param id the identifier of the worker.
   * 
   * @return the worker with the given identifier.
   * 
   * @throws WorkerNotFoundException if a worker with the given identifier does not exist.
   */
  Worker workerExistsWithException(String id) throws WorkerNotFoundException {
    Worker worker = workerExists(id);
    if (worker == null) {
      throw new WorkerNotFoundException(id);
    }
    return worker;
  }

  /**
   * Finds a habitat with the given identifier.
   * 
   * <p>
   * This method returns the {@link Habitat} with the given identifier or {@code null} if it does
   * not exist.
   * 
   * @param id the identifier of the habitat.
   * 
   * @return the habitat with the given identifier or {@code null} if it does not exist.
   */
  Habitat habitatExists(String id) {
    return _habitats.get(id);
  }

  /**
   * Finds a habitat with the given identifier and throws an exception if it does not exist.
   * 
   * <p>
   * This method returns the {@link Habitat} with the given identifier or throws an exception if it
   * does not exist.
   * 
   * @param id the identifier of the habitat.
   * 
   * @return the habitat with the given identifier.
   * 
   * @throws HabitatNotFoundException if a habitat with the given identifier does not exist.
   */
  Habitat habitatExistsWithException(String id) throws HabitatNotFoundException {
    Habitat habitat = habitatExists(id);
    if (habitat == null) {
      throw new HabitatNotFoundException(id);
    }
    return habitat;
  }

  /**
   * Finds a tree with the given identifier.
   * 
   * <p>
   * This method returns the {@link Tree} with the given identifier or {@code null} if it does not
   * exist.
   * 
   * @param id the identifier of the tree.
   * 
   * @return the tree with the given identifier or {@code null} if it does not exist.
   */
  Tree treeExists(String id) {
    return _trees.get(id);
  }

  /**
   * Finds a tree with the given identifier and throws an exception if it does not exist.
   * 
   * <p>
   * This method returns the {@link Tree} with the given identifier or throws an exception if it
   * does not exist.
   * 
   * @param id the identifier of the tree.
   * 
   * @return the tree with the given identifier.
   * 
   * @throws TreeNotFoundException if a tree with the given identifier does not exist.
   */
  Tree treeExistsWithException(String id) throws TreeNotFoundException {
    Tree tree = treeExists(id);
    if (tree == null) {
      throw new TreeNotFoundException(id);
    }
    return tree;
  }

  /**
   * Finds a vaccine with the given identifier.
   * 
   * <p>
   * This method returns the {@link Vaccine} with the given identifier or {@code null} if it does
   * not exist.
   * 
   * @param id the identifier of the vaccine.
   * 
   * @return the vaccine with the given identifier or {@code null} if it does not exist.
   */
  Vaccine vaccineExists(String id) {
    return _vaccines.get(id);
  }

  /**
   * Finds a vaccine with the given identifier and throws an exception if it does not exist.
   * 
   * <p>
   * This method returns the {@link Vaccine} with the given identifier or throws an exception if it
   * does not exist.
   * 
   * @param id the identifier of the vaccine.
   * 
   * @return the vaccine with the given identifier.
   * 
   * @throws VaccineNotFoundException if a vaccine with the given identifier does not exist.
   */
  Vaccine vaccineExistsWithException(String id) throws VaccineNotFoundException {
    Vaccine vaccine = vaccineExists(id);
    if (vaccine == null) {
      throw new VaccineNotFoundException(id);
    }
    return vaccine;
  }

  /**
   * Read text input file and create corresponding domain entities.
   * 
   * @param filename name of the text input file
   * 
   * @throws UnrecognizedEntryException if some entry is not correct
   * @throws IOException if there is an IO erro while processing the text file
   **/
  void importFile(String filename) throws UnrecognizedEntryException, IOException {
    Parser parser = new Parser(this);
    parser.parseFile(filename);
  }
}
