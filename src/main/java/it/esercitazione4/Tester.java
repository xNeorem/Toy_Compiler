package it.esercitazione4;

import it.esercitazione4.nodes.ProgramNode;
import it.esercitazione4.visitor.ClangVisitor;
import it.esercitazione4.visitor.SemanticVisitor;
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
        SemanticVisitor semanticVisitor = new SemanticVisitor();
        semanticVisitor.visit(result);
        System.out.println("SemanticVisitor done");

        System.out.println("ClangVisitor starting...");
        ClangVisitor clangVisitor = new ClangVisitor();
        clangVisitor.visit(result);
        String filePath = clangVisitor.saveC(args[0]);
        System.out.println("ClangVisitor done");
        Runtime.getRuntime().exec("./Toy2C.sh " + filePath);
    }
}