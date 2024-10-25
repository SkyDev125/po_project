package hva.core;

/**
 * Interface representing the formulas to calculate the {@link #satisfaction()} of a caretaker.
 * 
 * <p>
 * Implementation of the strategy pattern design.
 * 
 * @see CareTakerSatisfactionDefaultFormula
 */
public interface CareTakerSatisfactionFormula {

  /**
   * Calculates the satisfaction of the caretaker using a formula.
   * 
   * @return the satisfaction of the caretaker
   * 
   * @see CareTaker#satisfaction()
   * @see CareTakerSatisfactionDefaultFormula
   */
  public double satisfaction(CareTaker careTaker);
}
