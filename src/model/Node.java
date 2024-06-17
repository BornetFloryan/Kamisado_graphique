package model;

public class Node {
    private Node left;
    private Node right;
    private int value;
    private int[] to;

    public Node(int value, int[] to) {
        if (to == null || to.length != 2) {
            throw new IllegalArgumentException("Array 'to' must have exactly two elements.");
        }

        this.value = value;
        this.to = to;
        this.left = null;
        this.right = null;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public int getValue() {
        return value;
    }

    public int[] getTo() {
        return to;
    }

    public void setTo(int[] to) {
        if (to == null || to.length != 2) {
            throw new IllegalArgumentException("Array 'to' must have exactly two elements.");
        }
        this.to = to;
    }
}
