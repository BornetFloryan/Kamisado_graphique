package model;

import boardifier.model.ElementTypes;
import boardifier.model.GameElement;
import boardifier.model.GameStageModel;
//import boardifier.view.ConsoleColor;

/**
 * A basic pawn element, with only 2 fixed parameters : number and color
 * There are no setters because the state of a Hole pawn is fixed.
 */
public class Pawn extends GameElement {
    private final int number;
    private final String color;
    private final char symbol;

    public Pawn(int number, String color, char symbol, GameStageModel gameStageModel) {
        super(gameStageModel);
        this.number = number;
        this.color = color;
        this.symbol = symbol;
        ElementTypes.register("pawn", 50);
        type = ElementTypes.getType("pawn");
    }

    public String getColor() {
        return color;
    }

    public char getSymbol() {
        return symbol;
    }

    public String toString() {
        return "Symbol : " + symbol + ", color : " + color;
    }
}
