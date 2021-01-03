package it.esercitazione4.symboltable;

import java.util.ArrayList;
import java.util.HashMap;

public class SymbolTable {

  public SymbolTable() {
    this.symbolTable = new HashMap<>();
  }

  public EntrySymbolTable lookUp(String symbol){
    return this.symbolTable.get(symbol);
  }

  public boolean addToTable(String symbol, String type){
    if(this.symbolTable.containsKey(symbol))
      return false;
    this.symbolTable.put(symbol,new EntrySymbolTable(symbol,type));
    return true;
  }

  public boolean addToTable(String symbol, ArrayList<String> typeInput, ArrayList<String> typeOutput){
    if(this.symbolTable.containsKey(symbol))
      return false;
    this.symbolTable.put(symbol,new EntrySymbolTable(symbol,typeInput,typeOutput));
    return true;
  }
  public HashMap<String,EntrySymbolTable> symbolTable;
}
