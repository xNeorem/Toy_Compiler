package it.esercitazione4.nodes;

import java_cup.runtime.Symbol;

public class Node {
    protected String name;
    protected boolean isLeaf = false;
    protected String type;

    public Node() {
        this.name = Node.NODE_BASE;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    public String getType() {
      return type;
    }

    public void setType(String type) {
      this.type = type;
    }

  @Override
  public String toString() {
    return "Node{" +
        "name='" + name + '\'' +
        ", isLeaf=" + isLeaf +
        ", type='" + type + '\'' +
        '}';
  }

  public static final String
            UMINUS_OP = "UminusOp",
            ADD_OP = "AddOp",
            MUL_OP = "MulOp",
            DIFF_OP = "DiffOp",
            DIV_OP = "DivOP",
            AND_OP = "AndOp",
            NOT_OP = "NotOp",
            OR_OP = "OrOp",
            GT_OP = "GTOp",
            GE_OP = "GEOp",
            LT_OP = "LTOp",
            LE_OP = "LEOp",
            NE_OP = "NEOp",
            EQ_OP = "EQOp",

            INT_CONST = "int",
            FLOAT_CONST = "float",
            STRING_CONST = "string",
            BOOLEAN_CONST = "bool",
            NULL_CONST = "null",
            TRUE_CONST = "true",
            FALSE_CONST = "false",

            TYPE_DECL = "TypeDecl",
            RESULT_TYPE = "ResultType",

            ID = "identifier",
            STAT_LIST_OP = "StatListOp",
            IF_OP = "IfOp",
            ELSE_OP = "ElseOp",
            ELIF_OP = "ElifOp",
            ELIF_LIST_OP = "ElifListOp",
            WHILE_OP = "WhileOp",
            CALL_PROC_OP = "CallProcOp",
            VAR_DECL_OP = "VarDeclOp",
            VAR_DECL_LIST_OP = "VarDeclListOp",
            PAR_DECL_OP = "ParDeclOp",
            PARAM_DECL_LIST_OP = "ParDeclListOp",
            PROC_LIST_OP = "ProcListOp",
            PROC_OP = "ProcOp",
            PROGRAM_OP = "ProgramOp",

            ASSIGN_OP = "AssignOp",
            WRITE_OP = "WriteOp",
            READ_OP = "ReadOp",

            EXPR_LIST_OP = "ExprListOp",
            RESULT_TYPE_LIST_OP = "ResultTypeListOp",
            ID_LIST_OP = "IdListOp",
            ID_LIST_INIT_OP = "IdListInitOp",

            EXPR_OP = "ExprOp",
            NODE_BASE = "NodeBase",
            RETURN_EXPRS_OP = "ReturnExprsOp";
}
