package it.esercitazione4;

import java.io.FileReader;

public class Tester {

    public static void main(String[] args) throws Exception {
        parser p = new parser(new Yylex(new FileReader(args[0])));
        System.out.println("SyntaxVisitor starting...");
        SyntaxVisitor visitor = new SyntaxVisitor();
        VisitableNode<Node> result = (VisitableNode<Node>)p.debug_parse().value;
        visitor.visit(result);
        visitor.saveFileXML("file.xml");
        System.out.println("SyntaxVisitor done");
/*
        System.out.println("SemanticVisitor starting...");
        SemanticVisitor semanticVisitor = new SemanticVisitor();
        semanticVisitor.visit(result);
        System.out.println("SemanticVisitor done");

        while(TableStack.size() != 0){
            System.out.println(TableStack.getHead());
            TableStack.getHead().getSymbolTable().symbolTable.entrySet().forEach(entry->{
                System.out.println(entry.getKey() + " -> " + entry.getValue());
            });

            TableStack.pop();
            System.out.println("NEW NODE\n");
        }
        visitor = new SyntaxVisitor();
        visitor.visit(result);
        visitor.saveFileXML("file1.xml");
        System.out.println("filexml done");*/
    }
}