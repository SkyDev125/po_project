package hva.core;

import hva.core.enumf.Influence;
import hva.core.enumf.SeasonType;
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
  private Map<String, Species> _species = new HashMap<String, Species>();
  private Map<String, Vaccine> _vaccines = new HashMap<String, Vaccine>();
  private ArrayList<VaccineRegistry> _vaccineRegistry = new ArrayList<VaccineRegistry>();
  private Map<String, Animal> _animals = new HashMap<String, Animal>();
  private Map<String, Habitat> _habitats = new HashMap<String, Habitat>();
  private Map<String, Tree> _trees = new HashMap<String, Tree>();
  private Map<String, Worker> _workers = new HashMap<String, Worker>();

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
  public void addAnimal(String idAnimal, String name, String idSpecies, String idHabitat)
      throws DuplicateAnimalException, SpeciesNotFoundException, HabitatNotFoundException {

    // Exception Checks
    if (animalExists(idAnimal) != null)
      throw new DuplicateAnimalException(idAnimal);
    Species species = speciesExistsWithException(idSpecies);
    Habitat habitat = habitatExistsWithException(idHabitat);

    // Create and Add Animal
    Animal animal = new Animal(idAnimal, name, species, habitat);
    _animals.put(idHabitat, animal);
    species.addAnimal(animal);
    habitat.addAnimal(animal);
  }

  public void addSpecies(String idSpecies, String name) throws DuplicateSpeciesException {

    // Exception Checks
    if (speciesExists(idSpecies) != null)
      throw new DuplicateSpeciesException(idSpecies);

    // Create and Add Species
    Species species = new Species(idSpecies, name);
    _species.put(idSpecies, species);
    return;
  }

  public void addWorker(String idWorker, String name, String type)
      throws DuplicateWorkerException, UnrecognizedWorkerTypeException {

    // Exception Checks
    if (workerExists(idWorker) != null)
      throw new DuplicateWorkerException(idWorker);

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
  }

  public void addHabitat(String idHabitat, String name, int area) throws DuplicateHabitatException {

    // Exception Checks
    if (habitatExists(idHabitat) != null)
      throw new DuplicateHabitatException(idHabitat);

    // Create and Add Habitat
    Habitat habitat = new Habitat(idHabitat, name, area);
    _habitats.put(idHabitat, habitat);
  }

  public Tree addTreeToHabitat(String idHabitat, String idTree, String name, int age, int cleanDiff, String treeType)
      throws HabitatNotFoundException, UnrecognizedTreeTypeException, DuplicateTreeException {

    // Exception Checks
    Habitat habitat = habitatExistsWithException(idHabitat);
    if (treeExists(idTree) != null) {
      throw new DuplicateTreeException(idTree);
    }

    // Create and Add Tree
    Tree tree;
    switch (treeType) {
      case "PER":
        tree = new Deciduos(idTree, name, age, cleanDiff, this);
        break;
      case "CAD":
        tree = new Evergreen(idTree, name, age, cleanDiff, this);
        break;
      default:
        throw new UnrecognizedTreeTypeException(treeType);
    }

    habitat.addTree(tree);
    _trees.put(idTree, tree);
    return tree;
  }

  public void addVaccine(String idVaccine, String name, String idSpecies)
      throws DuplicateVaccineException, SpeciesNotFoundException {

    // Exception Checks
    if (vaccineExists(idVaccine) != null)
      throw new DuplicateVaccineException(idVaccine);

    String[] idsSpecies = idSpecies.split(",");
    ArrayList<Species> allSpecies = new ArrayList<Species>();

    for (String id : idsSpecies) {
      Species species = speciesExistsWithException(id);
      allSpecies.add(species);
    }

    // Create and Add Vaccine
    Vaccine vaccine = new Vaccine(idVaccine, name, allSpecies);
    _vaccines.put(idVaccine, vaccine);
  }

  /*
   * <------------------------ Others ------------------------>
   */
  public SeasonType progressSeason() {
    _season = _season.next();
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

  public void vaccinateAnimal(String idAnimal, String idVaccine, String idVet) {
    // TODO: define the function
    return;
  }

  public Collection<Animal> habitatAnimals(String idHabitat) {
    // TODO: define the function
    return Collections.unmodifiableCollection(null);
  }

  public List<VaccineRegistry> animalVaccinations(String idAnimal) {
    // TODO: define the function
    return Collections.unmodifiableList(null);
  }

  public List<VaccineRegistry> vetVaccinations(String idVet) {
    // TODO: define the function
    return Collections.unmodifiableList(null);
  }

  public List<VaccineRegistry> filterWrongVaccinations() {
    // TODO: define the function
    return Collections.unmodifiableList(null);
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
   * @throws IOException                if there is an IO erro while processing
   *                                    the text file
   **/
  void importFile(String filename) throws UnrecognizedEntryException, IOException /* FIXME maybe other exceptions */ {
    // FIXME implement method
  }
}
