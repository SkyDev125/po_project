package hva.core.enumerator;

/**
 * Enumeration of the different seasons and their values.
 * 
 * <p>
 * The season type can be SPRING, with a value of 0, SUMMER, with 1, FALL, with 2 ou WINTER, with 3.
 */
public enum SeasonType {
  SPRING(0), SUMMER(1), FALL(2), WINTER(3);

  private final int _value;

  SeasonType(int value) {
    _value = value;
  }

  public int value() {
    return _value;
  }

  @Override
  public String toString() {
    return Integer.toString(value());
  }
}
