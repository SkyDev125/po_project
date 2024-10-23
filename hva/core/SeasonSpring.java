package hva.core;

import hva.core.enumerator.LeafState;
import hva.core.enumerator.SeasonType;

public class SeasonSpring implements SeasonState {
  /*
   * <------------------------ State Related Functions ------------------------>
   */

  @Override
  public SeasonState next() {
    return new SeasonSummer();
  }

  @Override
  public SeasonType seasonType() {
    return SeasonType.SPRING;
  }

  /*
   * <------------------------ Seasonal Effort ------------------------>
   */

  @Override
  public int seasonalEffort(Deciduos tree) {
    return 1;
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
    return LeafState.GENERATELEAVES;
  }

  @Override
  public LeafState leafState(Evergreen tree) {
    return LeafState.GENERATELEAVES;
  }

  /*
   * <------------------------ Other ------------------------>
   */

  @Override
  public String toString() {
    return seasonType().toString();
  }
}
