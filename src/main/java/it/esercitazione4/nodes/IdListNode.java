package it.esercitazione4.nodes;

import it.esercitazione4.visitor.Visitable;
import it.esercitazione4.visitor.Visitor;
import java.util.ArrayList;

public class IdListNode extends Node implements Visitable {
    private ArrayList<IdLeaf> idListNode;

    public IdListNode(ArrayList<IdLeaf> idListNode) {
        super();
        this.name = Node.ID_LIST_OP;
        this.idListNode = idListNode;
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

    public ArrayList<IdLeaf> getIdListNode() {
        return idListNode;
    }

    public void setIdListNode(ArrayList<IdLeaf> idListNode) {
        this.idListNode = idListNode;
    }

    @Override
    public String toString() {
        return "IdListNode{" +
                "idListNode=" + idListNode +
                ", name='" + name + '\'' +
                ", isLeaf=" + isLeaf +
                '}';
    }
}
