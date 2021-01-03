package it.esercitazione4.symboltable;

import java.util.Stack;

public  class TableStack{

  public static EntrySymbolTable lookUp(String symbol){

    int i = stack.size() - 1;

    while(i > 0){
      SymbolTable currentSymbolTable = stack.get(i);
      if(currentSymbolTable != null) {
        EntrySymbolTable entrySymbolTable = currentSymbolTable.lookUp(symbol);
        if (entrySymbolTable != null) return entrySymbolTable;
      }
      i--;
    }

    return null;
  }

  public static SymbolTable getHead(){
    return stack.peek();
  }

  public static void add(SymbolTable symbolTable){
    stack.add(symbolTable);
  }

  public static SymbolTable pop(){
    return stack.pop();
  }

  public static int size(){
    return stack.size();
  }

  private static final Stack<SymbolTable> stack = new Stack<>();
}
