package ru.nsu.kozorez;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class nodeTree <T>{
    private T data;
    private nodeTree<T> parent;

    private List<nodeTree<T>> children;

    public nodeTree(T data) {
        this.data = data;
        this.children = new ArrayList<nodeTree<T>>();
        this.parent = null;
    }

    public T getData() {
        return data;
    }

    public List<nodeTree<T>> getChildren() {
        return children;
    }

    public nodeTree<T> getParent() {
        return parent;
    }

    public nodeTree<T> addChild(String childName) {
        nodeTree<T> child = new nodeTree(childName);
        this.children.add(child);
        child.parent = this;
        return child;
    }

    public void remove() {
        if (this.parent != null) {
            this.parent.children.remove(this);
        }
    }


    @Override
    public String toString() {
        String res = "";
        res += "Name: " + this.data + "; parent: ";
        if (this.parent != null) {
            res += this.parent.data + "; ";
        } else {
            res += "null; ";
        }
        res += "children: ";

        if (!this.children.isEmpty()) {
            res += this.children.toString();

        } else {
            res += "null";
        }
        return res;

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        nodeTree<T> that = (nodeTree<T>) o;
        return Objects.equals(this.toString(), that.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, parent, children);
    }
}

