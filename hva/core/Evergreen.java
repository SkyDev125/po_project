package hva.core;

import java.io.Serial;

import hva.core.enumerator.LeafState;
import hva.core.enumerator.SeasonType;

/**
 * Class representing an evergreen tree in the zoo hotel.
 * 
 * <p>
 * An evergreen is a type of tree and therefore defined by its id, name, age, birth season, cleaning
 * dificulty and hotel.
 * 
 * <p>
 * The evergreen, as a tree, can calculate its {@link #seasonalEffort()} and {@link #leafState()}.
 * 
 * @see Tree
 */
public class Evergreen extends Tree {

  @Serial
  private static final long serialVersionUID = 1L;

  /*
   * <------------------------ Constructor ------------------------>
   */

  /**
   * The constructor of this evergreen.
   * 
   * @param id the identifier of this evergreen
   * @param name the name of this evergreen
   * @param age the age of this evergreen
   * @param cleaningDifficulty the cleaning dificulty of this evergreen
   * @param hotel the hotel of this evergreen
   * 
   * @see Tree#Tree(String, String, int, int, Hotel)
   * @see Hotel
   */
  Evergreen(String id, String name, int age, int cleaningDifficulty, Hotel hotel) {
    super(id, name, age, cleaningDifficulty, hotel);
  }

  /*
   * <------------------------ Others ------------------------>
   */

  /**
   * Returns the seasonal effort of this evergreen.
   * 
   * @return the seasonal effort of this evergreen
   * 
   * @see Tree#seasonalEffort()
   * @see SeasonType
   */
  int seasonalEffort() {
    SeasonType currentSeason = hotel().season();

    switch (currentSeason) {
      case SPRING:
        return 1;
      case SUMMER:
        return 1;
      case FALL:
        return 1;
      default:
        return 2;
    }
  }

  /**
   * Returns the leaf state of this evergreen according to the current season.
   * 
   * @return the leaf state of this evergreen
   * 
   * @see Tree#leafState()
   * @see SeasonType
   */
  LeafState leafState() {
    SeasonType currentSeason = hotel().season();

    switch (currentSeason) {
      case SPRING:
        return LeafState.GENERATELEAVES;
      case SUMMER:
        return LeafState.WITHLEAVES;
      case FALL:
        return LeafState.WITHLEAVES;
      default:
        return LeafState.WITHLEAVES;
    }
  }

  /**
   * Returns a String representation of this evergreen.
   * 
   * <p>
   * This method follows the format:
   * <p>
   * ARVORE|idArvore|nomeArvore|idadeArvore|dificuldadeBaseLimpeza|PERENE|cicloBiologico
   * 
   * @return the String representation of this evergreen
   * 
   * @see Object#toString()
   * @see Tree#toString()
   */
  @Override
  public String toString() {
    return String.format("ÁRVORE|%s|%s|%d|%d|PERENE|%s", id(), name(), age(), cleaningDifficulty(),
        leafState());
  }
}
