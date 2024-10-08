package hva.core;

import java.io.Serial;
import hva.core.enumerator.LeafState;
import hva.core.enumerator.SeasonType;

public class Evergreen extends Tree {

  @Serial
  private static final long serialVersionUID = 1L;

  /*
   * <------------------------ Constructor ------------------------>
   */

  public Evergreen(String id, String name, int age, int cleaningDifficulty, Hotel hotel) {
    super(id, name, age, cleaningDifficulty, hotel);
  }

  /*
   * <------------------------ Others ------------------------>
   */

  /**
   * Returns the seasonal effort of the tree in this instance.
   * 
   * @return the seasonal effort
   */
  protected int seasonalEffort() {
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
   * Returns the leaf state of the tree in this instance.
   * 
   * @return the leaf state
   */
  protected LeafState leafState() {
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
   * Returns the tree in the format:
   * ́ARVORE|idArvore|nomeArvore|idadeArvore|dificuldadeBaseLimpeza|tipoArvore|cicloBiologico
   * 
   * @return the tree in format
   */
  @Override
  public String toString() {
    return String.format("ARVORE|%s|%s|%d|%d|PERENE|%s", id(), name(), age(), cleaningDifficulty(),
        leafState());
  }
}
