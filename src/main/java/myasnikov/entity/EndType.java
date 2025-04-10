package myasnikov.entity;

public enum EndType {
  WIN("WIN"),
  LOSE("LOSE"),
  NONE("NONE");

  private final String value;

  EndType(String value) {
    this.value = value;
  }

}
