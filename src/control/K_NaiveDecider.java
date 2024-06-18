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
        Logger.debug("Smart AI is deciding");

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

        Node nodeTo = tree.getMaxTo();

        List<Node> nodes = tree.getAll10Point();

        int[] to;

        if (nodes.size() == 0) {
            to = getRandomMove(board.getReachableCells());
        } else {
            int index = loto.nextInt(nodes.size());
            to = nodes.get(index).getTo();
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
        for (int i = 0; i < reachableCells.length; i++) {
            for (int j = 0; j < reachableCells[i].length; j++) {
                if (reachableCells[i][j] && j == rowFrom) {
                    tree.add(10, new int[]{i, j});
                } else if (reachableCells[i][j] && j != rowFrom) {
                    tree.add(0, new int[]{i, j});
                }
            }
        }
    }
}