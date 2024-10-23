package hva.core;

import hva.core.enumerator.LeafState;
import hva.core.enumerator.SeasonType;

public class SeasonSpring implements SeasonState {
  @Override
  public SeasonState next() {
    return new SeasonSummer();
  }

  @Override
  public int deciduosSeasonalEffort() {
    return 1;
  }

  @Override
  public LeafState deciduosLeafState() {
    return LeafState.GENERATELEAVES;
  }

  @Override
  public int evergreenSeasonalEffort() {
    return 1;
  }

  @Override
  public LeafState evergreenLeafState() {
    return LeafState.GENERATELEAVES;
  }

  @Override
  public SeasonType getSeasonType() {
    return SeasonType.SPRING;
  }
}
