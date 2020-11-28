package it.esercitazione4.visitor;

import java.io.*;
import java.util.Iterator;

public class SyntaxVisitor implements Visitor{

    @Override
    public String visit(Visitable node) {

        this.content = String.format("<%s>",((VisitableNode)node).data());
        Iterator<VisitableNode> childs = ((VisitableNode)node).subtrees().iterator();
        VisitableNode currentNode;
        while(childs.hasNext()) {
            if(!(currentNode = childs.next()).isLeaf()){
                this.content += currentNode.accept(this);
            }
            else{
                if(currentNode.data().equals("integer") || currentNode.data().equals("boolean"))
                    this.content += String.format("<%s/>",currentNode.data());
                else
                    this.content += String.format("%s",currentNode.data());
            }
        }
        this.content += String.format("</%s>",((VisitableNode)node).data());
        return this.content;
    }

    public void saveFileXML(String outFilePath){
        Writer writer = null;

        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(outFilePath), "utf-8"));
            writer.write(this.content);
            writer.close();
        } catch (IOException ex) {
            System.out.println("Errore nella scrittura del file");
        } finally {
            try {writer.close();} catch (Exception ex) {
                System.out.println("Errore durante la chiusura del file");
            }
        }
    }


    public String content = "";
    public int i = 0;
}
