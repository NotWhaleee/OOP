package ru.nsu.kozorez;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Iterator;
import java.util.Queue;
import java.util.LinkedList;


/**
 * Class for operations with trees.
 *
 * @param <T> string or subtree
 */
public class Tree<T> {
    private T data;
    private List<Tree<T>> children;
    private Tree<T> parent;

    /**
     * get data.
     *
     * @return data
     */
    public T getData() {
        return data;
    }

    /**
     * get children.
     *
     * @return children
     */
    public List<Tree<T>> getChildren() {
        return children;
    }

    /**
     * get parent.
     *
     * @return return parent
     */
    public Tree<T> getParent() {
        return parent;
    }


    /**
     * set tree.
     * @param data data
     */
    public Tree(T data) {
        this.data = data;
        this.children = new ArrayList<>();
    }

    /**
     * adds node to the tree.
     *
     * @param child node
     * @return node
     */
    public Tree<T> addChild(T child) {
        Tree<T> childNode = new Tree<>(child);
        childNode.parent = this;
        this.children.add(childNode);
        return childNode;
    }

    /**
     * adds subtree to the tree.
     *
     * @param subtree subtree
     */
    public void addChild(Tree<T> subtree) {
        subtree.parent = this;
        this.children.add(subtree);
    }

    /**
     * removes node from the tree.
     */
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

    /**
     * creates iterator.
     *
     * @return iterator
     */
    public Iterator<Tree<T>> iterator() {
        return new TreeIterator<>(this);
    }

    /**
     * iterator for trees
     *
     * @param <E> nodes
     */
    private class TreeIterator<E> implements Iterator<Tree<E>> {

        private Queue<Tree<E>> queue;

        /**
         * initialises iterator.
         *
         * @param root root node
         */
        public TreeIterator(Tree<E> root) {
            queue = new LinkedList<>();
            queue.add(root);

        }

        /**
         * checks if queue is empty or not.
         *
         * @return true or false
         */
        @Override
        public boolean hasNext() {

            return !queue.isEmpty();


        }

        /**
         * iterates to the next node.
         *
         * @return node
         */
        @Override
        public Tree<E> next() {
            Tree<E> nextNode = queue.poll();
            assert nextNode != null;
            queue.addAll(nextNode.children);
            return nextNode;

        }
    }

    /**
     * checks if the trees are equal.
     *
     * @param o tree
     * @return true or false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tree<?> tree = (Tree<?>) o;
        return Objects.equals(data, tree.data) && Objects.equals(children, tree.children);
    }

    /**
     * hashcode.
     *
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(data, children);
    }


    /**
     * represents the tree to string.
     *
     * @return sting
     */
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
