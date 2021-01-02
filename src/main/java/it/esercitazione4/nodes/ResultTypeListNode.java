package it.esercitazione4.nodes;

import it.esercitazione4.visitor.Visitable;
import it.esercitazione4.visitor.Visitor;
import java.util.ArrayList;

public class ResultTypeListNode extends Node implements Visitable {

  private ArrayList<ResultTypeNode> resultTypeListNode;

  public ResultTypeListNode(ArrayList<ResultTypeNode> resultTypeListNode) {
    super();
    this.resultTypeListNode = resultTypeListNode;
    this.name = Node.RESULT_TYPE_LIST_OP;
  }

  @Override
  public Object accept(Visitor visitor) {
    return visitor.visit(this);
  }

  public ArrayList<ResultTypeNode> getResultTypeListNode() {
    return resultTypeListNode;
  }

  public void setResultTypeListNode(
      ArrayList<ResultTypeNode> resultTypeListNode) {
    this.resultTypeListNode = resultTypeListNode;
  }

  @Override
  public String toString() {
    return "ResultTypeListNode{" +
        "name='" + name + '\'' +
        ", isLeaf=" + isLeaf +
        ", resultTypeListNode=" + resultTypeListNode +
        '}';
  }
}
