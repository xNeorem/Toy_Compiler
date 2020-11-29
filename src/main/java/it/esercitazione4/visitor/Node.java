package it.esercitazione4.visitor;

public class Node {
    public Node(String name) {
        this.name = name;
    }

    public Node(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Node{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }

    private String name;
    private Object value;
}
