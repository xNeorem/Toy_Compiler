package it.esercitazione4.visitor;

import it.esercitazione4.nodes.AssignStatNode;
import it.esercitazione4.nodes.BoolConstLeaf;
import it.esercitazione4.nodes.CallProcNode;
import it.esercitazione4.nodes.ElifListNode;
import it.esercitazione4.nodes.ElifNode;
import it.esercitazione4.nodes.ElseNode;
import it.esercitazione4.nodes.ExprListNode;
import it.esercitazione4.nodes.ExprNode;
import it.esercitazione4.nodes.FloatConstLeaf;
import it.esercitazione4.nodes.IdLeaf;
import it.esercitazione4.nodes.IdListInitNode;
import it.esercitazione4.nodes.IdListNode;
import it.esercitazione4.nodes.IfStatNode;
import it.esercitazione4.nodes.IntConstLeaf;
import it.esercitazione4.nodes.Node;
import it.esercitazione4.nodes.NullConstLeaf;
import it.esercitazione4.nodes.ParDeclNode;
import it.esercitazione4.nodes.ParamDeclListNode;
import it.esercitazione4.nodes.ProcListNode;
import it.esercitazione4.nodes.ProcNode;
import it.esercitazione4.nodes.ProgramNode;
import it.esercitazione4.nodes.ReadlnStatNode;
import it.esercitazione4.nodes.ResultTypeListNode;
import it.esercitazione4.nodes.ResultTypeNode;
import it.esercitazione4.nodes.ReturnExprsNode;
import it.esercitazione4.nodes.StatListNode;
import it.esercitazione4.nodes.StatNode;
import it.esercitazione4.nodes.StringConstLeaf;
import it.esercitazione4.nodes.TypeDeclNode;
import it.esercitazione4.nodes.VarDeclListNode;
import it.esercitazione4.nodes.VarDeclNode;
import it.esercitazione4.nodes.WhileStatNode;
import it.esercitazione4.nodes.WriteStatNode;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SyntaxVisitor implements Visitor{
    DocumentBuilderFactory dbFactory;
    DocumentBuilder dBuilder;
    Document doc;

    public SyntaxVisitor() throws ParserConfigurationException {
        dbFactory = DocumentBuilderFactory.newInstance();
        dBuilder = dbFactory.newDocumentBuilder();
        doc = dBuilder.newDocument();
    }

    public void saveXML(String name) throws TransformerException {
        // write the content into xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        DOMSource source = new DOMSource(doc);

        StreamResult result = new StreamResult(new File(name.substring(0, name.length()-4).split("/")[3]+ ".xml"));
        transformer.transform(source, result);

        System.out.println("File saved!");
    }

    @Override
    public Object visit(AssignStatNode node) throws Exception {
        Element rootElement = doc.createElement(node.getName());
        rootElement.appendChild((Element) node.getIdListNode().accept(this));
        rootElement.appendChild((Element) node.getExprListNode().accept(this));
        return rootElement;
    }

    @Override
    public Object visit(BoolConstLeaf leaf) {
        return doc.createElement(leaf.getName());
    }

    @Override
    public Object visit(CallProcNode node) throws Exception {
        Element rootElement = doc.createElement(node.getName());
        rootElement.appendChild((Element) node.getIdLeaf().accept(this));
        if(node.getExprListNode() != null)
            rootElement.appendChild((Element) node.getExprListNode().accept(this));
        return rootElement;
    }

    @Override
    public Object visit(ElifListNode node) throws Exception {
        Element rootElement = doc.createElement(node.getName());
        for (ElifNode elifNode : node.getElifListNode())
            rootElement.appendChild((Element) elifNode.accept(this));

        return rootElement;
    }

    @Override
    public Object visit(ElifNode node) throws Exception {
        Element rootElement = doc.createElement(node.getName());
        rootElement.appendChild((Element) node.getExprNode().accept(this));
        rootElement.appendChild((Element) node.getStatiListNode().accept(this));
        return rootElement;
    }

    @Override
    public Object visit(ElseNode node) throws Exception {
        Element rootElement = doc.createElement(node.getName());
        rootElement.appendChild((Element) node.getStatListNode().accept(this));
        return rootElement;
    }

    @Override
    public Object visit(ExprListNode node) throws Exception {
        Element rootElement = doc.createElement(node.getName());
        for (ExprNode exprNode : node.getExprListNode())
            rootElement.appendChild((Element) exprNode.accept(this));

            return rootElement;
    }

    @Override
    public Object visit(ExprNode node) throws Exception {
        String nodeName = node.getName();
        Element rootElement = null;
        switch (nodeName){
            case Node.NULL_CONST:
                rootElement = (Element) ((NullConstLeaf)node.getValue1()).accept(this);
                break;
            case Node.TRUE_CONST:
            case Node.FALSE_CONST:
                rootElement = (Element) ((BoolConstLeaf)node.getValue1()).accept(this) ;
                break;
            case Node.INT_CONST:
                rootElement = (Element) ((IntConstLeaf)node.getValue1()).accept(this);
                break;
            case Node.FLOAT_CONST:
                rootElement = (Element) ((FloatConstLeaf)node.getValue1()).accept(this);
                break;
            case Node.STRING_CONST:
                rootElement = (Element) ((StringConstLeaf)node.getValue1()).accept(this);
                break;
            case Node.ID:
                rootElement = (Element) ((IdLeaf)node.getValue1()).accept(this);
                break;
            case Node.CALL_PROC_OP:
                rootElement = (Element) ((CallProcNode)node.getValue1()).accept(this);
                break;
            case Node.ADD_OP:
            case Node.DIFF_OP:
            case Node.MUL_OP:
            case Node.DIV_OP:
            case Node.AND_OP:
            case Node.OR_OP:
            case Node.GT_OP:
            case Node.GE_OP:
            case Node.LT_OP:
            case Node.LE_OP:
            case Node.EQ_OP:
            case Node.NE_OP:
                rootElement = doc.createElement(node.getName());
                rootElement.appendChild((Element) ((ExprNode)node.getValue1()).accept(this));
                rootElement.appendChild((Element) ((ExprNode)node.getValue2()).accept(this));
                break;
            case Node.UMINUS_OP:
            case Node.NOT_OP:
            case Node.ASSIGN_OP:
                rootElement = (Element) ((ExprNode)node.getValue1()).accept(this);
                break;


        }
        return rootElement;
    }

    @Override
    public Object visit(FloatConstLeaf leaf) {
        Element rootElement = doc.createElement(leaf.getName());
        rootElement.setAttribute("value",String.valueOf(leaf.getValue()));

        return rootElement;
    }

    @Override
    public Object visit(IdLeaf leaf) {
        Element rootElement = doc.createElement(leaf.getName());
        rootElement.setAttribute("value",leaf.getValue());

        return rootElement;
    }

    @Override
    public Object visit(IdListInitNode node) throws Exception {
        Element rootElement = doc.createElement(node.getName());
        for (Object obj : node.getIdListInitNode()){
            if( obj instanceof IdLeaf){
                rootElement.appendChild((Element) ((IdLeaf) obj).accept(this));
            }
            else if( obj instanceof AssignStatNode){
                rootElement.appendChild((Element) ((AssignStatNode) obj).accept(this));
            }
        }

        return rootElement;
    }

    @Override
    public Object visit(IdListNode node) throws Exception {
        Element rootElement = doc.createElement(node.getName());
        for (IdLeaf idLeaf : node.getIdListNode())
            rootElement.appendChild((Element) idLeaf.accept(this));

        return rootElement;
    }

    @Override
    public Object visit(IfStatNode node) throws Exception {
        Element rootElement = doc.createElement(node.getName());
        rootElement.appendChild((Element) node.getExprNode().accept(this));
        rootElement.appendChild((Element) node.getStatListNode().accept(this));
        if(node.getElifListNode() != null)
            rootElement.appendChild((Element) node.getElifListNode().accept(this));
        if(node.getElseNode() != null)
            rootElement.appendChild((Element) node.getElseNode().accept(this));
        return rootElement;
    }

    @Override
    public Object visit(IntConstLeaf leaf) {
        Element rootElement = doc.createElement(leaf.getName());
        rootElement.setAttribute("value",String.valueOf(leaf.getValue()));

        return rootElement;
    }

    @Override
    public Object visit(NullConstLeaf leaf) {
        return doc.createElement(leaf.getName());
    }

    @Override
    public Object visit(ParamDeclListNode node) throws Exception {
        Element rootElement = doc.createElement(node.getName());
        for (ParDeclNode parDeclNode : node.getParamDeclListNode())
            rootElement.appendChild((Element) parDeclNode.accept(this));

        return rootElement;
    }

    @Override
    public Object visit(ParDeclNode node) throws Exception {
        Element rootElement = doc.createElement(node.getName());
        rootElement.appendChild((Element) node.getTypeDeclNode().accept(this));
        rootElement.appendChild((Element) node.getIdListNode().accept(this));

        return rootElement;
    }

    @Override
    public Object visit(ProcListNode node) throws Exception {
        Element rootElement = doc.createElement(node.getName());
        for (ProcNode procNode : node.getProcListNode())
            rootElement.appendChild((Element) procNode.accept(this));

        return rootElement;
    }

    @Override
    public Object visit(ProcNode node) throws Exception {
        Element rootElement = doc.createElement(node.getName());
        rootElement.appendChild((Element) node.getIdLeaf().accept(this));

        if(node.getParamDeclListNode() != null)
            rootElement.appendChild((Element) node.getParamDeclListNode().accept(this));

        rootElement.appendChild((Element) node.getResultTypeListNode().accept(this));

        if(node.getVarDeclListNode() != null)
            rootElement.appendChild((Element) node.getVarDeclListNode().accept(this));

        if(node.getStatListNode() != null)
            rootElement.appendChild((Element) node.getStatListNode().accept(this));

        if(node.getReturnExprsNode() != null)
            rootElement.appendChild((Element) node.getReturnExprsNode().accept(this));

        return rootElement;
    }

    @Override
    public Object visit(ProgramNode node) throws Exception {
        Element rootElement = doc.createElement(node.getName());
        if( node.getVarDeclListNode() != null)
            rootElement.appendChild((Element) node.getVarDeclListNode().accept(this));
        rootElement.appendChild((Element) node.getProcListNode().accept(this));

        doc.appendChild(rootElement);

        return rootElement;
    }

    @Override
    public Object visit(ReadlnStatNode node) throws Exception {
        Element rootElement = doc.createElement(node.getName());
        rootElement.appendChild((Element) node.getIdListNode().accept(this));

        return rootElement;
    }

    @Override
    public Object visit(ResultTypeListNode node) throws Exception {
        Element rootElement = doc.createElement(node.getName());
        for (ResultTypeNode resultTypeNode : node.getResultTypeListNode())
            rootElement.appendChild((Element) resultTypeNode.accept(this));

        return rootElement;
    }

    @Override
    public Object visit(ResultTypeNode node) throws Exception {
        Element rootElement = doc.createElement(node.getName());
        if(node.isVoid())
            rootElement.setAttribute("value","void");
        else
            rootElement.appendChild((Element) node.getTypeDeclNode().accept(this));


        return rootElement;
    }

    @Override
    public Object visit(ReturnExprsNode node) throws Exception {
        Element rootElement = doc.createElement(node.getName());
        rootElement.appendChild((Element) node.getExprListNode().accept(this));

        return rootElement;
    }

    @Override
    public Object visit(StatListNode node) throws Exception {
        Element rootElement = doc.createElement(node.getName());
        for (StatNode statNode : node.getStatListNode()){
            if(statNode == null) continue;

            if(statNode instanceof AssignStatNode){
                rootElement.appendChild((Element) ((AssignStatNode) statNode).accept(this));
            }
            if(statNode instanceof CallProcNode){
                rootElement.appendChild((Element) ((CallProcNode) statNode).accept(this));
            }
            if(statNode instanceof IfStatNode){
                rootElement.appendChild((Element) ((IfStatNode) statNode).accept(this));
            }
            if(statNode instanceof WhileStatNode){
                rootElement.appendChild((Element) ((WhileStatNode) statNode).accept(this));
            }
            if(statNode instanceof WriteStatNode){
                rootElement.appendChild((Element) ((WriteStatNode) statNode).accept(this));
            }
            if(statNode instanceof ReadlnStatNode){
                rootElement.appendChild((Element) ((ReadlnStatNode) statNode).accept(this));
            }

        }

        return rootElement;
    }

    @Override
    public Object visit(StringConstLeaf leaf) {
        Element rootElement = doc.createElement(leaf.getName());
        rootElement.setAttribute("value",leaf.getValue());

        return rootElement;
    }

    @Override
    public Object visit(TypeDeclNode node) {
        Element rootElement = doc.createElement(node.getName());
        rootElement.setAttribute("value",node.getValue());

        return rootElement;
    }

    @Override
    public Object visit(VarDeclListNode node) throws Exception {
        Element rootElement = doc.createElement(node.getName());
        for (VarDeclNode varDeclNode : node.getVarDeclListNode())
            rootElement.appendChild((Element) varDeclNode.accept(this));

        return rootElement;
    }

    @Override
    public Object visit(VarDeclNode node) throws Exception {
        Element rootElement = doc.createElement(node.getName());
        rootElement.appendChild((Element) node.getTypeDeclNode().accept(this));
        rootElement.appendChild((Element) node.getIdListInitNode().accept(this));


        return rootElement;
    }

    @Override
    public Object visit(WhileStatNode node) throws Exception {
        Element rootElement = doc.createElement(node.getName());
        if(node.getStatListNode1() != null)
            rootElement.appendChild((Element) node.getStatListNode1().accept(this));

        rootElement.appendChild((Element) node.getExprNode().accept(this));
        rootElement.appendChild((Element) node.getStatListNode2().accept(this));


        return rootElement;
    }

    @Override
    public Object visit(WriteStatNode node) throws Exception {
        Element rootElement = doc.createElement(node.getName());
        rootElement.appendChild((Element) node.getExprListNode().accept(this));

        return rootElement;
    }
}
