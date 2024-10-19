package hva.core;

public interface CareTakerSatisfactionFormula {

  /**
   * Calculates the satisfaction of the caretaker using a formula.
   * 
   * @return the satisfaction of the caretaker
   * 
   * @see CareTaker#satisfaction()
   * @see CareTakerSatisfactionDefaultFormula
   */
  public int satisfaction(CareTaker careTaker);
}
