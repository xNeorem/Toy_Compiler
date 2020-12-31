package it.esercitazione4.symboltable;

import it.esercitazione4.visitor.Node;
import java.util.Stack;

public  class TableStack{

  public static EntrySymbolTable lookUp(String symbol){

    int i = stack.size() - 1;

    while(i > 0){
      SymbolTable currentSymbolTable = stack.get(i).getSymbolTable();
      if(currentSymbolTable != null) {
        EntrySymbolTable entrySymbolTable = currentSymbolTable.lookUp(symbol);
        if (entrySymbolTable != null) return entrySymbolTable;
      }
      i--;
    }

    return null;
  }

  public static void add(Node node){
    stack.add(node);
  }

  public static Node pop(Node node){
    return stack.pop();
  }

  private static final Stack<Node> stack = new Stack<>();
}
