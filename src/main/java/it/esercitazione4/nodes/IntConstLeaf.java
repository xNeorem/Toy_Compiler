package it.esercitazione4.nodes;

import it.esercitazione4.visitor.Visitable;
import it.esercitazione4.visitor.Visitor;

public class IntConstLeaf extends Node implements Visitable {
  private int value;

  public IntConstLeaf(int value) {
    super();
    this.value = value;
    this.isLeaf = true;
    this.name = Node.INT_CONST;
  }
  @Override
  public Object accept(Visitor visitor)  throws Exception{
    return visitor.visit(this);
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "IntConstLeaf{" +
        "value=" + value +
        ", name='" + name + '\'' +
        ", isLeaf=" + isLeaf +
        '}';
  }
}
