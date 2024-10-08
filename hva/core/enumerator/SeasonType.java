package hva.core.enumerator;

public enum SeasonType {
  SPRING(0), SUMMER(1), FALL(2), WINTER(3);

  private final int value;

  SeasonType(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }

  public SeasonType next() {
    return switch (this) {
      case SPRING -> SUMMER;
      case SUMMER -> FALL;
      case FALL -> WINTER;
      case WINTER -> SPRING;
    };
  }
}
