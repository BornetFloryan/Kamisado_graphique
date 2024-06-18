package control;

import boardifier.control.ActionFactory;
import boardifier.control.Controller;
import boardifier.control.Decider;
import boardifier.control.Logger;
import boardifier.model.Model;
import boardifier.model.action.ActionList;
import model.*;
import view.KamisadoBoardLook;

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

    public ActionList decide() {
        Logger.debug("NAIVE AI is deciding");

        KamisadoStageModel stage = (KamisadoStageModel) model.getGameStage();
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

        int fromX = board.getPawnGridCoordinate(pawn.getY(), board.getNbCols());
        int fromY = board.getPawnGridCoordinate(pawn.getX(), board.getNbRows());

        addToTreeAllValidMoves(board.getReachableCells(), tree, fromY);

        System.out.println("Tree:");
        tree.displayTree();

        Node nodes = tree.getMaxTo();

        System.out.println("Max node: " + Arrays.toString(nodes.getTo()) + " with value: " + nodes.getValue());

        int[] to;

        if (nodes.getValue() == 0) {
            to = getRandomMove(board.getReachableCells());
        } else {
            to = nodes.getTo();
        }



        KamisadoBoardLook lookBoard = (KamisadoBoardLook) control.getElementLook(board);
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


    private void addToTreeAllValidMoves(boolean[][] reachableCells, Tree tree, int rowFrom) {
        int maxRow = reachableCells.length - 1;
        int maxCol = reachableCells[0].length - 1;

        for (int i = 0; i <= maxRow; i++) {
            for (int j = 0; j <= maxCol; j++) {
                if (reachableCells[i][j]) {
                    int distance = Math.abs(rowFrom - i);
                    int value = distance; // Plus la distance est grande, plus la valeur est grande

                    tree.add(value, new int[]{i, j});
                }
            }
        }
    }
}