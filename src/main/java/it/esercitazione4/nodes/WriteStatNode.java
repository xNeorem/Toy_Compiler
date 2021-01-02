package it.esercitazione4.nodes;

import it.esercitazione4.visitor.Visitable;
import it.esercitazione4.visitor.Visitor;

public class WriteStatNode extends Node implements StatNode, Visitable {

  private ExprListNode exprListNode;

  public WriteStatNode(ExprListNode exprListNode) {
    super();
    this.exprListNode = exprListNode;
    this.name = Node.WRITE_OP;
  }

  @Override
  public Object accept(Visitor visitor) {
    return visitor.visit(this);
  }

  public ExprListNode getExprListNode() {
    return exprListNode;
  }

  public void setExprListNode(ExprListNode exprListNode) {
    this.exprListNode = exprListNode;
  }

  @Override
  public String toString() {
    return "WriteStatNode{" +
        "name='" + name + '\'' +
        ", isLeaf=" + isLeaf +
        ", exprListNode=" + exprListNode +
        '}';
  }
}
