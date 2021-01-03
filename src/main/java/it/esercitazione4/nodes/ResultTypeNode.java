package it.esercitazione4.nodes;

import it.esercitazione4.visitor.Visitable;
import it.esercitazione4.visitor.Visitor;

public class ResultTypeNode extends Node implements Visitable {
  private TypeDeclNode typeDeclNode;
  private boolean isVoid;

  public ResultTypeNode() {
    super();
    this.name = Node.RESULT_TYPE;
    this.isVoid = true;
  }

  public ResultTypeNode(TypeDeclNode typeDeclNode) {
    super();
    this.typeDeclNode = typeDeclNode;
    this.name = Node.RESULT_TYPE;
    this.isVoid = false;
  }
  @Override
  public Object accept(Visitor visitor) throws Exception {
    return visitor.visit(this);
  }


  public TypeDeclNode getTypeDeclNode() {
    return typeDeclNode;
  }

  public void setTypeDeclNode(TypeDeclNode typeDeclNode) {
    this.typeDeclNode = typeDeclNode;
  }

  public boolean isVoid() {
    return isVoid;
  }

  public void setVoid(boolean aVoid) {
    isVoid = aVoid;
  }

  @Override
  public String toString() {
    return "ResultTypeNode{" +
        "name='" + name + '\'' +
        ", isLeaf=" + isLeaf +
        ", typeDeclNode=" + typeDeclNode +
        ", isVoid=" + isVoid +
        '}';
  }
}
