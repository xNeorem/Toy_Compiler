package it.esercitazione4.nodes;

import it.esercitazione4.visitor.Visitable;
import it.esercitazione4.visitor.Visitor;

public class NullConstLeaf extends Node implements Visitable {

  public NullConstLeaf() {
    this.isLeaf = true;
    this.name = Node.NULL_CONST;
  }

  @Override
  public Object accept(Visitor visitor) {
    return visitor.visit(this);
  }
}
