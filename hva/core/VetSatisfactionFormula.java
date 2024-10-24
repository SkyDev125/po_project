package hva.core;

/**
 * Interface representing the formulas to calculate the {@link #satisfaction()} of a vet.
 * 
 * <p>ss
 * Implementation of the strategy pattern design.
 * 
 * @see VetSatisfactionDefaultFormula
 */
public interface VetSatisfactionFormula {

  /**
   * Calculates the satisfaction of the vet using a formula.
   * 
   * @return the satisfaction of the vet
   * 
   * @see Vet#satisfaction()
   * @see VetSatisfactionDefaultFormula
   */
  public float satisfaction(Vet vet);
}
