package it.esercitazione4;

import java.io.FileReader;

public class Tester {

    public static void main(String[] args) throws Exception {
        parser p = new parser(new Yylex(new FileReader(args[0])));
        p.debug_parse();


    }
}