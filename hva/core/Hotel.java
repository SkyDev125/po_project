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
 * 
 * <p>
 * The hotel knows the current season and keeps record of its {@link Species}, {@link Vaccine}s,
 * {@link VaccineRegistry}, {@link Animal}s, {@link Habitat}s, {@link Tree}s and {@link Worker}s.
 * <p>
 * The hotel is responsible for adding, removing and managing all that it keeps record.
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
   * Retrieves all the animals in this hotel.
   * 
   * <p>
   * This method provides a way to access the collection of animals without allowing modifications
   * to the underlying collection. The returned collection is a read-only view, and any attempts to
   * modify it will result in an {@code UnsupportedOperationException}.
   * 
   * @return an unmodifiable collection of the animals
   * 
   * @see Collections#unmodifiableCollection(Collection)
   * @see Animal
   */
  public Collection<Animal> animals() {
    return Collections.unmodifiableCollection(_animals.values());
  }

  /**
   * Retrieves all the species in this hotel
   * 
   * <p>
   * This method provides a way to access the collection of species without allowing modifications
   * to the underlying collection. The returned collection is a read-only view, and any attempts to
   * modify it will result in an {@code UnsupportedOperationException}.
   * 
   * @return an unmodifiable collection of the species
   * 
   * @see Collections#unmodifiableCollection(Collection)
   * @see Worker
   */
  public Collection<Worker> workers() {
    return Collections.unmodifiableCollection(_workers.values());
  }

  /**
   * Retrieves all the habitats in this hotel.
   * 
   * <p>
   * This method provides a way to access the collection of habitats without allowing modifications
   * to the underlying collection. The returned collection is a read-only view, and any attempts to
   * modify it will result in an {@code UnsupportedOperationException}.
   * 
   * @return an unmodifiable collection of the habitats
   * 
   * @see Collections#unmodifiableCollection(Collection)
   * @see Habitat
   */
  public Collection<Habitat> habitats() {
    return Collections.unmodifiableCollection(_habitats.values());
  }

  /**
   * Retrieves all the vaccines in this hotel.
   * 
   * <p>
   * This method provides a way to access the collection of vaccines without allowing modifications
   * to the underlying collection. The returned collection is a read-only view, and any attempts to
   * modify it will result in an {@code UnsupportedOperationException}.
   * 
   * @return an unmodifiable collection of the vaccines
   * 
   * @see Collections#unmodifiableCollection(Collection)
   * @see Vaccine
   */
  public Collection<Vaccine> vaccines() {
    return Collections.unmodifiableCollection(_vaccines.values());
  }

  /**
   * Retrieves all the vaccine registries in this hotel.
   * 
   * <p>
   * This method provides a way to access the collection of vaccine registries without allowing
   * modifications to the underlying collection. The returned collection is a read-only view, and
   * any attempts to modify it will result in an {@code UnsupportedOperationException}.
   * 
   * @return an unmodifiable collection of the vaccine registries
   * 
   * @see Collections#unmodifiableCollection(Collection)
   * @see VaccineRegistry
   */
  public List<VaccineRegistry> vaccineRegistry() {
    return Collections.unmodifiableList(_vaccineRegistry);
  }

  /**
   * Returns the current season of this hotel.
   * 
   * <p>
   * The season of the hotel is a value of the SeasonType <code>enumeration</code> by which the
   * state and age of the trees progresses.
   * 
   * @return the current season of the hotel.
   * 
   * @see SeasonType
   * @see Tree
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
   * This method creates a new animal with the given parameters and adds it to the hotel. The animal
   * is also added to the corresponding species and habitat.
   * 
   * @param idAnimal the identifier of the animal
   * @param name the name of the animal
   * @param idSpecies the identifier of the species of the animal
   * @param idHabitat the identifier of the habitat of the animal
   * 
   * @return the created animal
   * 
   * @throws DuplicateAnimalException If an animal with the same identifier already exists.
   * @throws SpeciesNotFoundException If the species with the given identifier does not exist.
   * @throws HabitatNotFoundException If the habitat with the given identifier does not exist.
   * 
   * @see Animal
   * @see Species
   * @see Habitat
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
   * This method creates a new species with the given parameters and adds it to the hotel.
   * 
   * @param idSpecies the identifier of the species
   * @param name the name of the species
   * 
   * @return the created species
   * 
   * @throws DuplicateSpeciesException If a species with the same identifier already exists.
   * 
   * @see Species
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
   * This method creates a new worker with the given parameters and adds it to the hotel.
   * 
   * @param idWorker the identifier of the worker
   * @param name the name of the worker
   * @param type the type of the worker
   * 
   * @return the created worker
   * 
   * @throws DuplicateWorkerException If a worker with the same identifier already exists.
   * @throws UnrecognizedWorkerTypeException If the worker type is not recognized.
   * 
   * @see Worker
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
   * This method creates a new habitat with the given parameters and adds it to the hotel.
   * 
   * @param idHabitat the identifier of the habitat
   * @param name the name of the habitat
   * @param area the area of the habitat
   * 
   * @return the created habitat
   * 
   * @throws DuplicateHabitatException If a habitat with the same identifier already exists.
   * 
   * @see Habitat
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
   * This method creates a new tree with the given parameters and adds it to the habitat with the
   * given identifier.
   * 
   * @param idHabitat the identifier of the habitat
   * @param idTree the identifier of the tree
   * @param name the name of the tree
   * @param age the age of the tree
   * @param cleanDiff the cleaning difficulty of the tree
   * @param Type the type of the tree. "CADUCA" for Deciduos or "PERENE" Evergreen
   * 
   * @return the created tree
   * 
   * @throws HabitatNotFoundException If the habitat with the given identifier does not exist.
   * @throws UnrecognizedTreeTypeException If the tree type is not recognized.
   * @throws DuplicateTreeException If a tree with the same identifier already exists.
   * 
   * @see Tree
   * @see Deciduos
   * @see Evergreen
   * @see Worker
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
   * This method creates a new tree with the given parameters and adds it to the hotel directly.
   * 
   * @param idTree the identifier of the tree
   * @param name the name of the tree
   * @param age the age of the tree
   * @param cleanDiff the cleaning difficulty of the tree
   * @param Type the type of the tree. "CADUCA" for Deciduos or "PERENE" Evergreen
   * 
   * @return the created tree
   * 
   * @throws DuplicateTreeException If a tree with the same identifier already exists.
   * @throws UnrecognizedTreeTypeException If the tree type is not recognized.
   * 
   * @see Tree
   * @see Deciduos
   * @see Evergreen
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
   * This method creates a new vaccine with the given parameters and adds it to the hotel. This
   * vaccine will support the species with the given identifiers.
   * 
   * @param idVaccine the identifier of the vaccine
   * @param name the name of the vaccine
   * @param idSpecies the identifiers of the species supported by the vaccine
   * 
   * @return the created vaccine
   * 
   * @throws DuplicateVaccineException If a vaccine with the same identifier already exists.
   * @throws SpeciesNotFoundException If a species with the given identifier does not exist.
   * 
   * @see Vaccine
   * @see Species
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
   * This method progresses the season (SeasonType <code>enumeration</code>) of the hotel to the
   * next one. It also triggers the trees in the hotel to grow if possible.
   * 
   * @return the new season of the hotel
   * 
   * @see SeasonType
   * @see Tree
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
   * This method calculates the satisfaction of the hotel based on the satisfaction of all animals
   * and workers in the hotel.
   * 
   * @return the satisfaction of the hotel
   * 
   * @see Animal#satisfaction()
   * @see Worker#satisfaction()
   * @see Animal
   * @see Worker
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
   * This method transfers the animal with the given identifier to the habitat with the given
   * identifier.
   * 
   * @param idAnimal the identifier of the animal
   * @param idHabitat the identifier of the habitat
   * 
   * @throws AnimalNotFoundException If an animal with the given identifier does not exist.
   * @throws HabitatNotFoundException If a habitat with the given identifier does not exist.
   * 
   * @see Animal#transferAnimal(Habitat)
   * @see Animal
   * @see Habitat
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
   * This method calculates the satisfaction of the animal with the given identifier.
   * 
   * @param id the identifier of the animal
   * 
   * @return the satisfaction of the animal
   * 
   * @throws AnimalNotFoundException If an animal with the given identifier does not exist.
   * 
   * @see Animal
   */
  public float animalSatisfaction(String id) throws AnimalNotFoundException {
    return animalExistsWithException(id).satisfaction();
  }

  /**
   * Adds a responsibility to a worker.
   * 
   * <p>
   * This method adds a responsibility with the given identifier to the worker with the given
   * identifier.
   * 
   * @param idWorker the identifier of the worker
   * @param idResponsibility the identifier of the responsibility
   * 
   * @throws WorkerNotFoundException If a worker with the given identifier does not exist.
   * @throws ResponsibilityNotFoundException If a responsibility with the given identifier matching
   *         the worker type does not exist.
   * 
   * @see Worker
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
   * This method removes a responsibility with the given identifier from the worker with the given
   * identifier.
   * 
   * @param idWorker the identifier of the worker
   * @param idResponsibility the identifier of the responsibility
   * 
   * @throws WorkerNotFoundException If a worker with the given identifier does not exist.
   * @throws ResponsibilityNotFoundException If a responsibility with the given identifier does not
   *         exist in the worker's current responsibilities.
   * 
   * @see Worker
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
   * This method calculates the satisfaction of the worker with the given identifier.
   * 
   * @param id the identifier of the worker
   * 
   * @return the satisfaction of the worker
   * 
   * @throws WorkerNotFoundException If a worker with the given identifier does not exist.
   * 
   * @see Worker
   */
  public float workerSatisfaction(String id) throws WorkerNotFoundException {
    return workerExistsWithException(id).satisfaction();
  }

  /**
   * Changes the area of a habitat.
   * 
   * <p>
   * This method changes the area of the habitat with the given identifier.
   * 
   * @param idHabitat the identifier of the habitat
   * @param area the new area of the habitat
   * 
   * @throws HabitatNotFoundException If a habitat with the given identifier does not exist.
   * 
   * @see Habitat
   */
  public void changeHabitatArea(String idHabitat, int area) throws HabitatNotFoundException {
    habitatExistsWithException(idHabitat).changeArea(area);
  }

  /**
   * Changes the suitability of a species for a habitat.
   * 
   * <p>
   * This method changes the suitability of the species with the given identifier for the habitat
   * with the given identifier.
   * 
   * @param idHabitat the identifier of the habitat
   * @param idSpecies the identifier of the species
   * @param influence the new suitability of the species for the habitat
   * 
   * @throws HabitatNotFoundException If a habitat with the given identifier does not exist.
   * @throws SpeciesNotFoundException If a species with the given identifier does not exist.
   * 
   * @see Species
   * @see Habitat
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
   * This method returns an unmodifiable view of the collection of trees of the habitat with the
   * given identifier.
   * 
   * @param idHabitat the identifier of the habitat
   * 
   * @return an unmodifiable view of the collection of trees of the habitat
   * 
   * @throws HabitatNotFoundException If a habitat with the given identifier does not exist.
   * 
   * @see Tree
   * @see Habitat
   */
  public Collection<Tree> habitatTrees(String idHabitat) throws HabitatNotFoundException {
    return habitatExistsWithException(idHabitat).trees();
  }

  /**
   * Vaccinates an animal.
   * 
   * <p>
   * This method vaccinates the animal with the given identifier with the vaccine with the given
   * identifier by the vet with the given identifier.
   * 
   * @param idAnimal the identifier of the animal
   * @param idVaccine the identifier of the vaccine
   * @param idVet the identifier of the veterinay
   * 
   * @return the created vaccine registry
   * 
   * @throws AnimalNotFoundException If an animal with the given identifier does not exist.
   * @throws VaccineNotFoundException If a vaccine with the given identifier does not exist.
   * @throws WorkerNotFoundException If a worker with the given identifier does not exist and isn't
   *         a vet.
   * @throws WorkerNotAuthorizedException If the worker with the given identifier is not a vet.
   * 
   * @see Animal
   * @see Vaccine
   * @see Vet
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
   * This method returns an unmodifiable view of the collection of animals of the habitat with the
   * given identifier.
   * 
   * @param idHabitat the identifier of the habitat
   * 
   * @return An unmodifiable view of the collection of animals of the habitat.
   * 
   * @throws HabitatNotFoundException If a habitat with the given identifier does not exist.
   * 
   * @see Animal
   * @see Habitat
   */
  public Collection<Animal> habitatAnimals(String idHabitat) throws HabitatNotFoundException {
    return habitatExistsWithException(idHabitat).animals();
  }

  /**
   * Returns the Vaccination Registries of an animal.
   * 
   * <p>
   * This method returns an unmodifiable view of the collection of vaccine registries of the animal
   * with the given identifier.
   * 
   * @param idAnimal the identifier of the animal
   * 
   * @return An unmodifiable view of the collection of vaccination registries of the animal.
   * 
   * @throws AnimalNotFoundException If an animal with the given identifier does not exist.
   * 
   * @see VaccineRegistry
   * @see Animal
   */
  public List<VaccineRegistry> animalVaccinations(String idAnimal) throws AnimalNotFoundException {
    return animalExistsWithException(idAnimal).vaccineRegistry();
  }

  /**
   * Returns the Vaccination Registries of a vet.
   * 
   * <p>
   * This method returns an unmodifiable view of the collection of vaccine registries of the vet
   * with the given identifier.
   * 
   * @param idVet the identifier of the vet
   * 
   * @return An unmodifiable view of the collection of vaccination registries of the vet.
   * 
   * @throws WorkerNotFoundException If a worker with the given identifier does not exist and isnt a
   *         vet.
   * 
   * @see VaccineRegistry
   * @see Vet
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
   * This method filters the vaccine registries of the hotel and returns an unmodifiable view of the
   * collection of wrong vaccinations.
   * 
   * @return an unmodifiable view of the collection of wrong vaccinations
   * 
   * @see VaccineRegistry
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
   * This method returns the animal with the given identifier or {@code null} if it does not exist.
   * 
   * @param id the identifier of the animal
   * 
   * @return The animal with the given identifier or {@code null} if it does not exist.
   * 
   * @see Animal
   */
  Animal animalExists(String id) {
    return _animals.get(id);
  }

  /**
   * Finds an animal with the given identifier and throws an exception if it does not exist.
   * 
   * <p>
   * This method returns the animal with the given identifier or throws an exception if it does not
   * exist.
   * 
   * @param id the identifier of the animal
   * 
   * @return the animal with the given identifier
   * 
   * @throws AnimalNotFoundException If an animal with the given identifier does not exist.
   * 
   * @see Animal
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
   * This method returns the species with the given identifier or {@code null} if it does not exist.
   * 
   * @param id the identifier of the species
   * 
   * @return The species with the given identifier or {@code null} if it does not exist.
   * 
   * @see Species
   */
  Species speciesExists(String id) {
    return _species.get(id);
  }

  /**
   * Finds a species with the given identifier and throws an exception if it does not exist.
   * 
   * <p>
   * This method returns the species with the given identifier or throws an exception if it does not
   * exist.
   * 
   * @param id the identifier of the species
   * 
   * @return the species with the given identifier
   * 
   * @throws SpeciesNotFoundException If a species with the given identifier does not exist.
   * 
   * @see Species
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
   * This method returns the worker with the given identifier or {@code null} if it does not exist.
   * 
   * @param id the identifier of the worker
   * 
   * @return The worker with the given identifier or {@code null} if it does not exist.
   * 
   * @throws WorkerNotFoundException If a worker with the given identifier does not exist.
   * 
   * @see Worker
   */
  Worker workerExists(String id) {
    return _workers.get(id);
  }

  /**
   * Finds a worker with the given identifier and throws an exception if it does not exist.
   * 
   * <p>
   * This method returns the worker with the given identifier or throws an exception if it does not
   * exist.
   * 
   * @param id the identifier of the worker
   * 
   * @return the worker with the given identifier
   * 
   * @throws WorkerNotFoundException If a worker with the given identifier does not exist.
   * 
   * @see Worker
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
   * This method returns the habitat with the given identifier or {@code null} if it does not exist.
   * 
   * @param id the identifier of the habitat
   * 
   * @return The habitat with the given identifier or {@code null} if it does not exist.
   * 
   * @see Habitat
   */
  Habitat habitatExists(String id) {
    return _habitats.get(id);
  }

  /**
   * Finds a habitat with the given identifier and throws an exception if it does not exist.
   * 
   * <p>
   * This method returns the habitat with the given identifier or throws an exception if it does not
   * exist.
   * 
   * @param id the identifier of the habitat
   * 
   * @return the habitat with the given identifier
   * 
   * @throws HabitatNotFoundException If a habitat with the given identifier does not exist.
   * 
   * @see Habitat
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
   * This method returns the tree with the given identifier or {@code null} if it does not exist.
   * 
   * @param id the identifier of the tree
   * 
   * @return The tree with the given identifier or {@code null} if it does not exist.
   * 
   * @see Tree
   */
  Tree treeExists(String id) {
    return _trees.get(id);
  }

  /**
   * Finds a tree with the given identifier and throws an exception if it does not exist.
   * 
   * <p>
   * This method returns the tree with the given identifier or throws an exception if it does not
   * exist.
   * 
   * @param id the identifier of the tree
   * 
   * @return the tree with the given identifier
   * 
   * @throws TreeNotFoundException If a tree with the given identifier does not exist.
   * 
   * @see Tree
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
   * This method returns the vaccine with the given identifier or {@code null} if it does not exist.
   * 
   * @param id the identifier of the vaccine
   * 
   * @return The vaccine with the given identifier or {@code null} if it does not exist.
   * 
   * @see Vaccine
   */
  Vaccine vaccineExists(String id) {
    return _vaccines.get(id);
  }

  /**
   * Finds a vaccine with the given identifier and throws an exception if it does not exist.
   * 
   * <p>
   * This method returns the vaccine with the given identifier or throws an exception if it does not
   * exist.
   * 
   * @param id the identifier of the vaccine
   * 
   * @return the vaccine with the given identifier
   * 
   * @throws VaccineNotFoundException if a vaccine with the given identifier does not exist.
   * 
   * @see Vaccine
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
   * @throws UnrecognizedEntryException If some entry is not correct.
   * @throws IOException If there is an IO erro while processing the text file.
   **/
  void importFile(String filename) throws UnrecognizedEntryException, IOException {
    Parser parser = new Parser(this);
    parser.parseFile(filename);
  }
}
