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
        KamisadoStageModel stage = (KamisadoStageModel) gameStageModel;
        List<Point> lst = new ArrayList<>();
        int[][] directions;

        // If the name of the current player is equal to the first player name in the list of player
        if (stage.getCurrentPlayerName().equals(stage.getModel().getPlayers().get(0).getName())) {
            // Up, Up-Right, Up-Left
            directions = new int[][]{{0, -1}, {1, -1}, {-1, -1}};
        } else {
            // Down, Down-Right, Down-Left
            directions = new int[][]{{0, 1}, {-1, 1}, {1, 1}};
        }

        for (int[] dir : directions) {
            int dx = dir[0], dy = dir[1];
            int x = (int) (pawn.getX() / (600f / stage.getBoard().getNbRows())) + dx;
            int y = (int) (pawn.getY() / (600f / stage.getBoard().getNbCols())) + dy;

            while (x >= 0 && x < 8 && y >= 0 && y < 8) {
                if (stage.getBoard().getElement(y, x) == null) {
                    lst.add(new Point(x, y));
                } else {
                    break;
                }

                x += dx;
                y += dy;
            }
        }

        return lst;
    }
}
