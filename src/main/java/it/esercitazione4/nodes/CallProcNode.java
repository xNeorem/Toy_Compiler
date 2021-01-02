package it.esercitazione4.nodes;

import it.esercitazione4.visitor.Visitable;
import it.esercitazione4.visitor.Visitor;

public class CallProcNode extends Node implements StatNode, Visitable {
    private IdLeaf idLeaf;
    private ExprListNode exprListNode;

    public CallProcNode(IdLeaf idLeaf, ExprListNode exprListNode) {
        super();
        this.name = Node.CALL_PROC_OP;
        this.idLeaf = idLeaf;
        this.exprListNode = exprListNode;
    }

    public CallProcNode(IdLeaf idLeaf) {
        super();
        this.name = Node.CALL_PROC_OP;
        this.idLeaf = idLeaf;
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

    public IdLeaf getIdLeaf() {
        return idLeaf;
    }

    public void setIdLeaf(IdLeaf idLeaf) {
        this.idLeaf = idLeaf;
    }

    public ExprListNode getExprListNode() {
        return exprListNode;
    }

    public void setExprListNode(ExprListNode exprListNode) {
        this.exprListNode = exprListNode;
    }

    @Override
    public String toString() {
        return "CallProcNode{" +
                "idLeaf=" + idLeaf +
                ", exprListNode=" + exprListNode +
                ", name='" + name + '\'' +
                ", isLeaf=" + isLeaf +
                '}';
    }
}
