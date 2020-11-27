package it.esercitazione4.visitor;

import com.scalified.tree.TreeNode;
import com.scalified.tree.multinode.ArrayMultiTreeNode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class VisitableNode<T> extends ArrayMultiTreeNode<T> implements Visitable {

    public VisitableNode(T data) {
        super(data);
    }

    @Override
    public String accept(Visitor visitor) {
        return visitor.visit(this);
    }

    public VisitableNode<T> firstChild(){
        return (VisitableNode<T>) this.subtrees()[0];
    }

    public int numChild(){
        return this.subtrees().size();
    }

    public VisitableNode<T> getChild(int i ){
        return (VisitableNode<T>) this.subtrees()[i];
    }

}