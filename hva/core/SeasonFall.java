package hva.core;

import hva.core.enumerator.LeafState;
import hva.core.enumerator.SeasonType;

public class SeasonFall implements SeasonState {
  @Override
  public SeasonState next() {
    return new SeasonWinter();
  }

  @Override
  public int deciduosSeasonalEffort() {
    return 5;
  }

  @Override
  public LeafState deciduosLeafState() {
    return LeafState.FALLINGLEAVES;
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
    return SeasonType.FALL;
  }
}
