package hva.core;

import hva.core.enumerator.LeafState;
import hva.core.enumerator.SeasonType;

import java.io.Serializable;

public interface SeasonState extends Serializable {

  /*
   * <------------------------ State Related Functions ------------------------>
   */

  public SeasonState next();

  public SeasonType seasonType();

  /*
   * <------------------------ Seasonal Effort ------------------------>
   */

  public int seasonalEffort(Deciduos tree);

  public int seasonalEffort(Evergreen tree);

  /*
   * <------------------------ Leaf State ------------------------>
   */

  public LeafState leafState(Deciduos tree);

  public LeafState leafState(Evergreen tree);

  /*
   * <------------------------ Other ------------------------>
   */
  @Override
  public String toString();
}
