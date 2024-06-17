package model;

import java.util.ArrayList;
import java.util.List;

public class Tree {
    private Node root;

    public Tree() {
        root = null;
    }

    public void add(int value, int[] to) {
        if (root == null) {
            root = new Node(value, to);
        } else {
            addRecursive(root, value, to);
        }
    }

    private void addRecursive(Node node, int value, int[] to) {
        if (value > node.getValue()) {
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

    public int[] getMaxTo() {
        if (root == null) {
            return null;
        }
        return getMaxToRecursive(root);
    }

    private int[] getMaxToRecursive(Node node) {
        if (node.getRight() == null) {
            return node.getTo();
        } else {
            return getMaxToRecursive(node.getRight());
        }
    }

    public void displayTree() {
        displayTreeRecursive(root);
    }

    private void displayTreeRecursive(Node node) {
        if (node != null) {
            displayTreeRecursive(node.getLeft());
            System.out.println("Value: " + node.getValue() + ", to: [" + node.getTo()[0] + ", " + node.getTo()[1] + "]");
            displayTreeRecursive(node.getRight());
        }
    }

}
