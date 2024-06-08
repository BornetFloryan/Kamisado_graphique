package model;

import boardifier.model.ContainerElement;
import boardifier.model.GameStageModel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class HoleBoard extends ContainerElement {
    public HoleBoard(int x, int y, GameStageModel gameStageModel) {
        // call the super-constructor to create a 3x3 grid, named "holeboard", and in x,y in space
        super("holeboard", x, y, 8 , 8, gameStageModel);
        resetReachableCells(false);
    }

    public void setValidCells(Pawn pawn) {
        resetReachableCells(false);
        List<Point> valid = computeValidCells(pawn);
        if (valid != null) {
            for(Point p : valid) {
                reachableCells[p.y][p.x] = true;
            }
        }
        addChangeFaceEvent();
    }

    public List<Point> computeValidCells(Pawn pawn) {
        List<Point> lst = new ArrayList<>();

        return lst;
    }
}
