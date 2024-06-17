package model;

public class MinimalBoard {
    public char symbol;
    public String color;

    public MinimalBoard(char symbol, String color) {
        this.symbol = symbol;
        this.color = color;
    }

    public char getSymbol() {
        return symbol;
    }

    public String getColor() {
        return color;
    }


    public String toString() {
        return "Symbol: " + symbol + " Color: " + color;
    }

}
