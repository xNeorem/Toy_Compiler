package it.esercitazione4.visitor;

public interface Visitable {
    public Object accept(Visitor visitor);
}
