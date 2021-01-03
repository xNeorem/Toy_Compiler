package it.esercitazione4.nodes;

import it.esercitazione4.visitor.Visitable;
import it.esercitazione4.visitor.Visitor;

public class AssignStatNode extends Node implements StatNode, Visitable {
    private IdListNode idListNode;
    private ExprListNode exprListNode;

    public AssignStatNode(IdListNode idListNode, ExprListNode exprListNode) {
        super();
        this.name = Node.ASSIGN_OP;
        this.idListNode = idListNode;
        this.exprListNode = exprListNode;
    }

    @Override
    public Object accept(Visitor visitor)  throws Exception{
        return visitor.visit(this);
    }

    public IdListNode getIdListNode() {
        return idListNode;
    }

    public void setIdListNode(IdListNode idListNode) {
        this.idListNode = idListNode;
    }

    public ExprListNode getExprListNode() {
        return exprListNode;
    }

    public void setExprListNode(ExprListNode exprListNode) {
        this.exprListNode = exprListNode;
    }

    @Override
    public String toString() {
        return "AssignStatNode{" +
                "idListNode=" + idListNode +
                ", exprListNode=" + exprListNode +
                ", name='" + name + '\'' +
                ", isLeaf=" + isLeaf +
                '}';
    }
}
