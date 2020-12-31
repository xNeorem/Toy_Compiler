package it.esercitazione4.visitor;


public interface Visitor {
    public Object visit(Visitable node) throws Exception;
}
