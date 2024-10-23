package hva.core.enumerator;

public enum LeafState {
  WITHLEAVES("COMFOLHAS"), WITHOUTLEAVES("SEMFOLHAS"), FALLINGLEAVES(
      "LARGARFOLHAS"), GENERATELEAVES("GERARFOLHAS");

  private String _value;

  LeafState(String state) {
    _value = state;
  }

  public String value() {
    return _value;
  }

  @Override
  public String toString() {
    return value();
  }
}
