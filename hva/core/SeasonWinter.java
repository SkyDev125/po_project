package hva.core;

import hva.core.enumerator.LeafState;
import hva.core.enumerator.SeasonType;

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

  /*
   * <------------------------ Other ------------------------>
   */
  @Override
  public String toString() {
    return seasonType().toString();
  }
}
