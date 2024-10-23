package hva.core;

import hva.core.enumerator.LeafState;
import hva.core.enumerator.SeasonType;

public class SeasonWinter implements SeasonState {
  @Override
  public SeasonState next() {
    return new SeasonSpring();
  }

  @Override
  public int deciduosSeasonalEffort() {
    return 0;
  }

  @Override
  public LeafState deciduosLeafState() {
    return LeafState.WITHOUTLEAVES;
  }

  @Override
  public int evergreenSeasonalEffort() {
    return 2;
  }

  @Override
  public LeafState evergreenLeafState() {
    return LeafState.FALLINGLEAVES;
  }

  @Override
  public SeasonType getSeasonType() {
    return SeasonType.WINTER;
  }
}
