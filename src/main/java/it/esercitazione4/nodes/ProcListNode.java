package it.esercitazione4.nodes;

import it.esercitazione4.visitor.Visitable;
import it.esercitazione4.visitor.Visitor;
import java.util.ArrayList;

public class ProcListNode extends Node implements Visitable {
    private ArrayList<ProcNode> procListNode;

    public ProcListNode(ArrayList<ProcNode> procListNode) {
        super();
        this.name = Node.PROC_LIST_OP;
        this.procListNode = procListNode;
    }

    @Override
    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }

    public ArrayList<ProcNode> getProcListNode() {
        return procListNode;
    }

    public void setProcListNode(ArrayList<ProcNode> procListNode) {
        this.procListNode = procListNode;
    }

    @Override
    public String toString() {
        return "ProcListNode{" +
                "procListNode=" + procListNode +
                ", name='" + name + '\'' +
                ", isLeaf=" + isLeaf +
                '}';
    }
}
