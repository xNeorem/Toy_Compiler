package it.esercitazione4.symboltable;

import java.util.ArrayList;
import java.util.Arrays;

public class EntrySymbolTable {

  public EntrySymbolTable(String symbol, String type) {
    this.symbol = symbol;
    this.type = type;
    this.kind = KIND_VAR;
  }

  public EntrySymbolTable(String symbol, ArrayList<String> typeInput, ArrayList<String> typeOutput) {
    this.symbol = symbol;
    this.typeInput = typeInput;
    this.typeOutput = typeOutput;
    this.kind = KIND_PROC;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public ArrayList<String> getTypeInput() {
    return typeInput;
  }

  public void setTypeInput(ArrayList<String> typeInput) {
    this.typeInput = typeInput;
  }

  public ArrayList<String> getTypeOutput() {
    return typeOutput;
  }

  public void setTypeOutput(ArrayList<String> typeOutput) {
    this.typeOutput = typeOutput;
  }

  public String getKind() {
    return kind;
  }

  public void setKind(String kind) {
    this.kind = kind;
  }

  @Override
  public String toString() {
    return "EntrySymbolTable{" +
        "symbol='" + symbol + '\'' +
        ", type='" + type + '\'' +
        ", typeInput=" + typeInput +
        ", typeOutput=" + typeOutput +
        ", kind='" + kind + '\'' +
        '}';
  }

  private String symbol;
  private String type;
  private ArrayList<String> typeInput;
  private ArrayList<String> typeOutput;
  private String kind;

  public final static String
      KIND_VAR = "var",
      KIND_PROC = "proc";
}
