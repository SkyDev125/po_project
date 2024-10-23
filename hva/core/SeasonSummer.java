package hva.core;

import hva.core.enumerator.LeafState;
import hva.core.enumerator.SeasonType;

public class SeasonSummer implements SeasonState {
  @Override
  public SeasonState next() {
    return new SeasonFall();
  }

  @Override
  public int deciduosSeasonalEffort() {
    return 2;
  }

  @Override
  public LeafState deciduosLeafState() {
    return LeafState.WITHLEAVES;
  }

  @Override
  public int evergreenSeasonalEffort() {
    return 1;
  }

  @Override
  public LeafState evergreenLeafState() {
    return LeafState.WITHLEAVES;
  }

  @Override
  public SeasonType getSeasonType() {
    return SeasonType.SUMMER;
  }
}
