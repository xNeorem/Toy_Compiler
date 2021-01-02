package it.esercitazione4.nodes;

import it.esercitazione4.visitor.Visitable;
import it.esercitazione4.visitor.Visitor;

public class FloatConstLeaf extends Node implements Visitable {

  private double value;

  public FloatConstLeaf(double value) {
    super();
    this.name = Node.FLOAT_CONST;
    this.isLeaf = true;
    this.value = value;
  }

  @Override
  public Object accept(Visitor visitor) {
    return visitor.visit(this);
  }

  public double getValue() {
    return value;
  }

  public void setValue(double value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "FloatConstLeaf{" +
        "value=" + value +
        ", name='" + name + '\'' +
        ", isLeaf=" + isLeaf +
        '}';
  }
}
