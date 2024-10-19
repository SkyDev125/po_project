package hva.core;

public class CareTakerSatisfactionDefaultFormula implements CareTakerSatisfactionFormula {

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
  public int satisfaction(CareTaker careTaker) {

    int satisfactionPerHabitat = 0;
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
