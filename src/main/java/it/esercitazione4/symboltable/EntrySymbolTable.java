package it.esercitazione4.symboltable;

import java.util.Arrays;

public class EntrySymbolTable {

  public EntrySymbolTable(String symbol, String type) {
    this.symbol = symbol;
    this.type = type;
    this.kind = KIND_VAR;
  }

  public EntrySymbolTable(String symbol, String[] typeInput, String[] typeOutput) {
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

  public String[] getTypeInput() {
    return typeInput;
  }

  public void setTypeInput(String[] typeInput) {
    this.typeInput = typeInput;
  }

  public String[] getTypeOutput() {
    return typeOutput;
  }

  public void setTypeOutput(String[] typeOutput) {
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
        ", typeInput=" + Arrays.toString(typeInput) +
        ", typeOutput=" + Arrays.toString(typeOutput) +
        ", kind='" + kind + '\'' +
        '}';
  }

  private String symbol;
  private String type;
  private String[] typeInput;
  private String[] typeOutput;
  private String kind;

  public final static String
      KIND_VAR = "var",
      KIND_PROC = "proc";
}
