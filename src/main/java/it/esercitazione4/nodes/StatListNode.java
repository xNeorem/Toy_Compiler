package it.esercitazione4.nodes;

import it.esercitazione4.visitor.Visitable;
import it.esercitazione4.visitor.Visitor;
import java.util.ArrayList;

public class StatListNode extends Node implements Visitable {

  private ArrayList<StatNode> statListNode;

  public StatListNode(ArrayList<StatNode> statListNode) {
    super();
    this.statListNode = statListNode;
    this.name = Node.STAT_LIST_OP;
  }

  @Override
  public Object accept(Visitor visitor) throws Exception {
    return visitor.visit(this);
  }

  public ArrayList<StatNode> getStatListNode() {
    return statListNode;
  }

  public void setStatListNode(ArrayList<StatNode> statListNode) {
    this.statListNode = statListNode;
  }

  @Override
  public String toString() {
    return "StatListNode{" +
        "name='" + name + '\'' +
        ", isLeaf=" + isLeaf +
        ", statListNode=" + statListNode +
        '}';
  }
}
