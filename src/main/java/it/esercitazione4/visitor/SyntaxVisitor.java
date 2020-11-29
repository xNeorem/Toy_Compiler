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

    public SyntaxVisitor() {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            this.doc = docBuilder.newDocument();
        } catch(ParserConfigurationException pce) {
            pce.printStackTrace();
        }
    }

    @Override
    public Object visit(Visitable node) {
        VisitableNode<Node> rootNode = (VisitableNode<Node>) node;
        Element rootElement = doc.createElement(rootNode.data().getName());
        if(rootNode.data().getValue() != null && !(rootNode.data().getValue() instanceof ArrayList))
            rootElement.setAttribute("value",rootNode.data().getValue().toString());

        else if(rootNode.data().getValue() != null && rootNode.data().getValue() instanceof ArrayList){
            for (VisitableNode<Node> currentNode : (ArrayList<VisitableNode<Node>>)rootNode.data().getValue())
                rootElement.appendChild((Element) currentNode.accept(this));
        }

        Iterator<VisitableNode> childs = ((VisitableNode)rootNode).subtrees().iterator();
        VisitableNode currentNode;
        while(childs.hasNext()) {
            currentNode = childs.next();
            rootElement.appendChild((Element) currentNode.accept(this));
        }
        this.rootElement = rootElement;
        return this.rootElement;
    }

    public void saveFileXML(String outputPath){
        try {
            doc.appendChild(this.rootElement);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(outputPath));
            transformer.transform(source, result);
            System.out.println("File saved!");
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }

    private Element rootElement;
    private Document doc;
}
