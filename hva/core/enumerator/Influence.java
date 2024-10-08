package hva.core.enumerator;

public enum Influence {
  POS(20), NEU(0), NEG(-20);

  private final int value;

  Influence(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }
}
