package it.esercitazione4;

import it.esercitazione4.nodes.ProgramNode;
import it.esercitazione4.symboltable.TableStack;
import it.esercitazione4.visitor.NewSemanticVisitor;
import it.esercitazione4.visitor.SyntaxVisitor;

import java.io.FileReader;

public class Tester {

    public static void main(String[] args) throws Exception {
        parser p = new parser(new Yylex(new FileReader(args[0])));
        System.out.println("SyntaxVisitor starting...");
        SyntaxVisitor visitor = new SyntaxVisitor();
        ProgramNode result = (ProgramNode) p.debug_parse().value;
        visitor.visit(result);
        visitor.saveXML(args[0]);
        System.out.println("SyntaxVisitor done");

        System.out.println("SemanticVisitor starting...");
        NewSemanticVisitor semanticVisitor = new NewSemanticVisitor();
        semanticVisitor.visit(result);
        System.out.println("SemanticVisitor done");

        visitor = new SyntaxVisitor();
        visitor.visit(result);
        visitor.saveXML(args[0]);
        System.out.println("filexml done");
    }
}