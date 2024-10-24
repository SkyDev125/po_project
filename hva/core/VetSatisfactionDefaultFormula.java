package hva.core;

import java.io.Serial;
import java.io.Serializable;

/**
 * Class representing a formula to calculate the {@link #satisfaction()} of a vet.
 * 
 * <p>
 * A strategy of the strategy pattern design.
 * 
 * @see VetSatisfactionFormula
 */
public class VetSatisfactionDefaultFormula implements VetSatisfactionFormula, Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * Calculates the satisfaction of the vet using a formula.
   * 
   * <p>
   * This method calculates the satisfaction of the vet based on the species he is responsible for.
   * It follows the formula:
   * <p>
   * satisfaction = 20 - SUMbySpecies(population/numberOfVets)
   * 
   * @return the satisfaction of the vet
   * 
   * @see Vet#satisfaction()
   * @see VetSatisfactionFormula
   */
  public float satisfaction(Vet vet) {
    float satisfactionPerSpecies = 0;

    for (Species currentSpecies : vet.responsibilities()) {
      satisfactionPerSpecies += (currentSpecies.animalCount() / currentSpecies.vetCount());
    }

    return (20 - satisfactionPerSpecies);
  }
}
