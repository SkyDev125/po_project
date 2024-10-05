package hva.core;

import hva.core.enumf.Influence;
import hva.core.enumf.SeasonType;
import hva.core.exception.*;

import java.io.*;
import java.util.*;
// FIXME import classes

public class Hotel implements Serializable {

  @Serial
  private static final long serialVersionUID = 202407081733L;
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
  public void addAnimal(String idAnimal, String name, String idSpecies, String idHabitat) {
    // TODO: define the function
    return;
  }

  public void addWorker(String idWorker, String name, String type) {
    // TODO: define the function
    return;
  }

  public void addHabitat(String idHabitat, String name, int area) {
    // TODO: define the function
    return;
  }

  public void addVaccine(String idVaccine, String name, String idSpecies) {
    // TODO: define the function
    return;
  }

  /*
   * <------------------------ Others ------------------------>
   */
  public SeasonType progressSeason() {
    // TODO: define the function
    return SeasonType.SPRING;
  }

  public int satisfaction() {
    // TODO: define the function
    return -1;
  }

  public void transferAnimal(String idAnimal, String idHabitat) {
    // TODO: define the function
    return;
  }

  public int animalSatisfaction(String id) {
    // TODO: define the function
    return -1;
  }

  public void addResponsibilityToWorker(String idWorker, String idResponsibility) {
    // TODO: define the function
    return;
  }

  public void removeResponsibilityFromWorker(String idWorker, String idResponsibility) {
    // TODO: define the function
    return;
  }

  public int workerSatisfaction(String id) {
    // TODO: define the function
    return -1;
  }

  public void changeHabitatArea(String idHabitat, int area) {
    // TODO: define the function
    return;
  }

  public void changeHabitatSuitability(String idHabitat, String idSpecies, Influence Influence) {
    // TODO: define the function
    return;
  }

  public void addTreeToHabitat(String idHabitat, String idTree, String name, int age, int cleanDiff, String treeType) {
    // TODO: define the function
    return;
  }

  public Collection<Tree> habitatTrees(String idHabitat) {
    // TODO: define the function
    return Collections.unmodifiableCollection(null);
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
