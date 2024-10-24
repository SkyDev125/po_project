package hva.core.enumerator;

/**
 * Enumeration of the different influences and its values.
 * 
 * <p>
 * The influence can be POS, with the value of 20, NEU, with 0, or NEG, with -20.
 */
public enum Influence {
  POS(20), NEU(0), NEG(-20);

  private final int _value;

  Influence(int value) {
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
