package hva.core;

import java.io.Serial;

import hva.core.enumerator.LeafState;
import hva.core.enumerator.SeasonType;

/**
 * Class representing a deciduos tree in the zoo hotel.
 * 
 * <p>
 * A deciduos is a type of tree and therefore defined by its id, name, age, birth season, cleaning
 * dificulty and hotel.
 * 
 * <p>
 * The deciduos, as a tree, can calculate its {@link #seasonalEffort()} and {@link #leafState()}.
 * 
 * @see Tree
 */
public class Deciduos extends Tree {

  @Serial
  private static final long serialVersionUID = 1L;

  /*
   * <------------------------ Constructor ------------------------>
   */

  /**
   * The constructor of this deciduos.
   * 
   * @param id the identifier of this deciduos
   * @param name the name of this deciduos
   * @param age the age of this deciduos
   * @param cleaningDifficulty the cleaning dificulty of this deciduos
   * @param hotel the hotel of this deciduos
   * 
   * @see Tree#Tree(String, String, int, int, Hotel)
   * @see Hotel
   */
  Deciduos(String id, String name, int age, int cleaningDifficulty, Hotel hotel) {
    super(id, name, age, cleaningDifficulty, hotel);
  }

  /*
   * <------------------------ Others ------------------------>
   */

  /**
   * Returns the seasonal effort of this deciduos.
   * 
   * @return the seasonal effort of this deciduos
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
        return 2;
      case FALL:
        return 5;
      default:
        return 0;
    }
  }

  /**
   * Returns the leaf state of this deciduos according to the current season.
   * 
   * @return the leaf state of this deciduos
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
        return LeafState.FALLINGLEAVES;
      default:
        return LeafState.WITHOUTLEAVES;
    }
  }

  /**
   * Returns a String representation of this deciduos.
   * 
   * <p>
   * This method follows the format:
   * <p>
   * ARVORE|idArvore|nomeArvore|idadeArvore|dificuldadeBaseLimpeza|CADUCA|cicloBiologico
   * 
   * @return the String representation of this deciduos
   * 
   * @see Object#toString()
   * @see Tree#toString()
   */
  @Override
  public String toString() {
    return String.format("ÁRVORE|%s|%s|%d|%d|CADUCA|%s", id(), name(), age(), cleaningDifficulty(),
        leafState());
  }
}
