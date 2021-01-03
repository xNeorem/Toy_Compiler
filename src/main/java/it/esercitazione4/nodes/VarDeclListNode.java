package it.esercitazione4.nodes;

import it.esercitazione4.visitor.Visitable;
import it.esercitazione4.visitor.Visitor;
import java.util.ArrayList;

public class VarDeclListNode extends Node implements Visitable {

  private ArrayList<VarDeclNode> varDeclListNode;

  public VarDeclListNode(ArrayList<VarDeclNode> varDeclListNode) {
    super();
    this.varDeclListNode = varDeclListNode;
    this.name = Node.VAR_DECL_LIST_OP;
  }

  @Override
  public Object accept(Visitor visitor) throws Exception {
    return visitor.visit(this);
  }

  public ArrayList<VarDeclNode> getVarDeclListNode() {
    return varDeclListNode;
  }

  public void setVarDeclListNode(ArrayList<VarDeclNode> varDeclListNode) {
    this.varDeclListNode = varDeclListNode;
  }

  @Override
  public String toString() {
    return "VarDeclListNode{" +
        "name='" + name + '\'' +
        ", isLeaf=" + isLeaf +
        ", varDeclListNode=" + varDeclListNode +
        '}';
  }
}
