package hva.core.enumerator;

/**
 * Enumeration of the different vaccine damages and their values.
 * 
 * <p>
 * The vaccine damage can be NORMAL, CONFUSION, ACCIDENT or ERROR.
 */
public enum VaccineDamage {
  NORMAL("NORMAL"), CONFUSION("CONFUSÃO"), ACCIDENT("ACIDENTE"), ERROR("ERRO");

  private String _value;

  VaccineDamage(String value) {
    _value = value;
  }

  public String value() {
    return _value;
  }

  @Override
  public String toString() {
    return value();
  }
}
