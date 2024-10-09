package hva.core;

import hva.core.enumerator.Influence;
import hva.core.enumerator.SeasonType;
import hva.core.enumerator.VaccineDamage;
import hva.core.exception.*;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hotel implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  // TODO: how to use this later - private boolean _isModified = false;
  private SeasonType _season = SeasonType.SPRING;
  private final Map<String, Species> _species = new HashMap<String, Species>();
  private final Map<String, Vaccine> _vaccines = new HashMap<String, Vaccine>();
  private final ArrayList<VaccineRegistry> _vaccineRegistry = new ArrayList<VaccineRegistry>();
  private final Map<String, Animal> _animals = new HashMap<String, Animal>();
  private final Map<String, Habitat> _habitats = new HashMap<String, Habitat>();
  private final Map<String, Tree> _trees = new HashMap<String, Tree>();
  private final Map<String, Worker> _workers = new HashMap<String, Worker>();

  /*
   * <------------------------ Gets ------------------------>
   */
  public Collection<Animal> animals() {
    return Collections.unmodifiableCollection(_animals.values());
  }

  public Collection<Worker> workers() {
    return Collections.unmodifiableCollection(_workers.values());
  }

  public Collection<Habitat> habitats() {
    return Collections.unmodifiableCollection(_habitats.values());
  }

  public Collection<Vaccine> vaccines() {
    return Collections.unmodifiableCollection(_vaccines.values());
  }

  public List<VaccineRegistry> vaccineRegistry() {
    return Collections.unmodifiableList(_vaccineRegistry);
  }

  public SeasonType season() {
    return _season;
  }

  /*
   * <------------------------ Sets ------------------------>
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

  public Species addSpecies(String idSpecies, String name) throws DuplicateSpeciesException {

    // Exception Checks
    if (speciesExists(idSpecies) != null
        || _species.values().stream().anyMatch(species -> species.name().equals(name))) {
      throw new DuplicateSpeciesException(idSpecies);
    }

    // Create and Add Species
    Species species = new Species(idSpecies, name);
    _species.put(idSpecies, species);
    return species;
  }

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

  public Tree addTreeToHabitat(String idHabitat, String idTree, String name, int age, int cleanDiff,
      String treeType)
      throws HabitatNotFoundException, UnrecognizedTreeTypeException, DuplicateTreeException {

    // Exception Checks
    Habitat habitat = habitatExistsWithException(idHabitat);

    Tree tree = addTree(idTree, name, age, cleanDiff, treeType);
    habitat.addTree(tree);
    return tree;
  }

  public Tree addTree(String idTree, String name, int age, int cleanDiff, String treeType)
      throws DuplicateTreeException, UnrecognizedTreeTypeException {

    // Exception Checks
    if (treeExists(idTree) != null) {
      throw new DuplicateTreeException(idTree);
    }

    Tree tree;
    switch (treeType) {
      case "PERENE":
        tree = new Deciduos(idTree, name, age, cleanDiff, this);
        break;
      case "CADUCA":
        tree = new Evergreen(idTree, name, age, cleanDiff, this);
        break;
      default:
        throw new UnrecognizedTreeTypeException(treeType);
    }

    _trees.put(idTree, tree);
    return tree;
  }

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
  public SeasonType progressSeason() {
    _season = _season.next();
    for (Tree tree : _trees.values()) {
      tree.grow();
    }
    return _season;
  }

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

  public void transferAnimal(String idAnimal, String idHabitat)
      throws AnimalNotFoundException, HabitatNotFoundException {
    Animal animal = animalExistsWithException(idAnimal);
    Habitat habitat = habitatExistsWithException(idHabitat);
    animal.transferAnimal(habitat);
  }

  public float animalSatisfaction(String id) throws AnimalNotFoundException {
    return animalExistsWithException(id).satisfaction();
  }

  public void addResponsibilityToWorker(String idWorker, String idResponsibility)
      throws WorkerNotFoundException, ResponsibilityNotFoundException {
    Worker worker = workerExistsWithException(idWorker);
    try {
      worker.addResponsibility(idResponsibility);
    } catch (HabitatNotFoundException | SpeciesNotFoundException e) {
      throw new ResponsibilityNotFoundException(idWorker, idResponsibility);
    }
  }

  public void removeResponsibilityFromWorker(String idWorker, String idResponsibility)
      throws WorkerNotFoundException, ResponsibilityNotFoundException {
    Worker worker = workerExistsWithException(idWorker);
    try {
      worker.removeResponsibility(idResponsibility);
    } catch (HabitatNotFoundException | SpeciesNotFoundException e) {
      throw new ResponsibilityNotFoundException(idWorker, idResponsibility);
    }
  }

  public float workerSatisfaction(String id) throws WorkerNotFoundException {
    return workerExistsWithException(id).satisfaction();
  }

  public void changeHabitatArea(String idHabitat, int area) throws HabitatNotFoundException {
    habitatExistsWithException(idHabitat).changeArea(area);
  }

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

  public Collection<Tree> habitatTrees(String idHabitat) throws HabitatNotFoundException {
    return habitatExistsWithException(idHabitat).trees();
  }

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

  public Collection<Animal> habitatAnimals(String idHabitat) throws HabitatNotFoundException {
    return habitatExistsWithException(idHabitat).animals();
  }

  public List<VaccineRegistry> animalVaccinations(String idAnimal) throws AnimalNotFoundException {
    return animalExistsWithException(idAnimal).vaccineRegistry();
  }

  public List<VaccineRegistry> vetVaccinations(String idVet) throws WorkerNotFoundException {
    Worker worker = workerExistsWithException(idVet);
    if (!(worker instanceof Vet)) {
      throw new WorkerNotFoundException(idVet);
    }
    return ((Vet) worker).vaccineRegistry();
  }

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
  Animal animalExists(String id) {
    return _animals.get(id);
  }

  Animal animalExistsWithException(String id) throws AnimalNotFoundException {
    Animal animal = animalExists(id);
    if (animal == null) {
      throw new AnimalNotFoundException(id);
    }
    return animal;
  }

  Species speciesExists(String id) {
    return _species.get(id);
  }

  Species speciesExistsWithException(String id) throws SpeciesNotFoundException {
    Species species = speciesExists(id);
    if (species == null) {
      throw new SpeciesNotFoundException(id);
    }
    return species;
  }

  Worker workerExists(String id) {
    return _workers.get(id);
  }

  Worker workerExistsWithException(String id) throws WorkerNotFoundException {
    Worker worker = workerExists(id);
    if (worker == null) {
      throw new WorkerNotFoundException(id);
    }
    return worker;
  }

  Habitat habitatExists(String id) {
    return _habitats.get(id);
  }

  Habitat habitatExistsWithException(String id) throws HabitatNotFoundException {
    Habitat habitat = habitatExists(id);
    if (habitat == null) {
      throw new HabitatNotFoundException(id);
    }
    return habitat;
  }

  Tree treeExists(String id) {
    return _trees.get(id);
  }

  Tree treeExistsWithException(String id) throws TreeNotFoundException {
    Tree tree = treeExists(id);
    if (tree == null) {
      throw new TreeNotFoundException(id);
    }
    return tree;
  }

  Vaccine vaccineExists(String id) {
    return _vaccines.get(id);
  }

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
   * @throws UnrecognizedEntryException if some entry is not correct
   * @throws IOException if there is an IO erro while processing the text file
   **/
  void importFile(String filename) throws UnrecognizedEntryException, IOException {
    Parser parser = new Parser(this);
    parser.parseFile(filename);
  }
}
