package hva.core.enumerator;

public enum SeasonType {
  SPRING(0), SUMMER(1), FALL(2), WINTER(3);

  private final int _value;

  SeasonType(int value) {
    _value = value;
  }

  @Override
  public String toString() {
    return Integer.toString(_value);
  }
}
