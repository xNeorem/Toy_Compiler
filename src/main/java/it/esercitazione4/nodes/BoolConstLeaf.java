package it.esercitazione4.nodes;

import it.esercitazione4.visitor.Visitable;
import it.esercitazione4.visitor.Visitor;

public class BoolConstLeaf extends Node implements Visitable {
    private boolean value;

    public BoolConstLeaf(boolean value) {
        super();
        this.name = Node.BOOLEAN_CONST;
        this.isLeaf = true;
        this.value = value;
    }

    @Override
    public Object accept(Visitor visitor)  throws Exception{
        return visitor.visit(this);
    }

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "BoolConstLeaf{" +
            "value=" + value +
            ", name='" + name + '\'' +
            ", isLeaf=" + isLeaf +
            '}';
    }
}
