package it.esercitazione4.nodes;

import it.esercitazione4.visitor.Visitable;
import it.esercitazione4.visitor.Visitor;
import java.util.ArrayList;

public class ElifListNode extends Node implements Visitable {
    private ArrayList<ElifNode> elifListNode;

    public ElifListNode(ArrayList<ElifNode> elifListNode) {
        super();
        this.name = Node.ELIF_LIST_OP;
        this.elifListNode = elifListNode;
    }

    @Override
    public Object accept(Visitor visitor)  throws Exception{
        return visitor.visit(this);
    }

    public ArrayList<ElifNode> getElifListNode() {
        return elifListNode;
    }

    public void setElifListNode(ArrayList<ElifNode> elifListNode) {
        this.elifListNode = elifListNode;
    }

    @Override
    public String toString() {
        return "ElifListNode{" +
                "elifList=" + elifListNode +
                ", name='" + name + '\'' +
                ", isLeaf=" + isLeaf +
                '}';
    }
}
