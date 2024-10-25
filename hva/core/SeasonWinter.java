package hva.core;

import hva.core.enumerator.LeafState;
import hva.core.enumerator.SeasonType;

/**
 * Class representing the season winter as a state of season states.
 * 
 * <p>
 * Implementation of the state pattern design.
 * <p>
 * Calculates its {@link #seasonalEffort()} and {@link #leafState()}.
 * 
 * @see SeasonState
 */
public class SeasonWinter implements SeasonState {
  /*
   * <------------------------ State Related Functions ------------------------>
   */

  @Override
  public SeasonState next() {
    return new SeasonSpring();
  }

  @Override
  public SeasonType seasonType() {
    return SeasonType.WINTER;
  }

  /*
   * <------------------------ Seasonal Effort ------------------------>
   */

  @Override
  public int seasonalEffort(Deciduos tree) {
    return 0;
  }

  @Override
  public int seasonalEffort(Evergreen tree) {
    return 2;
  }

  /*
   * <------------------------ Leaf State ------------------------>
   */

  @Override
  public LeafState leafState(Deciduos tree) {
    return LeafState.WITHOUTLEAVES;
  }

  @Override
  public LeafState leafState(Evergreen tree) {
    return LeafState.FALLINGLEAVES;
  }
}
