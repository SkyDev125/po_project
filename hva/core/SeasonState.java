package hva.core;

import hva.core.enumerator.LeafState;
import hva.core.enumerator.SeasonType;

import java.io.Serializable;

/**
 * Interface representing the different season states.
 * 
 * <p>
 * Implementation of the state pattern design. 
 * <p>
 * Calculates its {@link #seasonalEffort()} and {@link #leafState()}.
 * 
 * @see SeasonSpring
 * @see SeasonSummer
 * @see SeasonFall
 * @see SeasonWinter
 */
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
}
