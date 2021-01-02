package it.esercitazione4.visitor;

import it.esercitazione4.exceptions.AlreadyDeclaredException;
import it.esercitazione4.exceptions.TypeMismatchException;
import it.esercitazione4.exceptions.UndeclaredException;
import it.esercitazione4.symboltable.EntrySymbolTable;
import it.esercitazione4.symboltable.TableStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class SemanticVisitor implements Visitor {

    @Override
    public Object visit(Visitable node) throws Exception {
        VisitableNode<Node> rootNode = (VisitableNode<Node>) node;
        Iterator<VisitableNode> childs = ((VisitableNode) rootNode).subtrees().iterator();
        Node currNode = rootNode.data();
        String rootName = currNode.getName();

        //SCOPING
        //REGOLA A
        if (Node.createTable(currNode)) TableStack.add(currNode);

        //REGOLA B
        if (rootName.equals(VisitableNode.VAR_DECL_OP) || rootName.equals(VisitableNode.PAR_DECL_OP)) {
            String type = (String) rootNode.getChild(0).data().getValue();
            ArrayList<VisitableNode<Node>> idList = (ArrayList<VisitableNode<Node>>) rootNode.getChild(1).data().getValue();
            for (VisitableNode<Node> idNode : idList){
                if(idNode.data().getName().equals(VisitableNode.ASSIGN_OP)){
                    idNode.getChild(0).data().setType(type);
                    if (!TableStack.getHead().getSymbolTable().addToTable((String) idNode.getChild(0).data().getValue(), type))
                        throw new AlreadyDeclaredException((String) idNode.getChild(0).data().getValue());
                }
                else{
                    idNode.data().setType(type);
                    if (!TableStack.getHead().getSymbolTable().addToTable((String) idNode.data().getValue(), type))
                        throw new AlreadyDeclaredException((String) idNode.data().getValue());
                }

            }
        }

        if(rootName.equals(VisitableNode.PROC_OP)){
            String id = (String) rootNode.getChild(0).data().getValue();
            ArrayList<String> parameters = new ArrayList<>();
            ArrayList<String> returnTypes = new ArrayList<>();

            VisitableNode<Node> pardecllist = rootNode.getChild(1);
            if(pardecllist != null){
                for(VisitableNode<Node> pardecl : (ArrayList<VisitableNode<Node>>)pardecllist.data().getValue()){
                    String type = (String) pardecl.getChild(0).data().getValue();
                    int size = ((ArrayList<VisitableNode<Node>>) pardecl.getChild(1).data().getValue()).size();
                    for(int i = 0; i < size; i++)
                        parameters.add(type);
                }
            }

            VisitableNode<Node> returnsList = rootNode.getChild(2);
            ArrayList<VisitableNode<Node>> rtls = returnsList.getSubtree();
            for(VisitableNode<Node> rtl : rtls){
                String type = (String) rtl.getChild(0).data().getValue();
                returnTypes.add(type);
            }

            TableStack.getHead().getSymbolTable().addToTable(id,parameters,returnTypes);

        }

        if(rootName.equals(VisitableNode.ID) && currNode.getType() == null){
            EntrySymbolTable entry = TableStack.lookUp((String)currNode.getValue());
            if(entry == null)
                throw new UndeclaredException((String)currNode.getValue());

            currNode.setType(entry.getType());
        }

        //TYPECHECKING
        if(SemanticVisitor.isConstant(rootNode.data()))
            this.typeCheckD(rootNode);


        if(rootNode.data().getValue() != null && rootNode.data().getValue() instanceof ArrayList){
            for (VisitableNode<Node> currentNode : (ArrayList<VisitableNode<Node>>)rootNode.data().getValue())
                currentNode.accept(this);
        }

        VisitableNode currentNode;
        while(childs.hasNext()) {
            currentNode = childs.next();
            if(currentNode != null)
                currentNode.accept(this);
        }
        if(!SemanticVisitor.isConstant(rootNode.data()))
            this.typeCheckE(rootNode);





        /*

         if(rootNode.data().getName().equals(VisitableNode.PROC_OP)) {
            rootNode.data().getSymbolTable().addToTable()
        }

        while(childs.hasNext()){
            VisitableNode<Node> currentNode = childs.next();
            String nameCurrentNode = currentNode.data().getName();

        }*/
        return null;
    }

    //REGOLA C
    private void typeCheckC(Node node, String symbol) throws UndeclaredException {
        TableStack.add(node);
        EntrySymbolTable entry = TableStack.lookUp(symbol);
        if(entry == null) throw new UndeclaredException(symbol);
        node.setType(entry.getType());
    }

    //REGOLA D
    private void typeCheckD(VisitableNode<Node> node) {
        if(node.data().getName().equals(VisitableNode.TRUE_CONST) || node.data().getName().equals(VisitableNode.FALSE_CONST))
            node.data().setType(VisitableNode.BOOLEAN_CONST);
        else node.data().setType(node.data().getName());
    }

    //REGOLA E
    private void typeCheckE(VisitableNode<Node> node) throws TypeMismatchException {

        boolean flag = false;

        for (VisitableNode<Node> child : node.getSubtree()){
            this.typeSystem(child);
            if(child.data().getType() != null)
                flag = child.data().getType().equals(VisitableNode.ERROR);
            if(flag)
                break;
        }

        if(flag){
            throw new TypeMismatchException();
        }

    }

    private void typeSystem(VisitableNode<Node> node){

        String name = node.data().getName();

        switch (name){
            case VisitableNode.WHILE_OP:
            case VisitableNode.IF_OP:
            case VisitableNode.ELIF_OP:

               boolean isBoolean = node.getChild(0).data().getType().equals(VisitableNode.BOOLEAN_CONST);

               boolean flag = false;
               for (VisitableNode<Node> child : node.getSubtree()){
                   flag = child.data().getType().equals(VisitableNode.ERROR);
                   if(flag)
                       break;
               }

               if(isBoolean && !flag)
                   node.data().setType(VisitableNode.VOID_CONST);
               else
                   node.data().setType(VisitableNode.ERROR);

               break;
            case VisitableNode.ASSIGN_OP:

                String firstChildType = node.getChild(0).data().getType();
                String secondChildType = node.getChild(1).data().getType();

                if(firstChildType.equals(secondChildType))
                    node.data().setType(VisitableNode.VOID_CONST);
                else
                    node.data().setType(VisitableNode.ERROR);
                break;

            case VisitableNode.GT_OP:
            case VisitableNode.GE_OP:
            case VisitableNode.LT_OP:
            case VisitableNode.LE_OP:
            case VisitableNode.EQ_OP:
                firstChildType = node.getChild(0).data().getType();
                secondChildType = node.getChild(1).data().getType();

                String firstCheck = this.opTypes2.get(new KeyOpTypes(firstChildType,secondChildType,VisitableNode.GT_OP));
                String secondCheck = this.opTypes2.get(new KeyOpTypes(secondChildType,firstChildType,VisitableNode.GT_OP));

                if (firstCheck != null || secondCheck != null ) {
                    node.data().setType(VisitableNode.BOOLEAN_CONST);
                }
                else{
                    node.data().setType(VisitableNode.ERROR);
                }

                break;

            case VisitableNode.ADD_OP:
            case VisitableNode.DIFF_OP:
            case VisitableNode.MUL_OP:
            case VisitableNode.DIV_OP:

                firstChildType = node.getChild(0).data().getType();
                secondChildType = node.getChild(1).data().getType();

                firstCheck = this.opTypes2.get(new KeyOpTypes(firstChildType,secondChildType,VisitableNode.GT_OP));
                secondCheck = this.opTypes2.get(new KeyOpTypes(secondChildType,firstChildType,VisitableNode.GT_OP));

                if (firstCheck != null) {
                    node.data().setType(firstCheck);
                }
                else if(secondCheck != null ){
                    node.data().setType(secondCheck);
                }
                else{
                    node.data().setType(VisitableNode.ERROR);
                }
                break;

            default:break;

        }

    }

    private static boolean isConstant(Node node){
        String name = node.getName();
        if(name.equals(VisitableNode.TRUE_CONST) || name.equals(VisitableNode.FALSE_CONST)
           || name.equals(VisitableNode.INT_CONST) || name.equals(VisitableNode.FLOAT_CONST)
            || name.equals(VisitableNode.STRING_CONST))
            return true;

        return false;
    }


    public final HashMap<KeyOpTypes,String> opTypes2 = new HashMap<>();
    {
        // +-*/, INTEGER,INTEGER -> INTEGER
        KeyOpTypes key = new KeyOpTypes(VisitableNode.INT_CONST,VisitableNode.INT_CONST,VisitableNode.ADD_OP);
        opTypes2.put(key,VisitableNode.INT_CONST);

        key = new KeyOpTypes(VisitableNode.INT_CONST,VisitableNode.INT_CONST,VisitableNode.DIFF_OP);
        opTypes2.put(key,VisitableNode.INT_CONST);

        key = new KeyOpTypes(VisitableNode.INT_CONST,VisitableNode.INT_CONST,VisitableNode.MUL_OP);
        opTypes2.put(key,VisitableNode.INT_CONST);

        key = new KeyOpTypes(VisitableNode.INT_CONST,VisitableNode.INT_CONST,VisitableNode.DIV_OP);
        opTypes2.put(key,VisitableNode.INT_CONST);

        // +-*/, INTEGER,FLOAT -> FLOAT
        key = new KeyOpTypes(VisitableNode.INT_CONST,VisitableNode.FLOAT_CONST,VisitableNode.ADD_OP);
        opTypes2.put(key,VisitableNode.FLOAT_CONST);

        key = new KeyOpTypes(VisitableNode.INT_CONST,VisitableNode.FLOAT_CONST,VisitableNode.DIFF_OP);
        opTypes2.put(key,VisitableNode.FLOAT_CONST);

        key = new KeyOpTypes(VisitableNode.INT_CONST,VisitableNode.FLOAT_CONST,VisitableNode.MUL_OP);
        opTypes2.put(key,VisitableNode.FLOAT_CONST);

        key = new KeyOpTypes(VisitableNode.INT_CONST,VisitableNode.FLOAT_CONST,VisitableNode.DIV_OP);
        opTypes2.put(key,VisitableNode.FLOAT_CONST);

        // +-*/, FLOAT,FLOAT -> FLOAT
        key = new KeyOpTypes(VisitableNode.FLOAT_CONST,VisitableNode.FLOAT_CONST,VisitableNode.ADD_OP);
        opTypes2.put(key,VisitableNode.FLOAT_CONST);

        key = new KeyOpTypes(VisitableNode.FLOAT_CONST,VisitableNode.FLOAT_CONST,VisitableNode.DIFF_OP);
        opTypes2.put(key,VisitableNode.FLOAT_CONST);

        key = new KeyOpTypes(VisitableNode.FLOAT_CONST,VisitableNode.FLOAT_CONST,VisitableNode.MUL_OP);
        opTypes2.put(key,VisitableNode.FLOAT_CONST);

        key = new KeyOpTypes(VisitableNode.FLOAT_CONST,VisitableNode.FLOAT_CONST,VisitableNode.DIV_OP);
        opTypes2.put(key,VisitableNode.FLOAT_CONST);

        //and or, BOOLEAN, BOOLEAN -> BOOLEAN
        key = new KeyOpTypes(VisitableNode.BOOLEAN_CONST,VisitableNode.BOOLEAN_CONST,VisitableNode.AND_OP);
        opTypes2.put(key,VisitableNode.BOOLEAN_CONST);

        key = new KeyOpTypes(VisitableNode.BOOLEAN_CONST,VisitableNode.BOOLEAN_CONST,VisitableNode.OR_OP);
        opTypes2.put(key,VisitableNode.BOOLEAN_CONST);

        //< <= == > >=, BOOLEAN, BOOLEAN -> BOOLEAN
        key = new KeyOpTypes(VisitableNode.BOOLEAN_CONST,VisitableNode.BOOLEAN_CONST,VisitableNode.LT_OP);
        opTypes2.put(key,VisitableNode.BOOLEAN_CONST);

        key = new KeyOpTypes(VisitableNode.BOOLEAN_CONST,VisitableNode.BOOLEAN_CONST,VisitableNode.LE_OP);
        opTypes2.put(key,VisitableNode.BOOLEAN_CONST);

        key = new KeyOpTypes(VisitableNode.BOOLEAN_CONST,VisitableNode.BOOLEAN_CONST,VisitableNode.EQ_OP);
        opTypes2.put(key,VisitableNode.BOOLEAN_CONST);

        key = new KeyOpTypes(VisitableNode.BOOLEAN_CONST,VisitableNode.BOOLEAN_CONST,VisitableNode.GT_OP);
        opTypes2.put(key,VisitableNode.BOOLEAN_CONST);

        key = new KeyOpTypes(VisitableNode.BOOLEAN_CONST,VisitableNode.BOOLEAN_CONST,VisitableNode.GE_OP);
        opTypes2.put(key,VisitableNode.BOOLEAN_CONST);

        //< <= == > >=, INTEGER, INTEGER -> BOOLEAN
        key = new KeyOpTypes(VisitableNode.INT_CONST,VisitableNode.INT_CONST,VisitableNode.LT_OP);
        opTypes2.put(key,VisitableNode.BOOLEAN_CONST);

        key = new KeyOpTypes(VisitableNode.INT_CONST,VisitableNode.INT_CONST,VisitableNode.LE_OP);
        opTypes2.put(key,VisitableNode.BOOLEAN_CONST);

        key = new KeyOpTypes(VisitableNode.INT_CONST,VisitableNode.INT_CONST,VisitableNode.EQ_OP);
        opTypes2.put(key,VisitableNode.BOOLEAN_CONST);

        key = new KeyOpTypes(VisitableNode.INT_CONST,VisitableNode.INT_CONST,VisitableNode.GT_OP);
        opTypes2.put(key,VisitableNode.BOOLEAN_CONST);

        key = new KeyOpTypes(VisitableNode.INT_CONST,VisitableNode.INT_CONST,VisitableNode.GE_OP);
        opTypes2.put(key,VisitableNode.BOOLEAN_CONST);

        //< <= == > >=, INTEGER, FLOAT -> BOOLEAN
        key = new KeyOpTypes(VisitableNode.INT_CONST,VisitableNode.FLOAT_CONST,VisitableNode.LT_OP);
        opTypes2.put(key,VisitableNode.BOOLEAN_CONST);

        key = new KeyOpTypes(VisitableNode.INT_CONST,VisitableNode.FLOAT_CONST,VisitableNode.LE_OP);
        opTypes2.put(key,VisitableNode.BOOLEAN_CONST);

        key = new KeyOpTypes(VisitableNode.INT_CONST,VisitableNode.FLOAT_CONST,VisitableNode.EQ_OP);
        opTypes2.put(key,VisitableNode.BOOLEAN_CONST);

        key = new KeyOpTypes(VisitableNode.INT_CONST,VisitableNode.FLOAT_CONST,VisitableNode.GT_OP);
        opTypes2.put(key,VisitableNode.BOOLEAN_CONST);

        key = new KeyOpTypes(VisitableNode.INT_CONST,VisitableNode.FLOAT_CONST,VisitableNode.GE_OP);
        opTypes2.put(key,VisitableNode.BOOLEAN_CONST);

        //< <= == > >=, FLOAT, FLOAT -> BOOLEAN
        key = new KeyOpTypes(VisitableNode.FLOAT_CONST,VisitableNode.FLOAT_CONST,VisitableNode.LT_OP);
        opTypes2.put(key,VisitableNode.BOOLEAN_CONST);

        key = new KeyOpTypes(VisitableNode.FLOAT_CONST,VisitableNode.FLOAT_CONST,VisitableNode.LE_OP);
        opTypes2.put(key,VisitableNode.BOOLEAN_CONST);

        key = new KeyOpTypes(VisitableNode.FLOAT_CONST,VisitableNode.FLOAT_CONST,VisitableNode.EQ_OP);
        opTypes2.put(key,VisitableNode.BOOLEAN_CONST);

        key = new KeyOpTypes(VisitableNode.FLOAT_CONST,VisitableNode.FLOAT_CONST,VisitableNode.GT_OP);
        opTypes2.put(key,VisitableNode.BOOLEAN_CONST);

        key = new KeyOpTypes(VisitableNode.FLOAT_CONST,VisitableNode.FLOAT_CONST,VisitableNode.GE_OP);
        opTypes2.put(key,VisitableNode.BOOLEAN_CONST);

        //< <= == > >=, STRING, STRING -> BOOLEAN
        key = new KeyOpTypes(VisitableNode.STRING_CONST,VisitableNode.STRING_CONST,VisitableNode.LT_OP);
        opTypes2.put(key,VisitableNode.BOOLEAN_CONST);

        key = new KeyOpTypes(VisitableNode.STRING_CONST,VisitableNode.STRING_CONST,VisitableNode.LE_OP);
        opTypes2.put(key,VisitableNode.BOOLEAN_CONST);

        key = new KeyOpTypes(VisitableNode.STRING_CONST,VisitableNode.STRING_CONST,VisitableNode.EQ_OP);
        opTypes2.put(key,VisitableNode.BOOLEAN_CONST);

        key = new KeyOpTypes(VisitableNode.STRING_CONST,VisitableNode.STRING_CONST,VisitableNode.GT_OP);
        opTypes2.put(key,VisitableNode.BOOLEAN_CONST);

        key = new KeyOpTypes(VisitableNode.STRING_CONST,VisitableNode.STRING_CONST,VisitableNode.GE_OP);
        opTypes2.put(key,VisitableNode.BOOLEAN_CONST);

    };


    class KeyOpTypes{

        public KeyOpTypes(String first, String second, String operation) {
            this.first = first;
            this.second = second;
            this.operation = operation;
        }

        @Override
        public String toString() {
            return "KeyOpTypes{" +
                "first='" + first + '\'' +
                ", second='" + second + '\'' +
                ", operation='" + operation + '\'' +
                '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            KeyOpTypes that = (KeyOpTypes) o;

            if (first != null ? !first.equals(that.first) : that.first != null) {
                return false;
            }
            if (second != null ? !second.equals(that.second) : that.second != null) {
                return false;
            }
            return operation != null ? operation.equals(that.operation) : that.operation == null;
        }

        @Override
        public int hashCode() {
            int result = first != null ? first.hashCode() : 0;
            result = 31 * result + (second != null ? second.hashCode() : 0);
            result = 31 * result + (operation != null ? operation.hashCode() : 0);
            return result;
        }

        String first,second,operation;
    }

}
