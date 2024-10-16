package hva.core;

public class VetSatisfactionDefaultFormula implements VetSatisfactionFormula {   
  
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
  public int satisfaction(Vet vet) {
   int satisfactionPerSpecies = 0;

    for (Species currentSpecies : vet.responsibilities()) {
      satisfactionPerSpecies += (currentSpecies.animalCount() / currentSpecies.vetCount());
    }

    return (20 - satisfactionPerSpecies);
  }
}
