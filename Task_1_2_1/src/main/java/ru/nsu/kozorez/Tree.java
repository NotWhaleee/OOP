package ru.nsu.kozorez;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Iterator;
import java.util.Queue;
import java.util.LinkedList;


public class Tree<T> {
    private T data;
    private List<Tree<T>> children;
    private Tree<T> parent;

    public T getData() {
        return data;
    }

    public List<Tree<T>> getChildren() {
        return children;
    }

    public Tree<T> getParent() {
        return parent;
    }

    public Tree(T data) {
        this.data = data;
        this.children = new ArrayList<>();
    }

    public Tree<T> addChild(T child) {
        Tree<T> childNode = new Tree<>(child);
        childNode.parent = this;
        this.children.add(childNode);
        return childNode;
    }

    public void addChild(Tree<T> subtree) {
        subtree.parent = this;
        this.children.add(subtree);
    }

    public void remove() {

        if (this.parent != null) {
            int index = this.parent.children.indexOf(this);
            this.parent.children.remove(this);
            for (Tree<T> each : children) {
                each.parent = this.parent;
            }
            this.parent.children.addAll(index, this.children);
        } else {
            throw new IllegalArgumentException("Cannot remove root node");
        }

    }

    public Iterator<Tree<T>> iterator() {
        return new TreeIterator<>(this);
    }

    private class TreeIterator<E> implements Iterator<Tree<E>> {

        private Queue<Tree<E>> queue;

        public TreeIterator(Tree<E> root) {
            queue = new LinkedList<>();
            queue.add(root);

        }

        @Override
        public boolean hasNext() {

            return !queue.isEmpty();


        }

        @Override
        public Tree<E> next() {
            Tree<E> nextNode = queue.poll();
            assert nextNode != null;
            queue.addAll(nextNode.children);
            return nextNode;

        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tree<?> tree = (Tree<?>) o;
        return Objects.equals(data, tree.data) && Objects.equals(children, tree.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, children);
    }


    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(data);
        if (!children.isEmpty()) {
            result.append(" [");
            for (Tree<T> child : children) {
                result.append(child.toString());
                result.append(", ");
            }
            result.delete(result.length() - 2, result.length());
            result.append("]");
        }
        return result.toString();
    }
}
