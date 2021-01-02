package it.esercitazione4.nodes;

import it.esercitazione4.visitor.Visitable;
import it.esercitazione4.visitor.Visitor;

public class WhileStatNode extends Node implements StatNode, Visitable {

  private StatListNode statListNode1;
  private StatListNode statListNode2;
  private ExprNode exprNode;

  public WhileStatNode(StatListNode statListNode1,ExprNode exprNode, StatListNode statListNode2) {
    super();
    this.statListNode1 = statListNode1;
    this.statListNode2 = statListNode2;
    this.exprNode = exprNode;
    this.name = Node.WHILE_OP;
  }

  public WhileStatNode(ExprNode exprNode,StatListNode statListNode2) {
    super();
    this.statListNode2 = statListNode2;
    this.exprNode = exprNode;
    this.name = Node.WHILE_OP;
  }

  @Override
  public Object accept(Visitor visitor) {
    return visitor.visit(this);
  }

  public StatListNode getStatListNode1() {
    return statListNode1;
  }

  public void setStatListNode1(StatListNode statListNode1) {
    this.statListNode1 = statListNode1;
  }

  public StatListNode getStatListNode2() {
    return statListNode2;
  }

  public void setStatListNode2(StatListNode statListNode2) {
    this.statListNode2 = statListNode2;
  }

  public ExprNode getExprNode() {
    return exprNode;
  }

  public void setExprNode(ExprNode exprNode) {
    this.exprNode = exprNode;
  }

  @Override
  public String toString() {
    return "WhileStatNode{" +
        "name='" + name + '\'' +
        ", isLeaf=" + isLeaf +
        ", statListNode1=" + statListNode1 +
        ", statListNode2=" + statListNode2 +
        ", exprNode=" + exprNode +
        '}';
  }
}
