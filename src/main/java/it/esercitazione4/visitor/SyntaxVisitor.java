package it.esercitazione4.visitor;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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

    public void myfunc(VisitableNode<Node> rootNode){
        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root elements
            this.doc = docBuilder.newDocument();

            doc.appendChild(createNodeXML(rootNode));
            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("file.xml"));

            // Output to console for testing
            // StreamResult result = new StreamResult(System.out);

            transformer.transform(source, result);

            System.out.println("File saved!");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }

    private Element createNodeXML(VisitableNode<Node> rootNode){

        Element rootElement = doc.createElement(rootNode.data().getName());
        if(rootNode.data().getValue() != null && !(rootNode.data().getValue() instanceof ArrayList))
            rootElement.setAttribute("value",rootNode.data().getValue().toString());

        else if(rootNode.data().getValue() != null && rootNode.data().getValue() instanceof ArrayList){
            for (VisitableNode<Node> currentNode : (ArrayList<VisitableNode<Node>>)rootNode.data().getValue())
                rootElement.appendChild(createNodeXML(currentNode));
        }
        Iterator<VisitableNode> childs = ((VisitableNode)rootNode).subtrees().iterator();
        VisitableNode currentNode;
        while(childs.hasNext()) {
            currentNode = childs.next();
            rootElement.appendChild(createNodeXML(currentNode));
        }
        return rootElement;
    }


    public String content = "";
    public int i = 0;
    private Document doc;
}
