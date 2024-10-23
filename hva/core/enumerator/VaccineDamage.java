package hva.core.enumerator;

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
