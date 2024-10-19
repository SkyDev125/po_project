package hva.core.enumerator;

public enum SeasonType {
  SPRING(0) {
    @Override
    public SeasonType next() {
      return SUMMER;
    }
  },
  SUMMER(1) {
    @Override
    public SeasonType next() {
      return FALL;
    }
  },
  FALL(2) {
    @Override
    public SeasonType next() {
      return WINTER;
    }
  },
  WINTER(3) {
    @Override
    public SeasonType next() {
      return SPRING;
    }
  };

  private final int _value;

  SeasonType(int value) {
    _value = value;
  }

  public int value() {
    return _value;
  }

  public abstract SeasonType next();

  @Override
  public String toString() {
    return Integer.toString(value());
  }
}
