package hva.core;

import java.io.Serial;
import java.util.Collection;
import java.util.Collections;
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

  private CareTakerSatisfactionFormula _careTakerSatisfactionFormula =
      new CareTakerSatisfactionDefaultFormula();
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
   * <------------------------ Gets ------------------------>
   */

  /**
   * Retrieves all the habitats this caretaker has as a responsibity.
   * 
   * <p>
   * This method provides a way to access the collection of habitats without allowing modifications
   * to the underlying collection. The returned collection is a read-only view, and any attempts to
   * modify it will result in an {@code UnsupportedOperationException}.
   * 
   * @return An unmodifiable collecion of the habitats this caretaker has as a responsibility.
   * 
   * @see Habitat
   */
  Collection<Habitat> responsibilities() {
    return Collections.unmodifiableCollection(_responsibilities.values());
  }

  /*
   * <------------------------ Sets ------------------------>
   */

  /**
   * This method alters this vet's satisfaction formula to the given one.
   * 
   * @param formula the new satisfaction formula of this vet
   * 
   * @see VetSatisfactionFormula
   */
  void setSatisfactionFormula(CareTakerSatisfactionFormula formula) {
    _careTakerSatisfactionFormula = formula;
  }

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
    Habitat habitat = hotel().habitatExistsWithException(id);
    _responsibilities.put(id, habitat);
    habitat.addCareTaker(this);
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
   * Calculates the satisfaction of this caretaker using its formula.
   * 
   * @return the satisfaction of this caretaker
   * 
   * @see Worker#satisfaction()
   * @see CareTakerSatisfactionFormula
   */
  double satisfaction() {
    return _careTakerSatisfactionFormula.satisfaction(this);
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
          _responsibilities.values().stream().sorted().map(h -> h.id()).toList());
    }

    return String.format("TRT|%s|%s%s", id(), name(), responsibilities.toString());
  }
}
