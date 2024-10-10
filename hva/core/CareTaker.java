package hva.core;

import java.io.Serial;

import java.util.Map;

import hva.core.exception.HabitatNotFoundException;

public class CareTaker extends Worker {

  @Serial
  private static final long serialVersionUID = 1L;

  private final Map<String, Habitat> _responsibilities = new CaseInsensitiveHashMap<Habitat>();

  /*
   * <------------------------ Constructor ------------------------>
   */

  CareTaker(String id, String name, Hotel hotel) {
    super(id, name, hotel);
  }

  /*
   * <------------------------ Others ------------------------>
   */

  /**
   * Adds an habitat as a responsability for the care taker in this instance to care.
   * 
   * @param id of the habitat
   */
  void addResponsibility(String id) throws HabitatNotFoundException {
    _responsibilities.put(id, hotel().habitatExistsWithException(id));
  }

  /**
   * Removes an habitat as a responsability for the care taker in this instance to care.
   * 
   * @param id of the habitat
   */
  void removeResponsibility(String id) throws HabitatNotFoundException {
    if (_responsibilities.remove(id) == null) {
      throw new HabitatNotFoundException(id);
    }
  }

  /**
   * Calculates the satisfaction of the vet in this instance.
   * 
   * @return the satisfaction
   */
  float satisfaction() {
    int satisfactionPerHabitat = 0;
    int workInHabitat;

    for (Map.Entry<String, Habitat> entry : _responsibilities.entrySet()) {
      Habitat currentHabitat = entry.getValue();

      workInHabitat = currentHabitat.area() + 3 * currentHabitat.animals().size();

      for (Tree currentTree : currentHabitat.trees()) {
        workInHabitat += currentTree.totalCleaningEffort();
      }

      satisfactionPerHabitat += (workInHabitat / currentHabitat.careTakers().size());
    }

    return (300 - satisfactionPerHabitat);
  }

  /**
   * Returns the vet in the format: tipo|id|nome|idResponsabilidades If the vet doesn't have
   * responsibilities, it's in this format: tipo|id|nome
   * 
   * @return the vaccine registry in format
   */
  @Override
  public String toString() {
    String responsibilities = "";

    if (!_responsibilities.isEmpty()) {
      responsibilities =
          "|" + String.join(",", _responsibilities.values().stream().map(Habitat::id).toList());
    }

    return String.format("TRT|%s|%s%s", id(), name(), responsibilities.toString());
  }
}
