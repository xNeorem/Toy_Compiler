package it.esercitazione4.nodes;

import it.esercitazione4.visitor.Visitable;
import it.esercitazione4.visitor.Visitor;

public class StringConstLeaf extends Node implements Visitable {
  private String value;

  public StringConstLeaf(String value) {
    super();
    this.value = value;
    this.name = Node.STRING_CONST;
    this.isLeaf = true;
  }

  @Override
  public Object accept(Visitor visitor) {
    return visitor.visit(this);
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "StringConstLeaf{" +
        "name='" + name + '\'' +
        ", isLeaf=" + isLeaf +
        ", value='" + value + '\'' +
        '}';
  }
}
