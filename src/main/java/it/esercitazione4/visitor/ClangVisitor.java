package it.esercitazione4.visitor;

import it.esercitazione4.nodes.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class ClangVisitor implements Visitor{

  private String clangCode;

  public ClangVisitor() {
    this.clangCode = "";
  }

  @Override
  public Object visit(AssignStatNode node) throws Exception {
    String code = "";
    code += (String) node.getIdListNode().accept(this);
    code += (String) node.getExprListNode().accept(this);
    return code;
  }

  @Override
  public Object visit(BoolConstLeaf leaf) throws Exception {
    return "int ";
  }

  @Override
  public Object visit(CallProcNode node) throws Exception {
    return null;
  }

  @Override
  public Object visit(ElifListNode node) throws Exception {
    return null;
  }

  @Override
  public Object visit(ElifNode node) throws Exception {
    return null;
  }

  @Override
  public Object visit(ElseNode node) throws Exception {
    return null;
  }

  @Override
  public Object visit(ExprListNode node) throws Exception {
    String code = "";
    for (ExprNode exprNode : node.getExprListNode()) {
      code += (String) exprNode.accept(this);
      code += ";";
    }
    return code;
  }

  @Override
  public Object visit(ExprNode node) throws Exception {
    String nodeName = node.getName();
    String code = "";
    switch (nodeName){
      case Node.NULL_CONST:
        code = (String) ((NullConstLeaf)node.getValue1()).accept(this);
        break;
      case Node.TRUE_CONST:
      case Node.FALSE_CONST:
        code = (String) ((BoolConstLeaf)node.getValue1()).accept(this) ;
        break;
      case Node.INT_CONST:
        code = (String) ((IntConstLeaf)node.getValue1()).accept(this);
        break;
      case Node.FLOAT_CONST:
        code = (String) ((FloatConstLeaf)node.getValue1()).accept(this);
        break;
      case Node.STRING_CONST:
        code = (String) ((StringConstLeaf)node.getValue1()).accept(this);
        break;
      case Node.ID:
        code = (String) ((IdLeaf)node.getValue1()).accept(this);
        break;
      case Node.CALL_PROC_OP:
        code = (String) ((CallProcNode)node.getValue1()).accept(this);
        break;
      case Node.ADD_OP:
      case Node.DIFF_OP:
      case Node.MUL_OP:
      case Node.DIV_OP:
      case Node.AND_OP:
      case Node.OR_OP:
      case Node.GT_OP:
      case Node.GE_OP:
      case Node.LT_OP:
      case Node.LE_OP:
      case Node.EQ_OP:
      case Node.NE_OP:
        code += (String) ((ExprNode)node.getValue1()).accept(this);
        code += ClangVisitor.operators.get(nodeName);
        code += (String) ((ExprNode)node.getValue2()).accept(this);
        break;
      case Node.UMINUS_OP:
      case Node.NOT_OP:
      case Node.ASSIGN_OP:
        code = ClangVisitor.operators.get(nodeName);
        code += (String) ((ExprNode)node.getValue1()).accept(this);
        break;
    }
    return code;
  }

  @Override
  public Object visit(FloatConstLeaf leaf) throws Exception {
    return "float ";
  }

  @Override
  public Object visit(IdLeaf leaf) throws Exception {
    return leaf.getValue();
  }

  @Override
  public Object visit(IdListInitNode node) throws Exception {
    String code = "";
    for (Object obj : node.getIdListInitNode()){
      if(obj instanceof IdLeaf){
        code += (String) ((IdLeaf) obj).accept(this);
      }
      else if( obj instanceof AssignStatNode){
        code += (String) ((AssignStatNode) obj).accept(this);
      }
    }
    return code;
  }

  @Override
  public Object visit(IdListNode node) throws Exception {
    String code = "";
    for (IdLeaf idLeaf : node.getIdListNode()) {
      code += (String) idLeaf.accept(this);

    }
    return code;
  }

  @Override
  public Object visit(IfStatNode node) throws Exception {
    return null;
  }

  @Override
  public Object visit(IntConstLeaf leaf) throws Exception {
    return "int ";
  }

  @Override
  public Object visit(NullConstLeaf leaf) throws Exception {
    return "null ";
  }

  @Override
  public Object visit(ParamDeclListNode node) throws Exception {
    String code = "(";
    for (ParDeclNode parDeclNode : node.getParamDeclListNode())
      code += parDeclNode.accept(this);
    //code = code.substring(0, code.length()-1);
    code += ")";
    return code;
  }

  @Override
  public Object visit(ParDeclNode node) throws Exception {
    String code = "";
    String type = (String) node.getTypeDeclNode().accept(this);
    for(IdLeaf idLeaf : node.getIdListNode().getIdListNode()) {
      code += type;
      code += (String) idLeaf.accept(this)  + ", ";
    }
    code = code.substring(0, code.length()-2);
    return code;
  }

  @Override
  public Object visit(ProcListNode node) throws Exception {
    String code = "";
    for(ProcNode procNode: node.getProcListNode())
      code += (String) procNode.accept(this);
    return code;
  }

  @Override
  public Object visit(ProcNode node) throws Exception {
    String code = (String) node.getResultTypeListNode().accept(this);

    code += (String) node.getIdLeaf().accept(this);

    if(node.getParamDeclListNode() != null)
      code += (String) node.getParamDeclListNode().accept(this);
    else
      code += "()";

    code += "{";

    if(node.getVarDeclListNode() != null)
      code += (String) node.getVarDeclListNode().accept(this);

    if(node.getStatListNode() != null)
      code += (String) node.getStatListNode().accept(this);

    if(node.getReturnExprsNode() != null)
      code += (String) node.getReturnExprsNode().accept(this);

    code += "}\n";

    return code;
  }

  @Override
  public Object visit(ProgramNode node) throws Exception {
    String code = "#include <stdio.h>\n";
    code += "#include <string.h>\n";
    code += "#define null ((char *)0)\n";
    code += "#define true 1\n";
    code += "#define false 0\n";

    if(node.getVarDeclListNode() != null)
      code += (String) node.getVarDeclListNode().accept(this);

    code += (String) node.getProcListNode().accept(this);
    clangCode = code;
    return null;
  }

  @Override
  public Object visit(ReadlnStatNode node) throws Exception {
    return null;
  }

  @Override
  public Object visit(ResultTypeListNode node) throws Exception {
    String code = "";
    for(ResultTypeNode resultTypeNode : node.getResultTypeListNode())
      code += (String) resultTypeNode.accept(this);
    return code;
  }

  @Override
  public Object visit(ResultTypeNode node) throws Exception {
    String code = "";
    if(node.isVoid())
      code += "void ";
    else
      code += (String) node.getTypeDeclNode().accept(this);
    return code;
  }

  @Override
  public Object visit(ReturnExprsNode node) throws Exception {
    return null;
  }

  @Override
  public Object visit(StatListNode node) throws Exception {
    return null;
  }

  @Override
  public Object visit(StringConstLeaf leaf) throws Exception {
    return null;
  }

  @Override
  public Object visit(TypeDeclNode node) throws Exception {
    return node.getValue() + " ";
  }

  @Override
  public Object visit(VarDeclListNode node) throws Exception {
    String code = "";
    for (VarDeclNode varDeclNode : node.getVarDeclListNode())
      code += (String) varDeclNode.accept(this);
    return code;
  }

  @Override
  public Object visit(VarDeclNode node) throws Exception {
    String code = "";
    code += (String) node.getTypeDeclNode().accept(this);
    code += (String) node.getIdListInitNode().accept(this);
    return code;
  }

  @Override
  public Object visit(WhileStatNode node) throws Exception {
    return null;
  }

  @Override
  public Object visit(WriteStatNode node) throws Exception {
    return null;
  }

  public void saveC(String fileName){
    try {
      FileWriter myWriter = new FileWriter(fileName);
      myWriter.write(clangCode);
      myWriter.close();
      System.out.println("Successfully wrote to the file.");
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }

  public static final HashMap<String, String> operators = new HashMap<>();
  static {
    operators.put(Node.ADD_OP, "+");
    operators.put(Node.DIFF_OP, "-");
    operators.put(Node.MUL_OP, "*");
    operators.put(Node.DIV_OP, "/");
    operators.put(Node.AND_OP, "&&");
    operators.put(Node.OR_OP, "||");
    operators.put(Node.GT_OP, ">");
    operators.put(Node.GE_OP, ">=");
    operators.put(Node.LT_OP, "<");
    operators.put(Node.LE_OP, "<=");
    operators.put(Node.EQ_OP, "==");
    operators.put(Node.NE_OP, "!=");
    operators.put(Node.UMINUS_OP, "-");
    operators.put(Node.NOT_OP, "!");
    operators.put(Node.ASSIGN_OP, "=");
  }
}
