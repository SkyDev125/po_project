package hva.core;

import java.io.IOException;

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
import hva.core.exception.WorkerNotFoundException;

import java.io.FileReader;
import java.io.BufferedReader;

/**
 * Esta solução assume que a classe Hotel já tem a seguinte funcionalidade
 * 
 * public class Hotel { public void registerAnimal(animalId, name, habitatId, speciesId) throws
 * OneOrMoreCoreExceptions { ... } public void registerSpecies(speciesId, name) throws
 * OneOrMoreCoreExceptions { ... } public void registerEmployee(employeeId, name, empType) throws
 * OneOrMoreCoreExceptions { ... } public void addResponsibility(employeeId, responsibility) throws
 * OneOrMoreCoreExceptions { ... } public void registerVaccine(vaccineId, name, String[] speciesIds)
 * throws someCoreExceptionsOneOrMoreCoreExceptions { ... } public void createTree(TreeId, name,
 * type, age, diff) throws OneOrMoreCoreExceptions { ... } public Habitat registerHabitat(habitatId,
 * name, area) throws OneOrMoreCoreExceptions { ... }
 * 
 * Note-se que esta funcionalidade pode ser utilizada na concretização de alguns dos comandos. Caso
 * Hotel não tenha esta funcionalidade, então deverão substituir a invocação destes métodos na
 * classe Parser por uma ou mais linhas com uma funcionalidade semelhante. Cada um destes métodos
 * pode lançar uma ou mais excepções que irão corresponder aos erros que podem acontecer ao nível do
 * domínio surante a concretização da funcionalidade em causa.
 **/

public class Parser {
  private Hotel _hotel;

  Parser(Hotel h) {
    _hotel = h;
  }

  public void parseFile(String filename) throws IOException, UnrecognizedEntryException {
    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
      String line;

      while ((line = reader.readLine()) != null)
        parseLine(line);
    }
  }

  private void parseLine(String line) throws UnrecognizedEntryException {
    String[] components = line.split("\\|");
    switch (components[0]) {
      case "ESPÉCIE" -> parseSpecies(components);
      case "ANIMAL" -> parseAnimal(components);
      case "ÁRVORE" -> parseTree(components);
      case "HABITAT" -> parseHabitat(components);
      case "TRATADOR" -> parseEmployee(components, "TRT");
      case "VETERINÁRIO" -> parseEmployee(components, "VET");
      case "VACINA" -> parseVaccine(components);
      default -> throw new UnrecognizedEntryException("tipo de entrada inválido: " + components[0]);
    }
  }

  // Parse a line with format ANIMAL|id|nome|idEspécie|idHabitat
  private void parseAnimal(String[] components) throws UnrecognizedEntryException {
    try {
      String id = components[1];
      String name = components[2];
      String speciesId = components[3];
      String habitatId = components[4];

      _hotel.addAnimal(id, name, speciesId, habitatId);
    } catch (DuplicateAnimalException | SpeciesNotFoundException | HabitatNotFoundException e) {
      throw new UnrecognizedEntryException("Invalid entry: " + e.getMessage());
    }
  }

  // Parse a line with format ESPÉCIE|id|nome
  private void parseSpecies(String[] components) throws UnrecognizedEntryException {
    try {
      String id = components[1];
      String name = components[2];

      _hotel.addSpecies(id, name);
    } catch (DuplicateSpeciesException e) {
      throw new UnrecognizedEntryException("Invalid entry: " + e.getMessage());
    }
  }

  // Parse a line with format TRATADOR|id|nome|idHabitat1,...,idHabitatN or
  // VETERINÁRIO|id|nome|idEspécie1,...,idEspécieN
  private void parseEmployee(String[] components, String empType)
      throws UnrecognizedEntryException {
    try {
      String id = components[1];
      String name = components[2];

      _hotel.addWorker(id, name, empType);

      if (components.length == 4) {
        for (String responsibility : components[3].split(","))
          _hotel.addResponsibilityToWorker(id, responsibility);
      }
    } catch (DuplicateWorkerException | UnrecognizedWorkerTypeException | WorkerNotFoundException
        | ResponsibilityNotFoundException e) {
      throw new UnrecognizedEntryException("Invalid entry: " + e.getMessage());
    }
  }

  // Parse a line with format VACINA|id|nome|idEspécie1,...,idEspécieN
  private void parseVaccine(String[] components) throws UnrecognizedEntryException {

    String id = components[1];
    String name = components[2];

    try {
      if (components.length == 4) {
        _hotel.addVaccine(id, name, components[3]);
      } else {
        _hotel.addVaccine(id, name, "");
      }
    } catch (DuplicateVaccineException | SpeciesNotFoundException e) {
      throw new UnrecognizedEntryException("Invalid entry: " + e.getMessage());
    }
  }

  // Parse a line with format ÁRVORE|id|nome|idade|dificuldade|tipo
  private void parseTree(String[] components) throws UnrecognizedEntryException {
    try {
      String id = components[1];
      String name = components[2];
      int age = Integer.parseInt(components[3]);
      int diff = Integer.parseInt(components[4]);
      String type = components[5];

      _hotel.addTree(id, name, age, diff, type);
    } catch (DuplicateTreeException | UnrecognizedTreeTypeException e) {
      throw new UnrecognizedEntryException("Invalid entry: " + e.getMessage());
    }
  }

  // Parse a line with format HABITAT|id|nome|área|idÁrvore1,...,idÁrvoreN
  private void parseHabitat(String[] components) throws UnrecognizedEntryException {
    try {
      String id = components[1];
      String name = components[2];
      int area = Integer.parseInt(components[3]);

      Habitat hab = _hotel.addHabitat(id, name, area);

      if (components.length == 5) {
        String[] listOfTree = components[4].split(",");
        for (String treeKey : listOfTree)
          hab.addTree(_hotel.treeExistsWithException(treeKey));
      }
    } catch (TreeNotFoundException | DuplicateHabitatException e) {
      throw new UnrecognizedEntryException("Invalid entry: " + e.getMessage());
    }
  }
}

/**
 * Nota: O bloco catch presente nos vários métodos parse desta classe deverá ter em conta as várias
 * excepções que podem acontecer no contexto do bloco try em questão. Estas excepções do domínio têm
 * que ser definidas por cada grupo e devem representar situações de erro específicas do domínio.
 **/
