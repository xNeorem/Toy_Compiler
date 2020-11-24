package it.esercitazione4;

import java.util.ArrayList;
import java.util.List;

public class Node<T>{
    private T data = null;
    private List<Node> children = new ArrayList<>();
    private Node parent = null;

    public Node(T data) {
        this.data = data;
    }

    public Node(T data, Node child) {
        this.data = data;
        this.addChild(child);
    }

    public Node(T data, List<Node> children) {
        this.data = data;
        this.addChildren(children);
    }

    public void addChild(Node child) {
        child.setParent(this);
        this.children.add(child);
    }

    public void addChild(T data) {
        Node<T> newChild = new Node<>(data);
        this.addChild(newChild);
    }

    public void addChildren(List<Node> children) {
        for(Node t : children) {
            t.setParent(this);
        }
        this.children.addAll(children);
    }

    public List<Node> getChildren() {
        return children;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    private void setParent(Node parent) {
        this.parent = parent;
    }

    public Node getParent() {
        return parent;
    }
}