package hva.core.enumerator;

/**
 * Enumeration of the different leaf states and their values.
 * 
 * <p>
 * The leaf state can be WITHLEAVES, with the value COMFOLHAS, WITHOUTLEAVES, with SEMFOLHAS,
 * FALLINGLEAVES, with LARGARFOLHAS, or GENERATELEAVES, with GERARFOLHAS.
 */
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
