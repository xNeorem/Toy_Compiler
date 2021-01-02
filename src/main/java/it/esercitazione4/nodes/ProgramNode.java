package it.esercitazione4.nodes;

import it.esercitazione4.symboltable.SymbolTable;
import it.esercitazione4.visitor.Visitable;
import it.esercitazione4.visitor.Visitor;

public class ProgramNode extends Node implements Visitable {
    private VarDeclListNode varDeclListNode;
    private ProcListNode procListNode;
    private SymbolTable symbolTable;

    public ProgramNode(VarDeclListNode varDeclListNode, ProcListNode procListNode) {
        super();
        this.name = Node.PROGRAM_OP;
        this.varDeclListNode = varDeclListNode;
        this.procListNode = procListNode;
        this.symbolTable = new SymbolTable();
    }
    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

    public VarDeclListNode getVarDeclListNode() {
        return varDeclListNode;
    }

    public void setVarDeclListNode(VarDeclListNode varDeclListNode) {
        this.varDeclListNode = varDeclListNode;
    }

    public ProcListNode getProcListNode() {
        return procListNode;
    }

    public void setProcListNode(ProcListNode procListNode) {
        this.procListNode = procListNode;
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    @Override
    public String toString() {
        return "ProgramNode{" +
                "varDeclListNode=" + varDeclListNode +
                ", procListNode=" + procListNode +
                ", name='" + name + '\'' +
                ", isLeaf=" + isLeaf +
                '}';
    }
}
