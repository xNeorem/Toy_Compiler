package it.esercitazione4.visitor;

import it.esercitazione4.nodes.AssignStatNode;
import it.esercitazione4.nodes.BoolConstLeaf;
import it.esercitazione4.nodes.CallProcNode;
import it.esercitazione4.nodes.ElifListNode;
import it.esercitazione4.nodes.ElifNode;
import it.esercitazione4.nodes.ElseNode;
import it.esercitazione4.nodes.ExprListNode;
import it.esercitazione4.nodes.ExprNode;
import it.esercitazione4.nodes.FloatConstLeaf;
import it.esercitazione4.nodes.IdLeaf;
import it.esercitazione4.nodes.IdListInitNode;
import it.esercitazione4.nodes.IdListNode;
import it.esercitazione4.nodes.IfStatNode;
import it.esercitazione4.nodes.IntConstLeaf;
import it.esercitazione4.nodes.Node;
import it.esercitazione4.nodes.NullConstLeaf;
import it.esercitazione4.nodes.ParDeclNode;
import it.esercitazione4.nodes.ParamDeclListNode;
import it.esercitazione4.nodes.ProcListNode;
import it.esercitazione4.nodes.ProcNode;
import it.esercitazione4.nodes.ProgramNode;
import it.esercitazione4.nodes.ReadlnStatNode;
import it.esercitazione4.nodes.ResultTypeListNode;
import it.esercitazione4.nodes.ResultTypeNode;
import it.esercitazione4.nodes.ReturnExprsNode;
import it.esercitazione4.nodes.StatListNode;
import it.esercitazione4.nodes.StringConstLeaf;
import it.esercitazione4.nodes.TypeDeclNode;
import it.esercitazione4.nodes.VarDeclListNode;
import it.esercitazione4.nodes.VarDeclNode;
import it.esercitazione4.nodes.WhileStatNode;
import it.esercitazione4.nodes.WriteStatNode;
import java.util.HashMap;

public class NewSemanticVisitor implements Visitor{

  @Override
  public Object visit(AssignStatNode node) {
    return null;
  }

  @Override
  public Object visit(BoolConstLeaf leaf) {
    return null;
  }

  @Override
  public Object visit(CallProcNode node) {
    return null;
  }

  @Override
  public Object visit(ElifListNode node) {
    return null;
  }

  @Override
  public Object visit(ElifNode node) {
    return null;
  }

  @Override
  public Object visit(ElseNode node) {
    return null;
  }

  @Override
  public Object visit(ExprListNode node) {
    return null;
  }

  @Override
  public Object visit(ExprNode node) {
    return null;
  }

  @Override
  public Object visit(FloatConstLeaf leaf) {
    return null;
  }

  @Override
  public Object visit(IdLeaf leaf) {
    return null;
  }

  @Override
  public Object visit(IdListInitNode node) {
    return null;
  }

  @Override
  public Object visit(IdListNode node) {
    return null;
  }

  @Override
  public Object visit(IfStatNode node) {
    return null;
  }

  @Override
  public Object visit(IntConstLeaf leaf) {
    return null;
  }

  @Override
  public Object visit(NullConstLeaf leaf) {
    return null;
  }

  @Override
  public Object visit(ParamDeclListNode node) {
    return null;
  }

  @Override
  public Object visit(ParDeclNode node) {
    return null;
  }

  @Override
  public Object visit(ProcListNode node) {
    return null;
  }

  @Override
  public Object visit(ProcNode node) {
    return null;
  }

  @Override
  public Object visit(ProgramNode node) {
    return null;
  }

  @Override
  public Object visit(ReadlnStatNode node) {
    return null;
  }

  @Override
  public Object visit(ResultTypeListNode node) {
    return null;
  }

  @Override
  public Object visit(ResultTypeNode node) {
    return null;
  }

  @Override
  public Object visit(ReturnExprsNode node) {
    return null;
  }

  @Override
  public Object visit(StatListNode node) {
    return null;
  }

  @Override
  public Object visit(StringConstLeaf leaf) {
    return null;
  }

  @Override
  public Object visit(TypeDeclNode node) {
    return null;
  }

