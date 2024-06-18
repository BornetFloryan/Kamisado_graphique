package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tree {
    private Node root;

    public Tree() {
        root = null;
    }

    public void add(int value, int[] to) {
        if (to == null || to.length != 2) {
            throw new IllegalArgumentException("Array 'to' must have exactly two elements.");
        }

        if (root == null) {
            root = new Node(value, to);
        } else {
            addRecursive(root, value, to);
        }
    }

    private void addRecursive(Node node, int value, int[] to) {
        if (Arrays.equals(node.getTo(), to)) {
            // If the node with the same move exists, update its value
            node.setValue(value);
        } else if (value > node.getValue()) {
            if (node.getRight() == null) {
                node.setRight(new Node(value, to));
            } else {
                addRecursive(node.getRight(), value, to);
            }
        } else {
            if (node.getLeft() == null) {
                node.setLeft(new Node(value, to));
            } else {
                addRecursive(node.getLeft(), value, to);
            }
        }
    }


    public Node getMaxTo() {
        if (root == null) {
            return null;
        }
        return getMaxToRecursive(root);
    }

    private Node getMaxToRecursive(Node node) {
        if (node.getRight() == null) {
            return node;
        } else {
            return getMaxToRecursive(node.getRight());
        }
    }

    public List<Node> getAll10Point() {
        return getAll10PointRecursive(root);
    }

    private List<Node> getAll10PointRecursive(Node node) {
        List<Node> tenPointMoves = new ArrayList<>();

        if (node == null) {
            return tenPointMoves;
        }
        if (node.getValue() == 10) {
            tenPointMoves.add(node);
        }

        tenPointMoves.addAll(getAll10PointRecursive(node.getLeft()));
        tenPointMoves.addAll(getAll10PointRecursive(node.getRight()));
        return tenPointMoves;
    }


    public void displayTree() {
        if (root == null) {
            System.out.println("Tree is empty.");
            return;
        }
        displayTreeRecursive(root);
    }

    private void displayTreeRecursive(Node node) {
        if (node != null) {
            displayTreeRecursive(node.getLeft());
            System.out.println("Value: " + node.getValue() + ", to: " + Arrays.toString(node.getTo()));
            displayTreeRecursive(node.getRight());
        }
    }

    public boolean isEmpty() {
        return root == null;
    }
}