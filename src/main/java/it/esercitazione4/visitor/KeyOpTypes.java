package it.esercitazione4.visitor;

public class KeyOpTypes {

  public KeyOpTypes(String first, String second, String operation) {
    this.first = first;
    this.second = second;
    this.operation = operation;
  }

  @Override
  public String toString() {
    return "KeyOpTypes{" +
        "first='" + first + '\'' +
        ", second='" + second + '\'' +
        ", operation='" + operation + '\'' +
        '}';
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    KeyOpTypes that = (KeyOpTypes) o;

    if (first != null ? !first.equals(that.first) : that.first != null) {
      return false;
    }
    if (second != null ? !second.equals(that.second) : that.second != null) {
      return false;
    }
    return operation != null ? operation.equals(that.operation) : that.operation == null;
  }

  @Override
  public int hashCode() {
    int result = first != null ? first.hashCode() : 0;
    result = 31 * result + (second != null ? second.hashCode() : 0);
    result = 31 * result + (operation != null ? operation.hashCode() : 0);
    return result;
  }

  String first, second, operation;
}
