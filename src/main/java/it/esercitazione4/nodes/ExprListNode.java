package it.esercitazione4.nodes;

import it.esercitazione4.visitor.Visitable;
import it.esercitazione4.visitor.Visitor;
import java.util.ArrayList;

public class ExprListNode extends Node implements Visitable {
    private ArrayList<ExprNode> exprListNode;

    public ExprListNode(ArrayList<ExprNode> exprListNode) {
        super();
        this.name = Node.EXPR_LIST_OP;
        this.exprListNode = exprListNode;
    }

    @Override
    public Object accept(Visitor visitor)  throws Exception{
        return visitor.visit(this);
    }

    public ArrayList<ExprNode> getExprListNode() {
        return exprListNode;
    }

    public void setExprListNode(ArrayList<ExprNode> exprListNode) {
        this.exprListNode = exprListNode;
    }

    @Override
    public String toString() {
        return "ExprListNode{" +
                "exprListNode=" + exprListNode +
                ", name='" + name + '\'' +
                ", isLeaf=" + isLeaf +
                '}';
    }
}
