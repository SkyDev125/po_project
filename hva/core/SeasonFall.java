package hva.core;

import hva.core.enumerator.LeafState;
import hva.core.enumerator.SeasonType;

public class SeasonFall implements SeasonState {
  /*
   * <------------------------ State Related Functions ------------------------>
   */

  @Override
  public SeasonState next() {
    return new SeasonWinter();
  }

  @Override
  public SeasonType seasonType() {
    return SeasonType.FALL;
  }

  /*
   * <------------------------ Seasonal Effort ------------------------>
   */

  @Override
  public int seasonalEffort(Deciduos tree) {
    return 5;
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
    return LeafState.FALLINGLEAVES;
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
