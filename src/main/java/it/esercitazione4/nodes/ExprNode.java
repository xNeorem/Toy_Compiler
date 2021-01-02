package it.esercitazione4.nodes;

import it.esercitazione4.visitor.Visitable;
import it.esercitazione4.visitor.Visitor;

public class ExprNode extends Node implements Visitable {
    private Object value1 = null;
    private Object value2 = null;

    public ExprNode(String name, Object value1) {
        super();
        this.name = name;
        this.value1 = value1;
    }

    public ExprNode(String name, Object value1, Object value2) {
        super();
        this.name = name;
        this.value1 = value1;
        this.value2 = value2;
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

    public Object getValue1() {
        return value1;
    }

    public void setValue1(Object value1) {
        this.value1 = value1;
    }

    public Object getValue2() {
        return value2;
    }

    public void setValue2(Object value2) {
        this.value2 = value2;
    }

    @Override
    public String toString() {
        return "ExprNode{" +
                "name='" + name + '\'' +
                ", value1=" + value1 +
                ", value2=" + value2 +
                ", name='" + name + '\'' +
                ", isLeaf=" + isLeaf +
                '}';
    }
}
