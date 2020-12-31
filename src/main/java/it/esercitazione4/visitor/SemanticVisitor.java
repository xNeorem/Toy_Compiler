package it.esercitazione4.visitor;

import it.esercitazione4.exceptions.AlreadyDeclaredException;
import it.esercitazione4.exceptions.UndeclaredException;
import it.esercitazione4.symboltable.EntrySymbolTable;
import it.esercitazione4.symboltable.TableStack;

import java.util.ArrayList;
import java.util.Iterator;

public class SemanticVisitor implements Visitor {

    @Override
    public Object visit(Visitable node) throws Exception {
        VisitableNode<Node> rootNode = (VisitableNode<Node>) node;
        Iterator<VisitableNode> childs = ((VisitableNode) rootNode).subtrees().iterator();

        //REGOLA A
        if (Node.createTable(rootNode.data())) TableStack.add(rootNode.data());

        //REGOLA B
        if (rootNode.data().getName().equals(VisitableNode.VAR_DECL_OP) || rootNode.data().getName().equals(VisitableNode.PAR_DECL_OP)) {
            ArrayList<VisitableNode<Node>> typesList = (ArrayList<VisitableNode<Node>>) rootNode.getChild(0).data().getValue();
            ArrayList<VisitableNode<Node>> idList = (ArrayList<VisitableNode<Node>>) rootNode.getChild(1).data().getValue();
            int length = idList.size();
            for (int i = 0; i < length; i++) {
                String id = (String) idList.get(i).data().getValue();
                String type = (String) typesList.get(i).data().getValue();
                if (!rootNode.data().getSymbolTable().addToTable(id, type)) throw new AlreadyDeclaredException();
            }
        }

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
    private void typeCheckD(Node node) {
        if(node.getName().equals(VisitableNode.TRUE_CONST) || node.getName().equals(VisitableNode.FALSE_CONST))
            node.setType(VisitableNode.BOOLEAN_CONST);
        else node.setType(node.getName());
    }


}
