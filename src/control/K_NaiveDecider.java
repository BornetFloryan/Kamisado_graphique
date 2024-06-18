package control;

import boardifier.control.ActionFactory;
import boardifier.control.Controller;
import boardifier.control.Decider;
import boardifier.control.Logger;
import boardifier.model.Model;
import boardifier.model.action.ActionList;
import model.*;
import view.K_BoardLook;

import java.util.*;

public class K_NaiveDecider extends Decider {
    private static final Random loto = new Random(Calendar.getInstance().getTimeInMillis());
    private static final List<int[]> winingMoveX = new ArrayList<>();
    private static final List<int[]> winingMoveO = new ArrayList<>();

    static {
        for (int i = 0; i < 8; i++) {
            winingMoveX.add(new int[]{i, 0});
            winingMoveO.add(new int[]{i, 7});
        }
    }

    public K_NaiveDecider(Model model, Controller controller) {
        super(model, controller);
    }

    @Override
    public ActionList decide() {
        Logger.debug("NAIVE AI is deciding");

        K_StageModel stage = (K_StageModel) model.getGameStage();
        HoleBoard board = stage.getBoard();
        Tree tree = new Tree();
        Pawn pawn;

        if (stage.getLockedColor() == null) {
            Pawn[] pawns = stage.getXPawns();
            pawn = pawns[loto.nextInt(pawns.length)];
        } else {
            pawn = stage.searchPawnFromLockedColor();
        }

        board.setValidCells(pawn);
        int fromY = board.getPawnGridCoordinate(pawn.getX(), board.getNbRows());

        addToTreeAllValidMoves(board.getReachableCells(), tree, fromY, pawn.getSymbol());

        Node nodes = tree.getMaxTo();
        int[] to = (nodes.getValue() == 0) ? getRandomMove(board.getReachableCells()) : nodes.getTo();

        K_BoardLook lookBoard = (K_BoardLook) control.getElementLook(board);
        String color = lookBoard.getColor(to[0], to[1]);
        stage.setLockedColor(color);

        ActionList action = ActionFactory.generateMoveWithinContainer(control, model, pawn, to[0], to[1]);
        action.setDoEndOfTurn(true);

        return action;
    }

    private int[] getRandomMove(boolean[][] reachableCells) {
        List<int[]> validMoves = new ArrayList<>();

        for (int i = 0; i < reachableCells.length; i++) {
            for (int j = 0; j < reachableCells[i].length; j++) {
                if (reachableCells[i][j]) {
                    validMoves.add(new int[]{i, j});
                }
            }
        }

        return validMoves.get(loto.nextInt(validMoves.size()));
    }

    private void addToTreeAllValidMoves(boolean[][] reachableCells, Tree tree, int rowFrom, char pawnSymbol) {
        int maxRow = reachableCells.length - 1;
        int maxCol = reachableCells[0].length - 1;
        boolean isMovingBottomToTop = pawnSymbol == 'X';

        for (int i = 0; i <= maxRow; i++) {
            for (int j = 0; j <= maxCol; j++) {
                if (reachableCells[i][j]) {
                    int distance = Math.abs(rowFrom - i);
                    int value = calculateMoveValue(i, j, maxRow, isMovingBottomToTop, distance);
                    tree.add(value, new int[]{i, j});
                }
            }
        }
    }

    private int calculateMoveValue(int row, int col, int maxRow, boolean isMovingBottomToTop, int distance) {
        int maxCol = 7;
        int baseValue;

        if (isMovingBottomToTop) {
            if (row == 0) {
                baseValue = Integer.MAX_VALUE;
            } else if (row == 1) {
                baseValue = distance + 10;
            } else {
                baseValue = distance;
            }
        } else {
            if (row == maxRow) {
                baseValue = Integer.MAX_VALUE;
            } else if (row == maxRow - 1) {
                baseValue = distance + 10;
            } else {
                baseValue = distance;
            }
        }

        if (col == 0 || col == maxCol) {
            baseValue += 2;
        }

        return baseValue;
    }
}
