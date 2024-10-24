package hva.core;

import hva.core.enumerator.LeafState;
import hva.core.enumerator.SeasonType;

/**
 * Class representing the season summer as a state of season states.
 * 
 * <p>
 * Implementation of the state pattern design.
 * <p>
 * Calculates its {@link #seasonalEffort()} and {@link #leafState()}.
 * 
 * @see SeasonState
 */
public class SeasonSummer implements SeasonState {
  /*
   * <------------------------ State Related Functions ------------------------>
   */

  @Override
  public SeasonState next() {
    return new SeasonFall();
  }

  @Override
  public SeasonType seasonType() {
    return SeasonType.SUMMER;
  }

  /*
   * <------------------------ Seasonal Effort ------------------------>
   */

  @Override
  public int seasonalEffort(Deciduos tree) {
    return 2;
  }

  @Override
  public int seasonalEffort(Evergreen tree) {
    return 1;
  }

  /*
   * <------------------------ Leaf State ------------------------>
   */

  @Override
  public LeafState leafState(Deciduos tree) {
    return LeafState.WITHLEAVES;
  }

  @Override
  public LeafState leafState(Evergreen tree) {
    return LeafState.WITHLEAVES;
  }

  /*
   * <------------------------ Other ------------------------>
   */
  @Override
  public String toString() {
    return seasonType().toString();
  }
}
