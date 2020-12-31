package it.esercitazione4.symboltable;

import it.esercitazione4.visitor.Node;
import java.util.Stack;

public  class TableStack{

  public static EntrySymbolTable lookUp(String symbol){

    int i = stack.size() - 1;

    while(i > 0){
      EntrySymbolTable entrySymbolTable = stack.get(i).getSymbolTable().lookUp(symbol);
      if(entrySymbolTable != null) return entrySymbolTable;

      i--;
    }

    return null;
  }

  private static final Stack<Node> stack = new Stack<>();
}
