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
    static public boolean createTable(Node node){

        if (!(node.name.equals(VisitableNode.PROC_OP) || node.name.equals(VisitableNode.PROGRAM_OP)))
            return false;
        node.symbolTable = new SymbolTable();
        return true;
    }

    public boolean hasTable(){
        return (this.symbolTable != null);
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    @Override
    public String toString() {
        return "Node{" +
                "name='" + name + '\'' +
                ", value=" + value +
                ", type='" + type + '\'' +
                '}';
    }

    private String name;
    private Object value;
    private String type;
    private SymbolTable symbolTable;
}
