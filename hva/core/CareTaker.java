package hva.core;

import java.io.Serial;

import java.util.Map;

import hva.core.exception.HabitatNotFoundException;

/**
 * Class representing a caretaker in the zoo hotel.
 * 
 * <p>
 * A caretaker is a type of worker and therefore defined by its id, name and hotel. On top of that,
 * he keeps record of its responsabilities, which are habitats to care for.
 * 
 * <p>
 * The caretaker, as a worker, can calculate its {@link #satisfaction()}.
 * 
 * @see Worker
 */
public class CareTaker extends Worker {

  @Serial
  private static final long serialVersionUID = 1L;

  private final Map<String, Habitat> _responsibilities = new CaseInsensitiveHashMap<Habitat>();

  /*
   * <------------------------ Constructor ------------------------>
   */

  /**
   * The constructor of this caretaker.
   * 
   * @param id the identifier of this caretaker
   * @param name the name of this caretaker
   * @param hotel the hotel of this caretaker
   * 
   * @see Worker#Worker(String, String, Hotel)
   * @see Hotel
   */
  CareTaker(String id, String name, Hotel hotel) {
    super(id, name, hotel);
  }

  /*
   * <------------------------ Sets ------------------------>
   */

  /**
   * This method adds a habitat as a responsibility to this caretaker.
   * 
   * @param id the identifier of the habitat
   * 
   * @throws HabitatNotFoundException If the given habitat does not exist.
   * 
   * @see Worker#addResponsibility(String)
   * @see Habitat
   */
  void addResponsibility(String id) throws HabitatNotFoundException {
    _responsibilities.put(id, hotel().habitatExistsWithException(id));
  }

  /**
   * This method removes a habitat as a responsibility to this caretaker.
   * 
   * @param id the identifier of the habitat
   * 
   * @throws HabitatNotFoundException If the given habitat does not exist.
   * 
   * @see Worker#removeResponsibility(String)
   * @see Habitat
   */
  void removeResponsibility(String id) throws HabitatNotFoundException {
    if (_responsibilities.remove(id) == null) {
      throw new HabitatNotFoundException(id);
    }
  }

  /*
   * <------------------------ Others ------------------------>
   */

  /**
   * Calculates the satisfaction of this caretaker.
   * 
   * <p>
   * This method calculates the satisfaction of this caretaker based on the habitats he is
   * responsible for. It follows the formula:
   * <p>
   * satisfaction = 300 - SUMbyHabitat(workInHabitat/numberOfCareTakers)
   * <p>
   * workInHabitat = area + 3*population + SUMbyTree(cleaningEffort)
   * 
   * @return the satisfaction of this caretaker
   * 
   * @see Worker#satisfaction()
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
   * Returns a String representation of this caretaker.
   * 
   * <p>
   * This method follows the format:
   * <p>
   * TRT|id|nome|idHabitat
   * <p>
   * If the caretaker does not have any habitats as a responsibility, the format is:
   * <p>
   * TRT|id|nome
   * 
   * @return the String representation of this caretaker
   * 
   * @see Object#toString()
   * @see Worker#toString()
   * @see Habitat
   */
  @Override
  public String toString() {
    String responsibilities = "";

    if (!_responsibilities.isEmpty()) {
      responsibilities = "|" + String.join(",",
          _responsibilities.values().stream().map(Habitat::id).sorted().toList());
    }

    return String.format("TRT|%s|%s%s", id(), name(), responsibilities.toString());
  }
}
