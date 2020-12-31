package it.esercitazione4.visitor;

import it.esercitazione4.symboltable.SymbolTable;

public class Node {
    public Node(String name) {
        this.name = name;
    }

    public Node(String name, Object value) {
        this.name = name;
        this.value = value;

    }
    static public Boolean createTable(Node node){

        if (!(node.name.equals(VisitableNode.PROC_OP) || node.name.equals(VisitableNode.PROGRAM_OP)))
            return false;

        node.symbolTable = new SymbolTable();
        return true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    @Override
    public String toString() {
        return "Node{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }

    private String name;
    private Object value;
    private SymbolTable symbolTable;
}
