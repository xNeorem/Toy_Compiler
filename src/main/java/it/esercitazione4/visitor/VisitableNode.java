package it.esercitazione4.visitor;

import com.scalified.tree.TreeNode;
import com.scalified.tree.multinode.ArrayMultiTreeNode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

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
        return (VisitableNode<T>) this.subtrees()[0];
    }

    public int numChild(){
        return this.subtrees().size();
    }

    public VisitableNode<T> getChild(int i ){
        return (VisitableNode<T>) this.subtrees()[i];
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
            EQ_OP = "EQOp",
            INT_CONST = "int_const",
            FLOAT_CONST = "float_const",
            STRING_CONST = "string_const",
            NULL_CONST = "null",
            TRUE_CONST = "true",
            FALSE_CONST = "false",
            ID = "identifier",

            IF_STAT_OP = "IfStatOp",
            ELIF_OP = "ElifOp",
            WHILE_OP = "WhileOp",
            CALL_PROC_OP = "CallProcOp",
            VAR_DECL_OP = "VarDeclOp",
            BODY_OP = "BodyOp",
            PAR_DECL_OP = "ParDeclOp",
            PROC_BODY_OP = "ProcBodyOp",
            PROC_OP = "ProcOp",
            PROGRAM_OP = "ProgramOp",

            ASSIGN_OP = "AssignOp",
            WRITE_OP = "WriteOp",
            READ_OP = "ReadOp";
}

/*          EXPR_NODE = "ExprOp",
            CONSTANT_NODE = "constant",
            CONST_NODE = "ConstOp",
            VAR_NODE = "VarOp",
            MUL_NODE = "multiplying_operator",
            BLOCK_OP = "BlockOp",
            ADD_NODE = "adding_operator",
            REL_NODE = "relational_operator",
            REL_OP = "RelationalOp",
            SIMPLE_NODE = "SimpleExprOp",
            IF_THEN_ELSE_NODE = "IfThenElseOp",
            IF_THEN_NODE = "IfThenOp",
            CALL_OP_NODE = "CallOp",
            COMP_NODE = "CompStatOp",
            SIMPLE_BLOCK_NODE = "SimpleBlockOp",
            PROC_NODE = "ProcDeclOp",
            PROC_DECL_PART = "ProcDeclPartOp",
            PROGRAM_NODE = "ProgramOp",
            VAR_DECL_PART_NODE = "VarDeclPartOp";
* */