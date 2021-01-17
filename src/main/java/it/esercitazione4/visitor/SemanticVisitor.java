package it.esercitazione4.visitor;

import it.esercitazione4.exceptions.CallProcException;
import it.esercitazione4.exceptions.ReturnParamsException;
import it.esercitazione4.exceptions.TypeMismatchException;
import it.esercitazione4.exceptions.UndeclaredException;
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
import it.esercitazione4.nodes.StatNode;
import it.esercitazione4.nodes.StringConstLeaf;
import it.esercitazione4.nodes.TypeDeclNode;
import it.esercitazione4.nodes.VarDeclListNode;
import it.esercitazione4.nodes.VarDeclNode;
import it.esercitazione4.nodes.WhileStatNode;
import it.esercitazione4.nodes.WriteStatNode;
import it.esercitazione4.symboltable.EntrySymbolTable;
import it.esercitazione4.symboltable.TableStack;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class SemanticVisitor implements Visitor{

  @Override
  public Object visit(AssignStatNode node) throws Exception {

    node.getIdListNode().accept(this);
    node.getExprListNode().accept(this);
    ArrayList<IdLeaf> idLeaves = node.getIdListNode().getIdListNode();
    ArrayList<ExprNode> exprNodes = node.getExprListNode().getExprListNode();


    int size = idLeaves.size();

    for (int i = 0; i < size; i++){

      if (exprNodes.get(i).getName().equals(Node.CALL_PROC_OP)) {

        CallProcNode callProcNode = (CallProcNode) exprNodes.get(i).getValue1();
        ArrayList<String> returnsCall = new ArrayList<String>(
            Arrays.asList(callProcNode.getType().split(", ")));

        for(String returns : returnsCall){
          if(i < size){
            if (idLeaves.get(i).getType().equals(returns)) {
              i++;
            } else {
              throw new TypeMismatchException();
            }
          }
        }


      } else if (!exprNodes.get(i).getType().contains(idLeaves.get(i).getType())) {
        throw new TypeMismatchException();
      }
    }


    return null;
  }

  @Override
  public Object visit(BoolConstLeaf leaf) {
    TableStack.getHead().addToTable(String.valueOf(leaf.isValue()),Node.BOOLEAN_CONST);
    leaf.setType(Node.BOOLEAN_CONST);
    return null;
  }

  @Override
  public Object visit(CallProcNode node) throws Exception {
    String functionName = node.getIdLeaf().getValue();
    EntrySymbolTable function = TableStack.lookUp(functionName);

    if(function == null)
      throw new UndeclaredException(functionName);

    node.setType(function.getTypeOutput()
        .toString()
        .replace("[","")
        .replace("]","")
    );

    if(node.getExprListNode() != null){

      node.getExprListNode().accept(this);

      ArrayList<String> inputTypes = function.getTypeInput();
      ArrayList<ExprNode> nodeTypes = node.getExprListNode().getExprListNode();

      if(inputTypes.size() != nodeTypes.size())
        throw new CallProcException(functionName);

      int size = inputTypes.size();

      for(int i = 0; i < size; i++){
        if(!nodeTypes.get(i).getType().contains(inputTypes.get(i)))
          throw new CallProcException(functionName);
      }

    }


    return null;
  }

  @Override
  public Object visit(ElifListNode node) throws Exception {
    for(ElifNode elifNode : node.getElifListNode())
      elifNode.accept(this);
    return null;
  }

  @Override
  public Object visit(ElifNode node) throws Exception {
    node.getExprNode().accept(this);
    if(!node.getExprNode().getType().equals(Node.BOOLEAN_CONST))
      throw new TypeMismatchException();
    node.getStatiListNode().accept(this);
    return null;
  }

  @Override
  public Object visit(ElseNode node) throws Exception {
    node.getStatListNode().accept(this);
    return null;
  }

  @Override
  public Object visit(ExprListNode node) throws Exception {

    for(ExprNode exprNode : node.getExprListNode())
      exprNode.accept(this);
    return null;
  }

  @Override
  public Object visit(ExprNode node) throws Exception {

    String nodeName = node.getName();
    switch (nodeName){
      case Node.NULL_CONST:
        ((NullConstLeaf)node.getValue1()).accept(this);
        node.setType(((NullConstLeaf)node.getValue1()).getType());
        break;
      case Node.TRUE_CONST:
      case Node.FALSE_CONST:
        ((BoolConstLeaf)node.getValue1()).accept(this);
        node.setType(((BoolConstLeaf)node.getValue1()).getType());
        break;
      case Node.INT_CONST:
        ((IntConstLeaf)node.getValue1()).accept(this);
        node.setType(((IntConstLeaf)node.getValue1()).getType());
        break;
      case Node.FLOAT_CONST:
        ((FloatConstLeaf)node.getValue1()).accept(this);
        node.setType(((FloatConstLeaf)node.getValue1()).getType());
        break;
      case Node.STRING_CONST:
        ((StringConstLeaf)node.getValue1()).accept(this);
        node.setType(((StringConstLeaf)node.getValue1()).getType());
        break;
      case Node.ID:
        ((IdLeaf)node.getValue1()).accept(this);
        node.setType(((IdLeaf)node.getValue1()).getType());
        break;
      case Node.CALL_PROC_OP:
        ((CallProcNode)node.getValue1()).accept(this);
        node.setType(((CallProcNode)node.getValue1()).getType());
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
        ((ExprNode)node.getValue1()).accept(this);
        ((ExprNode)node.getValue2()).accept(this);

        String type1 = ((ExprNode)node.getValue1()).getType();
        String type2 = ((ExprNode)node.getValue2()).getType();

        String result1 = SemanticVisitor.opTypes2.get(new KeyOpTypes(type1,type2,nodeName));
        String result2 = SemanticVisitor.opTypes2.get(new KeyOpTypes(type2,type1,nodeName));

        if(result1 != null)
          node.setType(result1);
        else if(result2 != null)
          node.setType(result2);
        else
          throw new TypeMismatchException();

        break;
      case Node.UMINUS_OP:
      case Node.NOT_OP:
      case Node.ASSIGN_OP:
        ((ExprNode)node.getValue1()).accept(this);
        node.setType(((ExprNode)node.getValue1()).getType());
        break;

    }

    return null;
  }

  @Override
  public Object visit(FloatConstLeaf leaf) {
    TableStack.getHead().addToTable(String.valueOf(leaf.getValue()),Node.FLOAT_CONST);
    leaf.setType(Node.FLOAT_CONST);
    return null;
  }

  @Override
  public Object visit(IdLeaf leaf) throws Exception {
    if(TableStack.lookUp(leaf.getValue()) == null)
      throw new UndeclaredException(leaf.getValue());

    leaf.setType(TableStack.lookUp(leaf.getValue()).getType());
    return null;
  }

  @Override
  public Object visit(IdListInitNode node) {
    return null;
  }

  @Override
  public Object visit(IdListNode node) throws Exception {
    for(IdLeaf idLeaf: node.getIdListNode())
      idLeaf.accept(this);
    return null;
  }

  @Override
  public Object visit(IfStatNode node) throws Exception {
    node.getExprNode().accept(this);
    if(!node.getExprNode().getType().equals(Node.BOOLEAN_CONST))
      throw new TypeMismatchException();
    node.getStatListNode().accept(this);
    if(node.getElifListNode() != null)
      node.getElifListNode().accept(this);
    if(node.getElseNode() != null)
      node.getElseNode().accept(this);
    return null;
  }

  @Override
  public Object visit(IntConstLeaf leaf) {
    TableStack.getHead().addToTable(String.valueOf(leaf.getValue()),Node.INT_CONST);
    leaf.setType(Node.INT_CONST);
    return null;
  }

  @Override
  public Object visit(NullConstLeaf leaf) {
    TableStack.getHead().addToTable("null",Node.NULL_CONST);
    leaf.setType(Node.NULL_CONST);
    return null;
  }

  @Override
  public Object visit(ParamDeclListNode node) throws Exception {

    for(ParDeclNode parDeclNode : node.getParamDeclListNode())
      parDeclNode.accept(this);
    return null;
  }

  @Override
  public Object visit(ParDeclNode node) throws Exception {
    String type = node.getTypeDeclNode().getValue();

    for(IdLeaf idLeaf : node.getIdListNode().getIdListNode()){
      TableStack.getHead().addToTable(idLeaf.getValue(),type);
      idLeaf.accept(this);
    }

    return null;
  }

  @Override
  public Object visit(ProcListNode node) throws Exception {

    for(ProcNode procNode : node.getProcListNode())
      procNode.accept(this);

    return null;
  }

  @Override
  public Object visit(ProcNode node) throws Exception {
    ArrayList<String> params = new ArrayList<>();
    ArrayList<String> returns = new ArrayList<>();

    if(node.getParamDeclListNode() != null)
      for(ParDeclNode parDeclNode : node.getParamDeclListNode().getParamDeclListNode()){
        String type = parDeclNode.getTypeDeclNode().getValue();
        for(IdLeaf idLeaf : parDeclNode.getIdListNode().getIdListNode())
          params.add(type);
      }

    int size = node.getResultTypeListNode().getResultTypeListNode().size();
    ArrayList<ResultTypeNode> resultTypeNodes = node.getResultTypeListNode().getResultTypeListNode();

    //CHECK deve esistere un solo void come parametro di ritorno.
    int i = 0;
    if(size == 1 && resultTypeNodes.get(i).isVoid()){
      returns.add("void");
      i++;
    }

    while(i < size){

      if(resultTypeNodes.get(i).isVoid())
        throw new ReturnParamsException(node.getIdLeaf().getValue());
      else
        returns.add(resultTypeNodes.get(i).getTypeDeclNode().getValue());

      i++;
    }

    TableStack.getHead().addToTable(node.getIdLeaf().getValue(),params,returns);
    TableStack.add(node.getSymbolTable());

    if(node.getParamDeclListNode() != null)
      node.getParamDeclListNode().accept(this);

    node.getResultTypeListNode().accept(this);

    if(node.getVarDeclListNode() != null)
      node.getVarDeclListNode().accept(this);

    if(node.getStatListNode() != null)
      node.getStatListNode().accept(this);

    if(node.getReturnExprsNode() != null){
      node.getReturnExprsNode().accept(this);
      ArrayList<ExprNode> exprNodes = node.getReturnExprsNode().getExprListNode().getExprListNode();

      /*if(exprNodes.size() != returns.size())
        throw new ReturnParamsException(node.getIdLeaf().getValue());*/
      for (int j = 0; j < exprNodes.size(); j++){
        ExprNode expr = exprNodes.get(j);
        if(expr.getName().equals(Node.CALL_PROC_OP)){
          CallProcNode callProcNode = (CallProcNode) exprNodes.get(j).getValue1();
          ArrayList<String> returnsCall = new ArrayList<String>(
              Arrays.asList(callProcNode.getType().split(", ")));
          for(String returnCall : returnsCall){
            if(!returnCall.equals(returns.get(j)))
              throw new ReturnParamsException(node.getIdLeaf().getValue());
            j++;
          }
        }
        else if(!returns.get(j).equals(expr.getType()))
          throw new ReturnParamsException(node.getIdLeaf().getValue());
      }
    }

    //System.out.println(node.getIdLeaf().getValue());
    //TableStack.printTables();

    TableStack.pop();

    return null;
  }

  @Override
  public Object visit(ProgramNode node) throws Exception {
    TableStack.add(node.getSymbolTable());

    if( node.getVarDeclListNode() != null)
      node.getVarDeclListNode().accept(this);

    node.getProcListNode().accept(this);

    //System.out.println(node.getName());
    //TableStack.printTables();

    if(TableStack.lookUp("main") == null)
      throw new Exception("Main non presente.");

    TableStack.pop();

    return null;
  }

  @Override
  public Object visit(ReadlnStatNode node) throws Exception {
    node.getIdListNode().accept(this);
    return null;
  }

  @Override
  public Object visit(ResultTypeListNode node) throws Exception {
    for(ResultTypeNode resultTypeNode : node.getResultTypeListNode())
      resultTypeNode.accept(this);
    return null;
  }

  @Override
  public Object visit(ResultTypeNode node) {
    return null;
  }

  @Override
  public Object visit(ReturnExprsNode node) throws Exception {
    node.getExprListNode().accept(this);
    return null;
  }

  @Override
  public Object visit(StatListNode node) throws Exception {

    for (StatNode statNode : node.getStatListNode()){
      if(statNode == null) continue;

      if(statNode instanceof AssignStatNode){
        ((AssignStatNode) statNode).accept(this);
      }
      if(statNode instanceof CallProcNode){
        ((CallProcNode) statNode).accept(this);
      }
      if(statNode instanceof IfStatNode){
        ((IfStatNode) statNode).accept(this);
      }
      if(statNode instanceof WhileStatNode){
        ((WhileStatNode) statNode).accept(this);
      }
      if(statNode instanceof WriteStatNode){
        ((WriteStatNode) statNode).accept(this);
      }
      if(statNode instanceof ReadlnStatNode){
        ((ReadlnStatNode) statNode).accept(this);
      }

    }
    return null;
  }

  @Override
  public Object visit(StringConstLeaf leaf) {
    TableStack.getHead().addToTable(leaf.getValue(),Node.STRING_CONST);
    leaf.setType(Node.STRING_CONST);
    return null;
  }

  @Override
  public Object visit(TypeDeclNode node) {
    return null;
  }

  @Override
  public Object visit(VarDeclListNode node) throws Exception {

    for(VarDeclNode varDeclNode : node.getVarDeclListNode())
      varDeclNode.accept(this);

    return null;
  }

  @Override
  public Object visit(VarDeclNode node) throws Exception {

    String type = node.getTypeDeclNode().getValue();

    for(Object obj : node.getIdListInitNode().getIdListInitNode()){

      if( obj instanceof IdLeaf){
        TableStack.getHead().addToTable(((IdLeaf) obj).getValue(),type);
        ((IdLeaf) obj).accept(this);
      }
      else if( obj instanceof AssignStatNode){
        ArrayList<IdLeaf> idLeaves = ((AssignStatNode) obj).getIdListNode().getIdListNode();
        ArrayList<ExprNode> exprNodes = ((AssignStatNode) obj).getExprListNode().getExprListNode();

        if(idLeaves.size() == 1 && exprNodes.size() == 1){
          TableStack.getHead().addToTable(idLeaves.get(0).getValue(),type);
          idLeaves.get(0).accept(this);
          exprNodes.get(0).accept(this);
          if(!exprNodes.get(0).getType().contains(idLeaves.get(0).getType()))
            throw new TypeMismatchException();
        }
        else
          throw new TypeMismatchException();

      }
    }

    return null;
  }

  @Override
  public Object visit(WhileStatNode node) throws Exception {

    node.getExprNode().accept(this);
    if(!node.getExprNode().getType().equals(Node.BOOLEAN_CONST))
      throw new TypeMismatchException();

    if(node.getStatListNode1() != null)
      node.getStatListNode1().accept(this);

    node.getStatListNode2().accept(this);
    return null;
  }

  @Override
  public Object visit(WriteStatNode node) throws Exception {
    node.getExprListNode().accept(this);
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

    //< <= != == > >=, BOOLEAN, BOOLEAN -> BOOLEAN
    key = new KeyOpTypes(Node.BOOLEAN_CONST,Node.BOOLEAN_CONST,Node.LT_OP);
    opTypes2.put(key,Node.BOOLEAN_CONST);

    key = new KeyOpTypes(Node.BOOLEAN_CONST,Node.BOOLEAN_CONST,Node.LE_OP);
    opTypes2.put(key,Node.BOOLEAN_CONST);

    key = new KeyOpTypes(Node.BOOLEAN_CONST,Node.BOOLEAN_CONST,Node.NE_OP);
    opTypes2.put(key,Node.BOOLEAN_CONST);

    key = new KeyOpTypes(Node.BOOLEAN_CONST,Node.BOOLEAN_CONST,Node.EQ_OP);
    opTypes2.put(key,Node.BOOLEAN_CONST);

    key = new KeyOpTypes(Node.BOOLEAN_CONST,Node.BOOLEAN_CONST,Node.GT_OP);
    opTypes2.put(key,Node.BOOLEAN_CONST);

    key = new KeyOpTypes(Node.BOOLEAN_CONST,Node.BOOLEAN_CONST,Node.GE_OP);
    opTypes2.put(key,Node.BOOLEAN_CONST);

    //< <= != == > >=, INTEGER, INTEGER -> BOOLEAN
    key = new KeyOpTypes(Node.INT_CONST,Node.INT_CONST,Node.LT_OP);
    opTypes2.put(key,Node.BOOLEAN_CONST);

    key = new KeyOpTypes(Node.INT_CONST,Node.INT_CONST,Node.LE_OP);
    opTypes2.put(key,Node.BOOLEAN_CONST);

    key = new KeyOpTypes(Node.INT_CONST,Node.INT_CONST,Node.NE_OP);
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

    key = new KeyOpTypes(Node.INT_CONST,Node.FLOAT_CONST,Node.NE_OP);
    opTypes2.put(key,Node.BOOLEAN_CONST);

    key = new KeyOpTypes(Node.INT_CONST,Node.FLOAT_CONST,Node.EQ_OP);
    opTypes2.put(key,Node.BOOLEAN_CONST);

    key = new KeyOpTypes(Node.INT_CONST,Node.FLOAT_CONST,Node.GT_OP);
    opTypes2.put(key,Node.BOOLEAN_CONST);

    key = new KeyOpTypes(Node.INT_CONST,Node.FLOAT_CONST,Node.GE_OP);
    opTypes2.put(key,Node.BOOLEAN_CONST);

    //< <= != == > >=, FLOAT, FLOAT -> BOOLEAN
    key = new KeyOpTypes(Node.FLOAT_CONST,Node.FLOAT_CONST,Node.LT_OP);
    opTypes2.put(key,Node.BOOLEAN_CONST);

    key = new KeyOpTypes(Node.FLOAT_CONST,Node.FLOAT_CONST,Node.LE_OP);
    opTypes2.put(key,Node.BOOLEAN_CONST);

    key = new KeyOpTypes(Node.FLOAT_CONST,Node.FLOAT_CONST,Node.NE_OP);
    opTypes2.put(key,Node.BOOLEAN_CONST);

    key = new KeyOpTypes(Node.FLOAT_CONST,Node.FLOAT_CONST,Node.EQ_OP);
    opTypes2.put(key,Node.BOOLEAN_CONST);

    key = new KeyOpTypes(Node.FLOAT_CONST,Node.FLOAT_CONST,Node.GT_OP);
    opTypes2.put(key,Node.BOOLEAN_CONST);

    key = new KeyOpTypes(Node.FLOAT_CONST,Node.FLOAT_CONST,Node.GE_OP);
    opTypes2.put(key,Node.BOOLEAN_CONST);

    //< <= != == > >=, STRING, STRING -> BOOLEAN
    key = new KeyOpTypes(Node.STRING_CONST,Node.STRING_CONST,Node.LT_OP);
    opTypes2.put(key,Node.BOOLEAN_CONST);

    key = new KeyOpTypes(Node.STRING_CONST,Node.STRING_CONST,Node.LE_OP);
    opTypes2.put(key,Node.BOOLEAN_CONST);

    key = new KeyOpTypes(Node.STRING_CONST,Node.STRING_CONST,Node.NE_OP);
    opTypes2.put(key,Node.BOOLEAN_CONST);

    key = new KeyOpTypes(Node.STRING_CONST,Node.STRING_CONST,Node.EQ_OP);
    opTypes2.put(key,Node.BOOLEAN_CONST);

    key = new KeyOpTypes(Node.STRING_CONST,Node.STRING_CONST,Node.GT_OP);
    opTypes2.put(key,Node.BOOLEAN_CONST);

    key = new KeyOpTypes(Node.STRING_CONST,Node.STRING_CONST,Node.GE_OP);
    opTypes2.put(key,Node.BOOLEAN_CONST);

  };
}
