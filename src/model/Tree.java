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
        return getMaxToRecursive(root, root);
    }

    private Node getMaxToRecursive(Node node, Node maxNode) {
        if (node == null) {
            return maxNode;
        }
        if (node.getValue() > maxNode.getValue()) {
            maxNode = node;
        }
        maxNode = getMaxToRecursive(node.getLeft(), maxNode);
        maxNode = getMaxToRecursive(node.getRight(), maxNode);
        return maxNode;
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

    public List<Node> get0valueNode() {
        List<Node> zeroPointMoves = new ArrayList<>();
        getAll0PointRecursive(root, zeroPointMoves);
        return zeroPointMoves;
    }

    private void getAll0PointRecursive(Node node, List<Node> zeroPointMoves) {
        if (node == null) {
            return;
        }
        if (node.getValue() == 0) {
            zeroPointMoves.add(node);
        }
        getAll0PointRecursive(node.getLeft(), zeroPointMoves);
        getAll0PointRecursive(node.getRight(), zeroPointMoves);
    }

    public List<Node> getAll10Point() {
        List<Node> tenPointMoves = new ArrayList<>();
        getAll10PointRecursive(root, tenPointMoves);
        return tenPointMoves;
    }

    private void getAll10PointRecursive(Node node, List<Node> tenPointMoves) {
        if (node == null) {
            return;
        }
        if (node.getValue() == 10) {
            tenPointMoves.add(node);
        }
        getAll10PointRecursive(node.getLeft(), tenPointMoves);
        getAll10PointRecursive(node.getRight(), tenPointMoves);
    }

}
