package hva.core.enumerator;

public enum Influence {
  POS(20), NEU(0), NEG(-20);

  private final int _value;

  Influence(int value) {
    _value = value;
  }

  public int value() {
    return _value;
  }
}
