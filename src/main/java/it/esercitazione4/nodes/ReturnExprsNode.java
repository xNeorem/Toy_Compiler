package it.esercitazione4.nodes;

import it.esercitazione4.visitor.Visitable;
import it.esercitazione4.visitor.Visitor;

public class ReturnExprsNode extends Node implements Visitable {

  private ExprListNode exprListNode;

  public ReturnExprsNode(ExprListNode exprListNode) {
    super();
    this.exprListNode = exprListNode;
    this.name = Node.RETURN_EXPRS_OP;
  }

  @Override
  public Object accept(Visitor visitor) throws Exception {
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
    return "ReturnExprsNode{" +
        "name='" + name + '\'' +
        ", isLeaf=" + isLeaf +
        ", exprListNode=" + exprListNode +
        '}';
  }
}
