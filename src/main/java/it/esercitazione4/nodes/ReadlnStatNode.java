package it.esercitazione4.nodes;

import it.esercitazione4.visitor.Visitable;
import it.esercitazione4.visitor.Visitor;

public class ReadlnStatNode extends Node implements StatNode, Visitable {

  private IdListNode idListNode;

  public ReadlnStatNode(IdListNode idListNode) {
    super();
    this.name = Node.READ_OP;
    this.idListNode = idListNode;
  }

  @Override
  public Object accept(Visitor visitor) {
    return visitor.visit(this);
  }

  public IdListNode getIdListNode() {
    return idListNode;
  }

  public void setIdListNode(IdListNode idListNode) {
    this.idListNode = idListNode;
  }

  @Override
  public String toString() {
    return "ReadlnStatNode{" +
        "name='" + name + '\'' +
        ", isLeaf=" + isLeaf +
        ", idListNode=" + idListNode +
        '}';
  }
}
