package it.esercitazione4.visitor;

import com.scalified.tree.multinode.ArrayMultiTreeNode;

public class VisitableNode<T> extends ArrayMultiTreeNode<T> implements Visitable {

    public VisitableNode(T data) {
        super(data);
    }

    @Override
    public String accept(Visitor visitor) {
        return visitor.visit(this);
    }

    /*
    public VisitableNode<T> firstChild(){
        return (VisitableNode<T>) this.subtrees[0];
    }

    public int numChild(){
        return this.subtrees().size();
    }

    public VisitableNode<T> getChild(int i ){
        return (VisitableNode<T>) this.subtrees[i];
    }
    */

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
            NE_OP = "LEOp",
            EQ_OP = "EQOp",

            INT_CONST = "int_const",
            FLOAT_CONST = "float_const",
            STRING_CONST = "string_const",
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
            PARAM_DECL_LIST_OP = "ParDeclOp",
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

            RETURN_EXPRS_OP = "ReturnExprsOp";

}