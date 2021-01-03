package it.esercitazione4.nodes;

import it.esercitazione4.visitor.Visitable;
import it.esercitazione4.visitor.Visitor;

public class IdLeaf extends Node implements Visitable {

  private String value;

  public IdLeaf(String value) {
    super();
    this.isLeaf = true;
    this.name = Node.ID;
    this.value = value;
  }

  @Override
  public Object accept(Visitor visitor)  throws Exception{
    return visitor.visit(this);
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
