package it.esercitazione4.nodes;

import it.esercitazione4.visitor.Visitable;
import it.esercitazione4.visitor.Visitor;
import java.util.ArrayList;

public class ParamDeclListNode extends Node implements Visitable {
    private ArrayList<ParDeclNode> paramDeclListNode;

    public ParamDeclListNode(ArrayList<ParDeclNode> paramDeclListNode) {
        super();
        this.name = Node.PARAM_DECL_LIST_OP;
        this.paramDeclListNode = paramDeclListNode;
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

    public ArrayList<ParDeclNode> getParamDeclListNode() {
        return paramDeclListNode;
    }

    public void setParamDeclListNode(ArrayList<ParDeclNode> paramDeclListNode) {
        this.paramDeclListNode = paramDeclListNode;
    }

    @Override
    public String toString() {
        return "ParamDeclListNode{" +
                "paramDeclListNode=" + paramDeclListNode +
                ", name='" + name + '\'' +
                ", isLeaf=" + isLeaf +
                '}';
    }
}
