package hva.core;

import java.io.Serial;
import java.io.Serializable;

/**
 * Class representing a formula to calculate the {@link #satisfaction()} of a caretaker.
 * 
 * <p>
 * A strategy of the strategy pattern design.
 * 
 * @see CareTakerSatisfactionFormula
 */
public class CareTakerSatisfactionDefaultFormula
    implements CareTakerSatisfactionFormula, Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * Calculates the satisfaction of the caretaker using a formula.
   * 
   * <p>
   * This method calculates the satisfaction of the caretaker based on the habitats he is
   * responsible for. It follows the formula:
   * <p>
   * satisfaction = 300 - SUMbyHabitat(workInHabitat/numberOfCareTakers)
   * <p>
   * workInHabitat = area + 3*population + SUMbyTree(cleaningEffort)
   * 
   * @return the satisfaction of the caretaker
   * 
   * @see CareTaker#satisfaction()
   * @see CareTakerFormula
   */
  public float satisfaction(CareTaker careTaker) {

    float satisfactionPerHabitat = 0;
    int workInHabitat;

    for (Habitat currentHabitat : careTaker.responsibilities()) {
      workInHabitat = currentHabitat.area() + 3 * currentHabitat.animals().size();

      for (Tree currentTree : currentHabitat.trees()) {
        workInHabitat += currentTree.totalCleaningEffort();
      }

      satisfactionPerHabitat += (workInHabitat / currentHabitat.careTakers().size());
    }

    return (300 - satisfactionPerHabitat);
  }
}