  @Override
  public Object visit(VarDeclListNode node) {
    return null;
  }

  @Override
  public Object visit(VarDeclNode node) {
    return null;
  }

  @Override
  public Object visit(WhileStatNode node) {
    return null;
  }

  @Override
  public Object visit(WriteStatNode node) {
    return null;
  }

  public static final HashMap<KeyOpTypes,String> opTypes2 = new HashMap<>();
  static {
    // +-*/, INTEGER,INTEGER -> INTEGER
    KeyOpTypes key = new KeyOpTypes(Node.INT_CONST,Node.INT_CONST,Node.ADD_OP);
    opTypes2.put(key,Node.INT_CONST);

    key = new KeyOpTypes(Node.INT_CONST,Node.INT_CONST,Node.DIFF_OP);
    opTypes2.put(key,Node.INT_CONST);

    key = new KeyOpTypes(Node.INT_CONST,Node.INT_CONST,Node.MUL_OP);
    opTypes2.put(key,Node.INT_CONST);

    key = new KeyOpTypes(Node.INT_CONST,Node.INT_CONST,Node.DIV_OP);
    opTypes2.put(key,Node.INT_CONST);

    // +-*/, INTEGER,FLOAT -> FLOAT
    key = new KeyOpTypes(Node.INT_CONST,Node.FLOAT_CONST,Node.ADD_OP);
    opTypes2.put(key,Node.FLOAT_CONST);

    key = new KeyOpTypes(Node.INT_CONST,Node.FLOAT_CONST,Node.DIFF_OP);
    opTypes2.put(key,Node.FLOAT_CONST);

    key = new KeyOpTypes(Node.INT_CONST,Node.FLOAT_CONST,Node.MUL_OP);
    opTypes2.put(key,Node.FLOAT_CONST);

    key = new KeyOpTypes(Node.INT_CONST,Node.FLOAT_CONST,Node.DIV_OP);
    opTypes2.put(key,Node.FLOAT_CONST);

    // +-*/, FLOAT,FLOAT -> FLOAT
    key = new KeyOpTypes(Node.FLOAT_CONST,Node.FLOAT_CONST,Node.ADD_OP);
    opTypes2.put(key,Node.FLOAT_CONST);

    key = new KeyOpTypes(Node.FLOAT_CONST,Node.FLOAT_CONST,Node.DIFF_OP);
    opTypes2.put(key,Node.FLOAT_CONST);

    key = new KeyOpTypes(Node.FLOAT_CONST,Node.FLOAT_CONST,Node.MUL_OP);
    opTypes2.put(key,Node.FLOAT_CONST);

    key = new KeyOpTypes(Node.FLOAT_CONST,Node.FLOAT_CONST,Node.DIV_OP);
    opTypes2.put(key,Node.FLOAT_CONST);

    //and or, BOOLEAN, BOOLEAN -> BOOLEAN
    key = new KeyOpTypes(Node.BOOLEAN_CONST,Node.BOOLEAN_CONST,Node.AND_OP);
    opTypes2.put(key,Node.BOOLEAN_CONST);

    key = new KeyOpTypes(Node.BOOLEAN_CONST,Node.BOOLEAN_CONST,Node.OR_OP);
    opTypes2.put(key,Node.BOOLEAN_CONST);

    //< <= == > >=, BOOLEAN, BOOLEAN -> BOOLEAN
    key = new KeyOpTypes(Node.BOOLEAN_CONST,Node.BOOLEAN_CONST,Node.LT_OP);
    opTypes2.put(key,Node.BOOLEAN_CONST);

    key = new KeyOpTypes(Node.BOOLEAN_CONST,Node.BOOLEAN_CONST,Node.LE_OP);
    opTypes2.put(key,Node.BOOLEAN_CONST);

    key = new KeyOpTypes(Node.BOOLEAN_CONST,Node.BOOLEAN_CONST,Node.EQ_OP);
    opTypes2.put(key,Node.BOOLEAN_CONST);

    key = new KeyOpTypes(Node.BOOLEAN_CONST,Node.BOOLEAN_CONST,Node.GT_OP);
    opTypes2.put(key,Node.BOOLEAN_CONST);

    key = new KeyOpTypes(Node.BOOLEAN_CONST,Node.BOOLEAN_CONST,Node.GE_OP);
    opTypes2.put(key,Node.BOOLEAN_CONST);

    //< <= == > >=, INTEGER, INTEGER -> BOOLEAN
    key = new KeyOpTypes(Node.INT_CONST,Node.INT_CONST,Node.LT_OP);
    opTypes2.put(key,Node.BOOLEAN_CONST);

    key = new KeyOpTypes(Node.INT_CONST,Node.INT_CONST,Node.LE_OP);
    opTypes2.put(key,Node.BOOLEAN_CONST);

    key = new KeyOpTypes(Node.INT_CONST,Node.INT_CONST,Node.EQ_OP);
    opTypes2.put(key,Node.BOOLEAN_CONST);

    key = new KeyOpTypes(Node.INT_CONST,Node.INT_CONST,Node.GT_OP);
    opTypes2.put(key,Node.BOOLEAN_CONST);

    key = new KeyOpTypes(Node.INT_CONST,Node.INT_CONST,Node.GE_OP);
    opTypes2.put(key,Node.BOOLEAN_CONST);

    //< <= == > >=, INTEGER, FLOAT -> BOOLEAN
    key = new KeyOpTypes(Node.INT_CONST,Node.FLOAT_CONST,Node.LT_OP);
    opTypes2.put(key,Node.BOOLEAN_CONST);

    key = new KeyOpTypes(Node.INT_CONST,Node.FLOAT_CONST,Node.LE_OP);
    opTypes2.put(key,Node.BOOLEAN_CONST);

    key = new KeyOpTypes(Node.INT_CONST,Node.FLOAT_CONST,Node.EQ_OP);
    opTypes2.put(key,Node.BOOLEAN_CONST);

    key = new KeyOpTypes(Node.INT_CONST,Node.FLOAT_CONST,Node.GT_OP);
    opTypes2.put(key,Node.BOOLEAN_CONST);

    key = new KeyOpTypes(Node.INT_CONST,Node.FLOAT_CONST,Node.GE_OP);
    opTypes2.put(key,Node.BOOLEAN_CONST);

    //< <= == > >=, FLOAT, FLOAT -> BOOLEAN
    key = new KeyOpTypes(Node.FLOAT_CONST,Node.FLOAT_CONST,Node.LT_OP);
    opTypes2.put(key,Node.BOOLEAN_CONST);

    key = new KeyOpTypes(Node.FLOAT_CONST,Node.FLOAT_CONST,Node.LE_OP);
    opTypes2.put(key,Node.BOOLEAN_CONST);

    key = new KeyOpTypes(Node.FLOAT_CONST,Node.FLOAT_CONST,Node.EQ_OP);
    opTypes2.put(key,Node.BOOLEAN_CONST);

    key = new KeyOpTypes(Node.FLOAT_CONST,Node.FLOAT_CONST,Node.GT_OP);
    opTypes2.put(key,Node.BOOLEAN_CONST);

    key = new KeyOpTypes(Node.FLOAT_CONST,Node.FLOAT_CONST,Node.GE_OP);
    opTypes2.put(key,Node.BOOLEAN_CONST);

    //< <= == > >=, STRING, STRING -> BOOLEAN
    key = new KeyOpTypes(Node.STRING_CONST,Node.STRING_CONST,Node.LT_OP);
    opTypes2.put(key,Node.BOOLEAN_CONST);

    key = new KeyOpTypes(Node.STRING_CONST,Node.STRING_CONST,Node.LE_OP);
    opTypes2.put(key,Node.BOOLEAN_CONST);

    key = new KeyOpTypes(Node.STRING_CONST,Node.STRING_CONST,Node.EQ_OP);
    opTypes2.put(key,Node.BOOLEAN_CONST);

    key = new KeyOpTypes(Node.STRING_CONST,Node.STRING_CONST,Node.GT_OP);
    opTypes2.put(key,Node.BOOLEAN_CONST);

    key = new KeyOpTypes(Node.STRING_CONST,Node.STRING_CONST,Node.GE_OP);
    opTypes2.put(key,Node.BOOLEAN_CONST);

  };
}
