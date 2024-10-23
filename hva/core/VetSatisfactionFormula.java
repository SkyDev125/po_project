package hva.core;

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
