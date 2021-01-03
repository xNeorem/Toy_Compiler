package it.esercitazione4.nodes;

import it.esercitazione4.visitor.Visitable;
import it.esercitazione4.visitor.Visitor;

public class ElifNode extends Node implements Visitable {
    private ExprNode exprNode;
    private StatListNode statiListNode;

    public ElifNode(ExprNode exprNode, StatListNode statiListNode) {
        super();
        this.name = Node.ELIF_OP;
        this.exprNode = exprNode;
        this.statiListNode = statiListNode;
    }

    @Override
    public Object accept(Visitor visitor)  throws Exception{
        return visitor.visit(this);
    }

    public ExprNode getExprNode() {
        return exprNode;
    }

    public void setExprNode(ExprNode exprNode) {
        this.exprNode = exprNode;
    }

    public StatListNode getStatiListNode() {
        return statiListNode;
    }

    public void setStatiListNode(StatListNode statiListNode) {
        this.statiListNode = statiListNode;
    }

    @Override
    public String toString() {
        return "ElifNode{" +
                "exprNode=" + exprNode +
                ", statiListNode=" + statiListNode +
                ", name='" + name + '\'' +
                ", isLeaf=" + isLeaf +
                '}';
    }
}
