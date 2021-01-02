package it.esercitazione4.nodes;

import it.esercitazione4.visitor.Visitable;
import it.esercitazione4.visitor.Visitor;
import java.util.ArrayList;

public class IdListInitNode extends Node implements Visitable {
    private ArrayList<Object> idListInitNode;


    public IdListInitNode(ArrayList<Object> idListInitNode) {
        super();
        this.name = Node.ID_LIST_INIT_OP;
        this.idListInitNode = idListInitNode;
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

    public ArrayList<Object> getIdListInitNode() {
        return idListInitNode;
    }

    public void setIdListInitNode(ArrayList<Object> idListInitNode) {
        this.idListInitNode = idListInitNode;
    }

    @Override
    public String toString() {
        return "IdListInitNode{" +
                "idListInitNode=" + idListInitNode +
                ", name='" + name + '\'' +
                ", isLeaf=" + isLeaf +
                '}';
    }
}
