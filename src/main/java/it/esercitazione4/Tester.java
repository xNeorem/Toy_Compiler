package it.esercitazione4;

import it.esercitazione4.visitor.Node;
import it.esercitazione4.visitor.SemanticVisitor;
import it.esercitazione4.visitor.SyntaxVisitor;
import it.esercitazione4.visitor.VisitableNode;
import java.io.FileReader;

public class Tester {

    public static void main(String[] args) throws Exception {
        parser p = new parser(new Yylex(new FileReader(args[0])));
        System.out.println("SyntaxVisitor starting...");
        SyntaxVisitor visitor = new SyntaxVisitor();
        visitor.visit((VisitableNode<Node>)p.debug_parse().value);
        visitor.saveFileXML("file.xml");
        System.out.println("SyntaxVisitor done");

        System.out.println("SemanticVisitor starting...");
        SemanticVisitor semanticVisitor = new SemanticVisitor();
        semanticVisitor.visit((VisitableNode<Node>)p.debug_parse().value);
        System.out.println("SemanticVisitor done");


    }
}