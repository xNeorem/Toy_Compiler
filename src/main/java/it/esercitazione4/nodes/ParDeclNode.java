package it.esercitazione4.nodes;

import it.esercitazione4.visitor.Visitable;
import it.esercitazione4.visitor.Visitor;

public class ParDeclNode extends Node implements Visitable {
    private TypeDeclNode typeDeclNode;
    private IdListNode idListNode;

    public ParDeclNode(TypeDeclNode typeDeclNode, IdListNode idListNode) {
        super();
        this.name = Node.PAR_DECL_OP;
        this.typeDeclNode = typeDeclNode;
        this.idListNode = idListNode;
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

    public TypeDeclNode getTypeDeclNode() {
        return typeDeclNode;
    }

    public void setTypeDeclNode(TypeDeclNode typeDeclNode) {
        this.typeDeclNode = typeDeclNode;
    }

    public IdListNode getIdListNode() {
        return idListNode;
    }

    public void setIdListNode(IdListNode idListNode) {
        this.idListNode = idListNode;
    }

    @Override
    public String toString() {
        return "ParDeclNode{" +
                "typeDeclNode=" + typeDeclNode +
                ", idListNode=" + idListNode +
                ", name='" + name + '\'' +
                ", isLeaf=" + isLeaf +
                '}';
    }
}
