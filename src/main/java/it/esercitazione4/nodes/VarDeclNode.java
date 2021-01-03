package it.esercitazione4.nodes;

import it.esercitazione4.visitor.Visitable;
import it.esercitazione4.visitor.Visitor;

public class VarDeclNode extends Node implements Visitable {

  private TypeDeclNode typeDeclNode;
  private IdListInitNode idListInitNode;

  public VarDeclNode(TypeDeclNode typeDeclNode, IdListInitNode idListInitNode) {
    super();
    this.typeDeclNode = typeDeclNode;
    this.idListInitNode = idListInitNode;
    this.name = Node.VAR_DECL_OP;
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

  public IdListInitNode getIdListInitNode() {
    return idListInitNode;
  }

  public void setIdListInitNode(IdListInitNode idListInitNode) {
    this.idListInitNode = idListInitNode;
  }

  @Override
  public String toString() {
    return "VarDeclNode{" +
        "name='" + name + '\'' +
        ", isLeaf=" + isLeaf +
        ", typeDeclNode=" + typeDeclNode +
        ", idListInitNode=" + idListInitNode +
        '}';
  }
}
