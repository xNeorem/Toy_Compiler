package it.esercitazione4.visitor;

public interface Visitable {
    public String accept(Visitor visitor);
}
