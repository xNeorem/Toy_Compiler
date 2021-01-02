package it.esercitazione4.nodes;

import it.esercitazione4.visitor.Visitable;
import it.esercitazione4.visitor.Visitor;

public class IfStatNode extends Node implements StatNode, Visitable {
    private ExprNode exprNode;
    private StatListNode statListNode;
    private ElifListNode elifListNode;
    private ElseNode elseNode;

    public IfStatNode(ExprNode exprNode, StatListNode statListNode, ElifListNode elifListNode, ElseNode elseNode) {
        super();
        this.name = Node.IF_OP;
        this.exprNode = exprNode;
        this.statListNode = statListNode;
        this.elifListNode = elifListNode;
        this.elseNode = elseNode;
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

    public ExprNode getExprNode() {
        return exprNode;
    }

    public void setExprNode(ExprNode exprNode) {
        this.exprNode = exprNode;
    }

    public StatListNode getStatListNode() {
        return statListNode;
    }

    public void setStatListNode(StatListNode statListNode) {
        this.statListNode = statListNode;
    }

    public ElifListNode getElifListNode() {
        return elifListNode;
    }

    public void setElifListNode(ElifListNode elifListNode) {
        this.elifListNode = elifListNode;
    }

    public ElseNode getElseNode() {
        return elseNode;
    }

    public void setElseNode(ElseNode elseNode) {
        this.elseNode = elseNode;
    }

    @Override
    public String toString() {
        return "IfStatNode{" +
                "exprNode=" + exprNode +
                ", statListNode=" + statListNode +
                ", elifListNode=" + elifListNode +
                ", elseNode=" + elseNode +
                ", name='" + name + '\'' +
                ", isLeaf=" + isLeaf +
                '}';
    }
}
