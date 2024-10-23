package hva.core;

import hva.core.enumerator.LeafState;
import hva.core.enumerator.SeasonType;

public interface SeasonState {
  public SeasonType getSeasonType();

  SeasonState next();
  
  public int deciduosSeasonalEffort();
  
  public int evergreenSeasonalEffort();
  
  public LeafState deciduosLeafState();
  
  public LeafState evergreenLeafState();
}
