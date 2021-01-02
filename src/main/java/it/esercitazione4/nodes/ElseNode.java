package it.esercitazione4.nodes;

import it.esercitazione4.visitor.Visitable;
import it.esercitazione4.visitor.Visitor;

public class ElseNode extends Node implements Visitable {
    private StatListNode statListNode;

    public ElseNode(StatListNode statListNode) {
        super();
        this.name = Node.ELSE_OP;
        this.statListNode = statListNode;
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

    public StatListNode getStatListNode() {
        return statListNode;
    }

    public void setStatListNode(StatListNode statListNode) {
        this.statListNode = statListNode;
    }

    @Override
    public String toString() {
        return "ElseNode{" +
                "statListNode=" + statListNode +
                ", name='" + name + '\'' +
                ", isLeaf=" + isLeaf +
                '}';
    }
}
