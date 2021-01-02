package it.esercitazione4.nodes;

import it.esercitazione4.symboltable.SymbolTable;
import it.esercitazione4.visitor.Visitable;
import it.esercitazione4.visitor.Visitor;

public class ProcNode extends Node implements Visitable {
    private IdLeaf idLeaf;
    private ParamDeclListNode paramDeclListNode;
    private ResultTypeListNode resultTypeListNode;
    private VarDeclListNode varDeclListNode;
    private StatListNode statListNode;
    private ReturnExprsNode returnExprsNode;
    private SymbolTable symbolTable;

    public ProcNode(IdLeaf idLeaf, ParamDeclListNode paramDeclListNode, ResultTypeListNode resultTypeListNode, VarDeclListNode varDeclListNode, StatListNode statListNode, ReturnExprsNode returnExprsNode) {
        super();
        this.name = Node.PROC_OP;
        this.idLeaf = idLeaf;
        this.paramDeclListNode = paramDeclListNode;
        this.resultTypeListNode = resultTypeListNode;
        this.varDeclListNode = varDeclListNode;
        this.statListNode = statListNode;
        this.returnExprsNode = returnExprsNode;
        this.symbolTable = new SymbolTable();
    }

    public ProcNode(IdLeaf idLeaf, ResultTypeListNode resultTypeListNode, VarDeclListNode varDeclListNode, StatListNode statListNode, ReturnExprsNode returnExprsNode) {
        super();
        this.name = Node.PROC_OP;
        this.idLeaf = idLeaf;
        this.resultTypeListNode = resultTypeListNode;
        this.varDeclListNode = varDeclListNode;
        this.statListNode = statListNode;
        this.returnExprsNode = returnExprsNode;
        this.symbolTable = new SymbolTable();
    }

    public ProcNode(IdLeaf idLeaf, ParamDeclListNode paramDeclListNode, ResultTypeListNode resultTypeListNode, VarDeclListNode varDeclListNode, ReturnExprsNode returnExprsNode) {
        super();
        this.name = Node.PROC_OP;
        this.idLeaf = idLeaf;
        this.paramDeclListNode = paramDeclListNode;
        this.resultTypeListNode = resultTypeListNode;
        this.varDeclListNode = varDeclListNode;
        this.returnExprsNode = returnExprsNode;
        this.symbolTable = new SymbolTable();
    }

    public ProcNode(IdLeaf idLeaf, ResultTypeListNode resultTypeListNode, VarDeclListNode varDeclListNode, ReturnExprsNode returnExprsNode) {
        super();
        this.name = Node.PROC_OP;
        this.idLeaf = idLeaf;
        this.resultTypeListNode = resultTypeListNode;
        this.varDeclListNode = varDeclListNode;
        this.returnExprsNode = returnExprsNode;
        this.symbolTable = new SymbolTable();
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    public IdLeaf getIdLeaf() {
        return idLeaf;
    }

    public void setIdLeaf(IdLeaf idLeaf) {
        this.idLeaf = idLeaf;
    }

    public ParamDeclListNode getParamDeclListNode() {
        return paramDeclListNode;
    }

    public void setParamDeclListNode(ParamDeclListNode paramDeclListNode) {
        this.paramDeclListNode = paramDeclListNode;
    }

    public ResultTypeListNode getResultTypeListNode() {
        return resultTypeListNode;
    }

    public void setResultTypeListNode(ResultTypeListNode resultTypeListNode) {
        this.resultTypeListNode = resultTypeListNode;
    }

    public VarDeclListNode getVarDeclListNode() {
        return varDeclListNode;
    }

    public void setVarDeclListNode(VarDeclListNode varDeclListNode) {
        this.varDeclListNode = varDeclListNode;
    }

    public StatListNode getStatListNode() {
        return statListNode;
    }

    public void setStatListNode(StatListNode statListNode) {
        this.statListNode = statListNode;
    }

    public ReturnExprsNode getReturnExprsNode() {
        return returnExprsNode;
    }

    public void setReturnExprsNode(ReturnExprsNode returnExprsNode) {
        this.returnExprsNode = returnExprsNode;
    }

    @Override
    public String toString() {
        return "ProcNode{" +
                "idLeaf=" + idLeaf +
                ", paramDeclListNode=" + paramDeclListNode +
                ", resultTypeListNode=" + resultTypeListNode +
                ", varDeclListNode=" + varDeclListNode +
                ", statListNode=" + statListNode +
                ", returnExprsNode=" + returnExprsNode +
                ", name='" + name + '\'' +
                ", isLeaf=" + isLeaf +
                '}';
    }
}
