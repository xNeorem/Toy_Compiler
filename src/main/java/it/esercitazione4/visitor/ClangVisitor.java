package it.esercitazione4.visitor;

import it.esercitazione4.exceptions.TypeMismatchException;
import it.esercitazione4.nodes.*;
import it.esercitazione4.symboltable.EntrySymbolTable;
import it.esercitazione4.symboltable.TableStack;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ClangVisitor implements Visitor{

  private String clangCode;

  public ClangVisitor() {
    this.clangCode = "";
  }

  @Override
  public Object visit(AssignStatNode node) throws Exception {

    String code = "";

    ArrayList<IdLeaf> idLeaves = node.getIdListNode().getIdListNode();
    ArrayList<ExprNode> exprNodes = node.getExprListNode().getExprListNode();
    int size = idLeaves.size();

    for (int i = 0; i < size; i++){

      if (exprNodes.get(i).getName().equals(Node.CALL_PROC_OP)) {

        CallProcNode callProcNode = (CallProcNode) exprNodes.get(i).getValue1();
        ArrayList<String> returnsCall = new ArrayList<String>(
            Arrays.asList(callProcNode.getType().split(", ")));

        //code += "array = "+callProcNode.getIdLeaf().accept(this);
        String temp_param = null;
        if(returnsCall.size() > 1){
          temp_param = ClangVisitor.generateTempVariable();
          code += "void** "+ temp_param + "=";
        } else {
          code += idLeaves.get(i).accept(this) + " = ";
        }
        code += callProcNode.getIdLeaf().accept(this);
        code += "(";
        if(callProcNode.getExprListNode() != null){
          for(ExprNode exprNode: callProcNode.getExprListNode().getExprListNode())
            code += exprNode.accept(this) + ",";
          code = code.substring(0,code.length() - 1);
        }
        code+=");";

        if(returnsCall.size() > 1){

          for(int j = 0; j < idLeaves.size(); j++){
            String type = idLeaves.get(i).getType();
            if(type.equals("string"))
              type = "char*";

            code += idLeaves.get(i).accept(this) + " = "+ "*("+type+"*)"+temp_param+"["+j+"];";

            i++;
          }

          code += "free("+temp_param+");";
        }


      } else {
        code += idLeaves.get(i).accept(this) + "=" + exprNodes.get(i).accept(this) + ";";
      }
    }
    return code;
  }

  @Override
  public Object visit(BoolConstLeaf leaf) throws Exception {
    return String.valueOf(leaf.isValue()) + " ";
  }

  @Override
  public Object visit(CallProcNode node) throws Exception {
    String code = "";
    code += (String) node.getIdLeaf().accept(this);
    if(node.getExprListNode() != null)
      code += String.format("(%s)", (String)node.getExprListNode().accept(this));
    else
      code += "()";
    //code += ";";
    return code;
  }

  @Override
  public Object visit(ElifListNode node) throws Exception {
    String code = "";
    for(ElifNode elifNode : node.getElifListNode())
      code += (String) elifNode.accept(this);
    return code;
  }

  @Override
  public Object visit(ElifNode node) throws Exception {
    String code = "else if(";
    code += (String) node.getExprNode().accept(this);
    code += "){";
    code += (String) node.getStatiListNode().accept(this);
    code += "}";
    return code;
  }

  @Override
  public Object visit(ElseNode node) throws Exception {
    String code = "else {";
    code += (String) node.getStatListNode().accept(this);
    code += "}";
    return code;
  }

  @Override
  public Object visit(ExprListNode node) throws Exception {
    String code = "";
    for (ExprNode exprNode : node.getExprListNode()) {
      code += (String) exprNode.accept(this) + ", ";
    }
    code = code.substring(0, code.length()-2);
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
    return String.valueOf(leaf.getValue()) + " ";
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
    String code = "if(";
    code += (String) node.getExprNode().accept(this);
    code += "){";
    code += (String) node.getStatListNode().accept(this);
    code += "}";
    if(node.getElifListNode() != null)
      code += (String) node.getElifListNode().accept(this);
    if(node.getElseNode() != null)
      code += (String) node.getElseNode().accept(this);
    return code;
  }

  @Override
  public Object visit(IntConstLeaf leaf) throws Exception {
    return String.valueOf(leaf.getValue()) + " ";
  }

  @Override
  public Object visit(NullConstLeaf leaf) throws Exception {
    return "null ";
  }

  @Override
  public Object visit(ParamDeclListNode node) throws Exception {
    String code = "";
    for (ParDeclNode parDeclNode : node.getParamDeclListNode())
      code += parDeclNode.accept(this);
    code = code.substring(0, code.length()-2);
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
    //code = code.substring(0, code.length()-2);
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
    String code = "";

    TableStack.add(node.getSymbolTable());

    ArrayList<String> returnsCall = new ArrayList<String>(
        Arrays.asList(((String) node.getResultTypeListNode().accept(this)).split(" ")));

    if(returnsCall.size() == 1)
      code += (String) node.getResultTypeListNode().accept(this);
    else
      code += "void** ";

    String functionName = (String) node.getIdLeaf().accept(this);
    if(functionName.equals("main"))
      functionName = "main_func";
    code += functionName;

    code += "(";


    if(node.getParamDeclListNode() != null)
      code += (String) node.getParamDeclListNode().accept(this);
    code += "){";

    if(node.getVarDeclListNode() != null)
      code += (String) node.getVarDeclListNode().accept(this);

    String temp_param = null;
    if(returnsCall.size() > 1){

      temp_param = ClangVisitor.generateTempVariable();
      //code += "void *array["+returnsCall.size()+"];";
      code += "    void **"+temp_param+" = (void **)malloc(sizeof(void *) * "+returnsCall.size()+");"
          + "     if ("+temp_param+" == NULL) {"
          + "        printf(\"Memory not allocated for return values in "+ functionName+".\");"
          + "        exit(0);"
          + "    }";

    }

    if(node.getStatListNode() != null)
      code += (String) node.getStatListNode().accept(this);

    if(node.getReturnExprsNode() != null && returnsCall.size() == 1)
      code += (String) node.getReturnExprsNode().accept(this);

    else if(returnsCall.size() > 1){
      ArrayList<ExprNode> exprNodes = node.getReturnExprsNode().getExprListNode().getExprListNode();
      int exprCount = 0,i = 0;
      while (i < returnsCall.size()){

        if(exprNodes.get(exprCount).getName().equals(Node.CALL_PROC_OP) &&
            Arrays.asList(((CallProcNode) exprNodes.get(exprCount).getValue1()).getType().split(", ")).size() > 1){

          CallProcNode callProcNode = (CallProcNode) exprNodes.get(exprCount).getValue1();
          ArrayList<String> callProcReturns = new ArrayList<String>(
              Arrays.asList(callProcNode.getType().split(", ")));

          String tempCallProc = ClangVisitor.generateTempVariable();
          code += "void** "+tempCallProc + " = "+callProcNode.accept(this)+";";

          for(int j = 0; j < callProcReturns.size(); j++){
            String tempVar = ClangVisitor.generateTempVariable();
            String type = callProcReturns.get(j);
            if(type.equals("string"))
              type = "char*";

            code += type+" "+tempVar + " = "+ "*("+type+"*)"+tempCallProc+"["+j+"];";
            code += temp_param+"["+i+"]"+" = "+"&"+tempVar+";";
            i++;
          }

          code += "free("+tempCallProc+");";
        }
        else if(exprNodes.get(exprCount).getName().equals(Node.ID)){
          code += temp_param+"["+i+"]"+" = &"+exprNodes.get(exprCount).accept(this)+";";
          i++;
        }
        else{
          String new_temp = ClangVisitor.generateTempVariable();
          String expr_code = (String) exprNodes.get(exprCount).accept(this);

          code += returnsCall.get(i)+" "+new_temp+" = "+expr_code+";";
          code += temp_param+"["+i+"]"+" = &"+new_temp+";";
          i++;
        }

        exprCount++;

      }
      code += "return "+temp_param+";";
    }



    code += "}";

    TableStack.pop();
    return code;
  }

  @Override
  public Object visit(ProgramNode node) throws Exception {
    TableStack.add(node.getSymbolTable());
    String params = "";
    String code = "#include <stdio.h>\n";
    code += "#include <string.h>\n";
    code += "#include <stdlib.h>\n";
    code += "#include <stdbool.h>\n";
    code += "#define null NULL\n";

    if(node.getVarDeclListNode() != null)
      code += (String) node.getVarDeclListNode().accept(this);

    code += (String) node.getProcListNode().accept(this);

    code += "int main(int argc, char *argv[]){";
    EntrySymbolTable main = TableStack.lookUp("main");
    if(main.getTypeInput().size() > 0) {
      code += "if( argc != "+ (main.getTypeInput().size() + 1) + " ) {" +
              "      printf(\"Non sono stati forniti tutti gli argomenti necessari\\n\");return 0;" +
              "   }";
      for(int i=0; i < main.getTypeInput().size(); i++){
        String temp = ClangVisitor.generateTempVariable();
        if(main.getTypeInput().get(i).equals(Node.INT_CONST)) {
          code += "int "+ temp +"=atoi(argv[" + (i + 1) + "]);";
        } else if(main.getTypeInput().get(i).equals(Node.FLOAT_CONST)) {
          code += "float "+temp+"=atof(argv[" + (i + 1) + "]);";
        } else if(main.getTypeInput().get(i).equals(Node.BOOLEAN_CONST)) {
            code += "bool "+temp+"=atoi(argv["+(i+1)+"]);";
        } else {
          code += "char *"+temp+"=argv["+(i+1)+"];";
        }
        params += temp+", ";
      }
      params = params.substring(0, params.length()-2);
    }
    code +=  "main_func("+params+");return 0;}";
    clangCode = code;
    TableStack.pop();
    return null;
  }

  @Override
  public Object visit(ReadlnStatNode node) throws Exception {
    String code = "";
    String types = "";
    String params = "";
    for(IdLeaf idLeaf : node.getIdListNode().getIdListNode()){
      params += "&" + (String) idLeaf.accept(this) + ", ";
      types += ClangVisitor.ioConst.get(idLeaf.getType()) + " ";
    }
    params = params.substring(0, params.length()-2);
    types = types.substring(0, types.length()-1);
    code += String.format("scanf(\"%s\", %s);", types, params);
    return code;
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
    return "return " + (String) node.getExprListNode().accept(this) + ";";
  }

  @Override
  public Object visit(StatListNode node) throws Exception {
    String code = "";
    for (StatNode statNode : node.getStatListNode()){
      if(statNode == null) continue;

      if(statNode instanceof AssignStatNode){
        code += (String) ((AssignStatNode) statNode).accept(this);
      }
      if(statNode instanceof CallProcNode){
        String callProc = (String) ((CallProcNode)statNode).accept(this);
        if(Arrays.asList(((CallProcNode) statNode).getType().split(", ")).size() > 1)
          code += "free("+callProc+");";
        else
          code += callProc + ";";
      }
      if(statNode instanceof IfStatNode){
        code += (String) ((IfStatNode) statNode).accept(this);
      }
      if(statNode instanceof WhileStatNode){
        code += (String) ((WhileStatNode) statNode).accept(this);
      }
      if(statNode instanceof WriteStatNode){
        code += (String) ((WriteStatNode) statNode).accept(this);
      }
      if(statNode instanceof ReadlnStatNode){
        code += (String) ((ReadlnStatNode) statNode).accept(this);
      }
    }
    return code;
  }

  @Override
  public Object visit(StringConstLeaf leaf) throws Exception {
    return leaf.getValue().replace("\n","\\n") + " ";
  }

  @Override
  public Object visit(TypeDeclNode node) throws Exception {
    if(node.getValue().equals("string"))
      return "char* ";
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
    String type = (String) node.getTypeDeclNode().accept(this);
    code += type;
    //code += (String) node.getIdListInitNode().accept(this);
    for(Object obj : node.getIdListInitNode().getIdListInitNode()){

      if(obj instanceof IdLeaf){
        code += (String) ((IdLeaf)obj).accept(this);
        if(type.equals("char "))
          code += "[]";
      }

      else if(obj instanceof AssignStatNode){
        ArrayList<IdLeaf> idLeaves = ((AssignStatNode) obj).getIdListNode().getIdListNode();
        ArrayList<ExprNode> exprNodes = ((AssignStatNode) obj).getExprListNode().getExprListNode();
        code += (String) idLeaves.get(0).accept(this);
        if(type.equals("char "))
          code += "[]";
        code += ClangVisitor.operators.get(Node.ASSIGN_OP);
        code += (String) exprNodes.get(0).accept(this);
      }

      code += ", ";
    }
    code = code.substring(0, code.length()-2);
    code += ';';
    return code;
  }

  @Override
  public Object visit(WhileStatNode node) throws Exception {
    String code = "";
    String statListNode1 = "";

    if(node.getStatListNode1() != null)
      statListNode1 += (String) node.getStatListNode1().accept(this);

    code += statListNode1;
    code += "while(" + (String) node.getExprNode().accept(this) + "){";
    code += (String) node.getStatListNode2().accept(this);
    code += statListNode1;
    code += "}";
    return code;
  }

  @Override
  public Object visit(WriteStatNode node) throws Exception {
    String code = "";
    String types = "";
    String params = "";
    String temp_param = null;
    for(ExprNode exprNode : node.getExprListNode().getExprListNode()){
      if(exprNode.getName().equals(Node.CALL_PROC_OP) && Arrays.asList(((CallProcNode) exprNode.getValue1()).getType().split(", ")).size() > 1){
        CallProcNode callProcNode = (CallProcNode) exprNode.getValue1();
        ArrayList<String> returnsCall = new ArrayList<String>(
                Arrays.asList(callProcNode.getType().split(", ")));
        temp_param = ClangVisitor.generateTempVariable();
        code += "void** "+ temp_param + "=";
        code += callProcNode.getIdLeaf().accept(this);
        code += "(";
        if(callProcNode.getExprListNode() != null){
          for(ExprNode exprNodeIntern: callProcNode.getExprListNode().getExprListNode())
            code += exprNodeIntern.accept(this) + ",";
          code = code.substring(0,code.length() - 1);
        }
        code+=");";

        for(int i = 0; i < returnsCall.size(); i++){
          String type = returnsCall.get(i);
          if(type.equals("string"))
            type = "char*";
          params += "*("+type+"*)"+temp_param+"["+i+"],";
          types += ClangVisitor.ioConst.get(type.equals("char*") ? "string" : type);
        }
      } else {
        params += (String) exprNode.accept(this) + ",";
        types += ClangVisitor.ioConst.get(exprNode.getType());
      }
    }
    params = params.substring(0, params.length()-1);

    code += String.format("printf(\"%s\", %s);", types, params);
    if(temp_param != null) code += "free("+temp_param+");";
    return code;
  }

  public String saveC(String fileName){
    String cname = fileName.substring(0, fileName.length()-4).split("/")[3]+ ".c";
    try {
      FileWriter myWriter = new FileWriter(cname);
      myWriter.write(clangCode);
      myWriter.close();
      //Runtime.getRuntime().exec("java -jar /Users/salvatorefasano/.m2/repository/com/github/abrarsyed/jastyle/jAstyle/1.3/jAstyle-1.3.jar " + cname);
      System.out.println("Successfully wrote to the file.");
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
    return cname;
  }

  private static final String generateTempVariable(){
    int i = 0;
    String temp = String.format("t_%d",i);
    while (TableStack.lookUp(temp) != null)
      temp = String.format("t_%d",++i);

    TableStack.getHead().addToTable(temp,"TEMP");

    return temp;
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
  public static final HashMap<String, String> ioConst = new HashMap<>();
  static {
    ioConst.put(Node.INT_CONST, "%d");
    ioConst.put(Node.FLOAT_CONST, "%f");
    ioConst.put(Node.STRING_CONST, "%s");
    ioConst.put(Node.BOOLEAN_CONST, "%d");
  }
}
